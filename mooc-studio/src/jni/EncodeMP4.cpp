
#include "EncodeMP4.h"

#ifndef NDK_BUILD
#include <io.h> 
#endif 

/************************************************************************/
/* ��ȱ���ڴ��ͷ�      isFirst */
/************************************************************************/
CEncodeMP4::CEncodeMP4(void)
{
	IS_DEBUG = false;
	tagScope = 800;//��������Ƿ���ܹ��ķ�Χ
	xorLength = 4567;//���������С
	tagWhere = 5656;//��������Ƿ���ܹ����ļ�λ��
	tagValue = 0x83128def;//��������Ƿ���ܵı�־
	minEndTag = 8;//��β���ڻ����ĵ���С����
}

void CEncodeMP4::init(const char *path)
{
	sprintf(fileName, path, 500);
	if((in = fopen(fileName, "rb+"))== NULL)
	{
		if (IS_DEBUG)
		{
			OutputDbgInfo("Cannot open the file: %s\n", fileName);
		}
		return;
	}

	if(in){
		long curpos, length;
		curpos = ftell(in);
		fseek(in, 0L, SEEK_END);
		fileLength = ftell(in);
		fseek(in, curpos, SEEK_SET);
		if (IS_DEBUG){
			OutputDbgInfo(" file length: %08x\n", fileLength);
		}
	}
}

CEncodeMP4::~CEncodeMP4(void)
{
	fclose(in);
}

void CEncodeMP4::decode(){
	if (!checkLength()) {
		return;
	}
	if (!isEncode()) {
		return;
	}

	int position = getPosition();
	char *encodeBuffer = (char *)malloc(xorLength);
	// ��ȡ�ļ�ĩβָ�����ȵ�����
	fseek(in,fileLength - xorLength - position - minEndTag,SEEK_SET);
	fread(encodeBuffer,xorLength,1,in);

	char * encodeBuffer2 = (char *)malloc(xorLength);
	// ��ȡ��������ָ������byte
	fseek(in,getXorStart(position),SEEK_SET);
	fread(encodeBuffer2,xorLength,1,in);

	// �������
	xorScope(encodeBuffer, encodeBuffer2);

	// �������д����ļ���ʼλ�á�
	fseek(in,0,SEEK_SET);
	fwrite(encodeBuffer,xorLength,1,in);

	deleteEncodeTag(position);
}


void CEncodeMP4::encode(){
	if (!checkLength()) {
		return;
	}
	if (isEncode()) {
		return;
	}
	int position = getPosition();

	// ��ȡ�ļ���ǰ��ָ������byte
	char *encodeBuffer = (char *)malloc(xorLength);
	fseek(in, 0, SEEK_SET);
	fread(encodeBuffer, xorLength,1,in);

	// ��ȡ��������ָ������byte
	char *encodeBuffer2 = (char *)malloc(xorLength);
	fseek(in,getXorStart(position), SEEK_SET);
	fread(encodeBuffer2, xorLength,1,in);

	// �ļ����
	xorScope(encodeBuffer, encodeBuffer2);

	writeOtherInfoToFirst();
	createEncodeTag(position);

	if (checkFileLast(encodeBuffer, position + minEndTag)) {
		if (IS_DEBUG) {
			OutputDbgInfo("not first encode");
		}
		return;
	} else {
		if (IS_DEBUG) {
			OutputDbgInfo("first encode");
		}
	}

	// ���ݱ��浽�ļ�ĩβ
	fseek(in, fileLength, SEEK_SET);
	fwrite(encodeBuffer, xorLength,1,in);
	fileLength += xorLength;
	fflush(in);

	createConfusionEnd(position + minEndTag);

	free(encodeBuffer);
	free(encodeBuffer2);
	encodeBuffer = NULL;
	encodeBuffer2 = NULL;
	if(IS_DEBUG){
		OutputDbgInfo("finish");
	}
}


bool CEncodeMP4::isEncode() {
	if (!checkLength()) {
		return false;
	}
	int position = getPosition();
	fseek(in,position,SEEK_SET);

	U32 num = 0;
	fread(&num, sizeof(U32), 1, in); 
	num = ((num>>24)&0xff)+((num>>8)&0xff00)+((num<<8)&0xff0000)+((num<<24)&0xff000000);

	if (tagValue == num) {
		if (IS_DEBUG) {
			OutputDbgInfo("have been encoded %s", fileName);
		}
		return true;
	} else {
		if (IS_DEBUG) {
			OutputDbgInfo("not encoded %s", fileName);
		}
		return false;
	}
}

bool CEncodeMP4::checkFileLast(char *encodeBuffer, int position){
	char *checkBuf = (char *)malloc(xorLength);
	//����ԭ��д��xorLength  �����Ǽ����ĳ��Ȳ���
	fseek(in, fileLength - xorLength - position, SEEK_SET);
	fread(checkBuf, xorLength,1,in);
	bool flag = true;
	for (int i = 0; i < xorLength; i++) {
		if (encodeBuffer[i] != checkBuf[i]) {
			flag = false;
			break;
		}
	}
	free(checkBuf);
	checkBuf = NULL;
	return flag;
}

void CEncodeMP4::writeOtherInfoToFirst(){
	char *encodeBuffer = (char *)malloc(xorLength);
	// ��ȡ�м�ĳ�������ݶ��ļ�ͷ�������
	fseek(in,(fileLength - 1 - xorLength) / 5,SEEK_SET);
	fread(encodeBuffer, xorLength,1,in);

	// ���������д�뵽�ļ�ͷ
	fseek(in, 0,SEEK_SET);
	fwrite(encodeBuffer, xorLength,1,in);
	free(encodeBuffer);
	encodeBuffer = NULL;
}

void CEncodeMP4::deleteEncodeTag(int position) {
	fseek(in, fileLength - xorLength - position,SEEK_SET);
}

int CEncodeMP4::getXorStart(int position) {
	//����Ҫ��һ����λ��ƫ��
	int result = xorLength * ((position % 3) + 1) + 5;
	return result;
}

void CEncodeMP4::createConfusionEnd(int position){
	char *encodeBuffer = (char *)malloc(position);
	fseek(in, fileLength/ 3,SEEK_SET);
	//����ԭ��д��xorLength  ������position����
	fread(encodeBuffer, position,1,in);
	// ���ݱ��浽�ļ�ĩβ
	fseek(in, fileLength, SEEK_SET);
	fwrite(encodeBuffer, position,1,in);
	fileLength+=position;
	fflush(in);
	free(encodeBuffer);
	encodeBuffer = NULL;
}

void CEncodeMP4::xorScope(char *one, char *two)
{
	for (int i = 0; i < xorLength; i++) {
		one[i] = one[i] ^ two[i];
	}
}

void CEncodeMP4::createEncodeTag(int position)
{
	fseek(in, position, SEEK_SET);
	int num = ((tagValue>>24)&0xff)+((tagValue>>8)&0xff00)+((tagValue<<8)&0xff0000)+((tagValue<<24)&0xff000000);

	//MARk
	fwrite(&num, sizeof(U32), 1, in);  
}

int CEncodeMP4::getPosition()
{
	U32 num = 0;
	fseek(in, tagWhere, SEEK_SET);
	fread(&num, sizeof(U32), 1, in); 
	num = ((num>>24)&0xff)+((num>>8)&0xff00)+((num<<8)&0xff0000)+((num<<24)&0xff000000);

	return num % tagScope;
}

bool CEncodeMP4::checkLength()
{
	return fileLength > xorLength * 5;
}

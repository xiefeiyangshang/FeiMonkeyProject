
#include "EncodeMP4.h"

#ifndef NDK_BUILD
#include <io.h> 
#endif 

/************************************************************************/
/* 还缺少内存释放      isFirst */
/************************************************************************/
CEncodeMP4::CEncodeMP4(void)
{
	IS_DEBUG = false;
	tagScope = 800;//用来标记是否加密过的范围
	xorLength = 4567;//加密区域大小
	tagWhere = 5656;//用来标记是否加密过的文件位置
	tagValue = 0x83128def;//用来标记是否加密的标志
	minEndTag = 8;//结尾用于混淆的的最小区域
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
	// 读取文件末尾指定长度的内容
	fseek(in,fileLength - xorLength - position - minEndTag,SEEK_SET);
	fread(encodeBuffer,xorLength,1,in);

	char * encodeBuffer2 = (char *)malloc(xorLength);
	// 读取接下来的指定数量byte
	fseek(in,getXorStart(position),SEEK_SET);
	fread(encodeBuffer2,xorLength,1,in);

	// 内容异或
	xorScope(encodeBuffer, encodeBuffer2);

	// 将异或结果写入的文件开始位置。
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

	// 获取文件最前面指定数量byte
	char *encodeBuffer = (char *)malloc(xorLength);
	fseek(in, 0, SEEK_SET);
	fread(encodeBuffer, xorLength,1,in);

	// 读取接下来的指定数量byte
	char *encodeBuffer2 = (char *)malloc(xorLength);
	fseek(in,getXorStart(position), SEEK_SET);
	fread(encodeBuffer2, xorLength,1,in);

	// 文件异或
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

	// 内容保存到文件末尾
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
	//这里原来写的xorLength  发现是计算后的长度才行
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
	// 获取中间某部分内容对文件头进行填充
	fseek(in,(fileLength - 1 - xorLength) / 5,SEEK_SET);
	fread(encodeBuffer, xorLength,1,in);

	// 将填充内容写入到文件头
	fseek(in, 0,SEEK_SET);
	fwrite(encodeBuffer, xorLength,1,in);
	free(encodeBuffer);
	encodeBuffer = NULL;
}

void CEncodeMP4::deleteEncodeTag(int position) {
	fseek(in, fileLength - xorLength - position,SEEK_SET);
}

int CEncodeMP4::getXorStart(int position) {
	//至少要有一个身位的偏移
	int result = xorLength * ((position % 3) + 1) + 5;
	return result;
}

void CEncodeMP4::createConfusionEnd(int position){
	char *encodeBuffer = (char *)malloc(position);
	fseek(in, fileLength/ 3,SEEK_SET);
	//这里原来写的xorLength  发现是position才行
	fread(encodeBuffer, position,1,in);
	// 内容保存到文件末尾
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

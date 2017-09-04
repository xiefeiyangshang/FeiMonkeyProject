#include "jnicommon.h"

#pragma once

class CEncodeMP4
{
public:
	CEncodeMP4(void);
	~CEncodeMP4(void);
	void init(const char *path);
private:
	bool IS_DEBUG;
	char fileName[500] ;
	int tagScope ;//用来标记是否加密过的范围
	int xorLength;//加密区域大小
	int tagWhere ;//用来标记是否加密过的文件位置
	int tagValue ;//用来标记是否加密的标志
	int minEndTag;//结尾用于混淆的的最小区域
	FILE *in;
	long fileLength;
public:
    void encode();
	bool isEncode();
	void decode();
private:

	void xorScope(char *one, char *two);

	void createEncodeTag(int position);

	int getPosition();

	bool checkLength();

	bool checkFileLast(char* encodeBuffer, int position) ;

	void writeOtherInfoToFirst() ;

	void deleteEncodeTag(int position);

	int getXorStart(int position);

	void createConfusionEnd(int position);

};


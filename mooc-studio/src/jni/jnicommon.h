#pragma once
#define NDK_BUILD 1;

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#ifndef NDK_BUILD
#include <io.h> 
#endif 

#define U8	unsigned char
#define U16	unsigned short
#define U32	unsigned int
#pragma warning(disable:4996)

#ifdef NDK_BUILD
#include <jni.h>
#include<android/log.h>
#define TAG "jnicommon" // 这个是自定义的LOG的标识    
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义OutputDbgInfo类型    
#else
#include <stdarg.h>
#endif

/**************************************
*  功能：调试输出
*  参数：
*
*  返回值：
*  负责人：
*  时间：[2014/4/27/ Administrator]
****************************************/
static void OutputDbgInfo(char *format, ...){
	va_list arglist;
	char buffer[1024];
	va_start (arglist,format);
	vsprintf(buffer, format, arglist);
	va_end (arglist);
	strcat(buffer, "\n");
#ifdef NDK_BUILD
	LOGE(buffer, "");
#else
	printf(buffer);
#endif
}

/*
static long getFileLength(FILE* in){
	long result = 0;
	if(in){
		result = filelength(fileno(in));
	}
	OutputDbgInfo("文件大小： %08x", result);
	return result;
}
*/





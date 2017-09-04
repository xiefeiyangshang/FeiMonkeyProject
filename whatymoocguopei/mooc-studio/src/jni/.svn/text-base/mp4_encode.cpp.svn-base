#include "EncodeMP4.h"
#include <stdlib.h>
int main()
{
	CEncodeMP4 encode;
	char *file = "C:/Users/whaty/Desktop/new/111.mp4";
	encode.init(file);
	if(encode.isEncode()){
		OutputDbgInfo("加密过了 - 进行解密： %s", file);
		encode.decode();
	}else{
		OutputDbgInfo("没加密- 进行加密： %s", file);
		encode.encode();
	}
	getchar();
	return 0;
}


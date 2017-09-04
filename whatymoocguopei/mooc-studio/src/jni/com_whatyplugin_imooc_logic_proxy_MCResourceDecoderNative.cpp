
#include <stdio.h>
#include "EncodeMP4.h"

#include "com_whatyplugin_imooc_logic_proxy_MCResourceDecoderNative.h" 

//这里添加个  #ifdef NDK_BUILD 跟头文件保持一致
#ifdef NDK_BUILD
#ifdef __cplusplus
extern "C" {
#endif
	JNIEXPORT jboolean JNICALL Java_com_whatyplugin_imooc_logic_proxy_MCResourceDecoderNative_encode
		(JNIEnv *env, jclass jc, jstring js){
			//变量转换
			const char *str = env->GetStringUTFChars(js,0);
			int result = 0;
			CEncodeMP4 *encode =new CEncodeMP4();
			encode->init(str);
			if(!encode->isEncode()){
				encode->encode();
				result=1;
			}
			free(encode);

			//字符串释放
			env->ReleaseStringUTFChars(js, str);

			return result;
	}

	JNIEXPORT jboolean JNICALL Java_com_whatyplugin_imooc_logic_proxy_MCResourceDecoderNative_decode
		(JNIEnv *env, jclass jc, jstring js){
			//变量转换
			const char *str = env->GetStringUTFChars(js,0);

			int result = 0;
			CEncodeMP4 *encode =new CEncodeMP4();
			encode->init(str);
			if(encode->isEncode()){
				encode->decode();
				result=1;
			}
			free(encode);

			//字符串释放
			env->ReleaseStringUTFChars(js, str);
			return result;
	}
#ifdef __cplusplus
}
#endif
#endif
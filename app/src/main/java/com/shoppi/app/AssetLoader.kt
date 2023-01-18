package com.shoppi.app

import android.content.Context
import android.util.Log

class AssetLoader {

    // loadAsset에서 Exception이 발생했을 경우에 대한 처리를 해주는 함수
    fun getJsonString(context: Context, fileName: String): String? {
        // 문자열을 얻는 과정에서 Exception이 발생할 수도 있음
        // 성공 혹은 실패케이스로 나누어지는 과정을 처리할 경우
        return kotlin.runCatching {
            loadAsset(context, fileName)
        }.getOrNull()       // runCatching 내에서 Exception이 발생했을 경우 null로 받을 수 있음.
    }

    // 여기서 얻고자 하는 값: Json Format을 String 타입으로 변환한 값
    private fun loadAsset(context: Context, fileName: String): String {
        // application 전역에서 사용할수 있는 정보에 접근, 시스템 자원에 접근
        // inputStream객체로 가져온 정보를 해제를 해주어야 한다.
        return context.assets.open(fileName).use { inputStream ->
            val size = inputStream.available()      // size 확인
            val bytes = ByteArray(size)         // ByteArray 초기화
            inputStream.read(bytes)
            String(bytes)       // String 타입으로 변환

        }
    }
}
package com.shoppi.app.ui

import androidx.lifecycle.ViewModel

// Home화면을 그리는데 필요한 데이터의 홀더 역할
// 뷰 모델: 같은 상태 유지 --> activity 생명주기와 비교된다. 기존 액티비티와 다르게 어떤 상태던지 언제든지 데이터를 요청 가능
// 종료시 -> onCleared() 호출
class HomeViewModel: ViewModel() {
    // 데이터 요청 메소드
    fun loadHomeData() {
        // TODO: Data Layer - Repository에 요청
    }
}
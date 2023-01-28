package com.shoppi.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shoppi.app.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_main)
        bottomNavigationView.itemIconTintList = null        // 커스텀한 디자인이 적용되도록 한다.


        // NavHostFragment에 대해 접근
        // find나 get으로 시작하는 메소드는 반환할때 null을 반환할 수 있기 때문에 safecall operator를 사용.
        val navController = supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()
        navController?.let {
            // 바텀네비게이션과 fragmentHost인 NavHostFragment와 연결 시켜줌, 이동을 관리하는 객체
            bottomNavigationView.setupWithNavController(it)
        }
    }
}
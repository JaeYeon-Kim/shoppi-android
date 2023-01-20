package com.shoppi.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import org.json.JSONObject

class HomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // 다음의 메소드 안에서 리스너 구현
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 텍스트뷰와 이미지뷰 변수 생성
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbar_home_title)
        val toolbarIcon = view.findViewById<ImageView>(R.id.toolbar_home_icon)
        val viewpager = view.findViewById<ViewPager2>(R.id.viewpager_home_banner)
        val viewpagerIndicator = view.findViewById<TabLayout>(R.id.viewpager_home_banner_indicator)


        // json 변환을 위해 만들어둔 클래스 인스턴스 생성
        val assetLoader = AssetLoader()
        val homeJsonString = assetLoader.getJsonString(requireContext(), "home.json")
        Log.d("homeData", homeJsonString ?: "")

        // homeData를 JsonObject로 변환하기, homeData가 null이거나 empty가 아닐때에만 JsonObject 생성
        if(!homeJsonString.isNullOrEmpty()) {
            // gson 사용
            val gson = Gson()
            val homeData = gson.fromJson(homeJsonString, HomeData::class.java)
            // 앱바 텍스트뷰 변경
            toolbarTitle.text = homeData.title.text
            GlideApp.with(this)
                .load(homeData.title.iconUrl)
                .into(toolbarIcon)


            // 뷰페이저 어댑터 할당
            viewpager.adapter = HomeBannerAdapter().apply{
                // 배너의 리스트를 전달
                submitList(homeData.topBanners)
            }

        }
    }
}
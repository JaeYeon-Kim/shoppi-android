package com.shoppi.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.shoppi.app.AssetLoader
import com.shoppi.app.GlideApp
import com.shoppi.app.HomeBannerAdapter
import com.shoppi.app.HomeData
import com.shoppi.app.R

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

            // dp값을 pixel단위로 변경하는 작업 필요 -> 안드로이드 시스템에서 제공하는 메소드 사용
            val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
            val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
            // 디바이스의 가로길이 구하기
            val screenWidth = resources.displayMetrics.widthPixels
            val offset = screenWidth - pageWidth - pageMargin

            viewpager.offscreenPageLimit = 3

            // 뷰페이저 애니메이션 구현
            // SAM 형식의 메서드는 람다 형식으로 구현이 가능 하기 때문에 람다식으로 변경
            viewpager.setPageTransformer { page, position ->
                page.translationX = position * -offset          // position에 offset을 곱한만큼 적용
            }

            // 인디케이터 구현
            TabLayoutMediator(viewpagerIndicator, viewpager) { tab, position ->

            }.attach()          // 뷰페이저의 페이지가 변경되었을때 탭 레이아웃의 인디케이터도 변경된다.


        }
    }
}
package com.shoppi.app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import kotlin.math.roundToInt


class HomeBannerAdapter: ListAdapter<Banner, HomeBannerAdapter.HomeBannerViewHolder>(BannerDiffCallback())  {



    // 위에서 생성한 뷰 홀더를 생성하는 메소드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner, parent, false)
        return HomeBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        // 해당 포지션의 데이터 타입을 반환해준다.
        holder.bind(getItem(position))
    }



// 뷰 홀더 생성
class HomeBannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val bannerImageView = view.findViewById<ImageView>(R.id.iv_banner_image)
    private val bannerBadgeTextView = view.findViewById<TextView>(R.id.tv_banner_badge)
    private val bannerTitleTextView = view.findViewById<TextView>(R.id.tv_banner_title)
    private val bannerDetailThumbnailImageView = view.findViewById<ImageView>(R.id.iv_banner_detail_thumbnail)
    private val bannerDetailBrandLabelTextView = view.findViewById<TextView>(R.id.tv_banner_detail_brand_label)
    private val bannerProductLabelTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_label)
    private val bannerProductDiscountRateTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_discount_rate)
    private val bannerDetailDiscountPriceTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_discount_price)
    private val bannerDetailPriceTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_price)



    // 바인딩할 메소드
    fun bind(banner: Banner) {
        loadImage(banner.backgroundImageUrl, bannerImageView)
        bannerBadgeTextView.text = banner.badge.label
        bannerBadgeTextView.background = ColorDrawable(Color.parseColor(banner.badge.backgroundColor))
        bannerTitleTextView.text = banner.label
        bannerDetailThumbnailImageView
        loadImage(banner.productDetail.thumbnailImageUrl, bannerDetailThumbnailImageView)
        bannerDetailBrandLabelTextView.text = banner.productDetail.brandName
        bannerProductLabelTextView.text = banner.productDetail.brandName
        bannerProductDiscountRateTextView.text = "${banner.productDetail.discountRate}%"
        calculateDiscountAmount(bannerDetailDiscountPriceTextView, banner.productDetail.discountRate, banner.productDetail.price)
        applyPriceFormat(bannerDetailPriceTextView, banner.productDetail.price)
        }

    private fun calculateDiscountAmount(view: TextView, discountRate: Int, price: Int){
        val discountPrice = (((100 - discountRate) / 100.0) * price).roundToInt()
        applyPriceFormat(view, discountPrice)
    }

    // 가격에 콤마를 표시하기 위한 메소드
    private fun applyPriceFormat(view: TextView, price: Int) {
        val decimalFormat = DecimalFormat("#,###")
        view.text = decimalFormat.format(price) + "원"
    }

    // 이미지를 로드하는 부분의 중복을 막기 위해 함수로 추출
    // 뷰홀더 내부에서만 사용
    private fun loadImage(urlString: String, imageView: ImageView) {
        GlideApp.with(itemView)
            .load(urlString)
            .into(imageView)
    }
    }
}


/**
 * DiffUtil: 스크롤이 변경 됨에 따라서 실제로 데이터가 변경 되는지 확인 하고 실제로 변경이 확인이 되면 UI를 적용
 */
class BannerDiffCallback : DiffUtil.ItemCallback<Banner>() {
    // 기존 객체와 새로운 객체를 비교할때 어떠한것을 식별자로 사용할지 결정
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.productDetail.productId == newItem.productDetail.productId
    }

    // 위 메소드에서 식별자가 설정되어 판별이 끝나면 한번더 비교하여 동등성 비교
    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }

}
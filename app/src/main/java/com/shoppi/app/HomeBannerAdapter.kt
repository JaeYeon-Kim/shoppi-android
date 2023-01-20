package com.shoppi.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


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
    private val bannerDetailProductPriceTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_price)



    // 바인딩할 메소드
    fun bind(banner: Banner) {
            GlideApp.with(itemView)
                .load(banner.backgroundImageUrl)
                .into(bannerImageView)
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
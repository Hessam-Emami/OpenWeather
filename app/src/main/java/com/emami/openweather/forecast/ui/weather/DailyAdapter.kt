package com.emami.openweather.forecast.ui.weather

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.emami.openweather.R
import com.emami.openweather.common.base.BaseAdapter
import com.emami.openweather.common.util.makeGone
import com.emami.openweather.common.util.makeVisible
import com.emami.openweather.common.util.setColoredTextWithGivenRange
import com.emami.openweather.forecast.ui.model.UiDailyWeather
import kotlinx.android.synthetic.main.item_recycler_weather_by_day.view.*
import java.util.*
import javax.inject.Inject

class DailyAdapter @Inject constructor() :
    BaseAdapter<UiDailyWeather>(
        DiffCallback
    ) {

    var lastSelectedItemPos = -1
    var onDailyItemClickListener: ((item: UiDailyWeather, isFirstItem: Boolean) -> Unit)? = null
    override fun getLayoutId(): Int {
        return R.layout.item_recycler_weather_by_day
    }

    @SuppressLint("DefaultLocale")
    override fun bindView(): (item: UiDailyWeather, itemView: View, position: Int) -> Unit {
        return { item, itemView, position ->
            itemView.setOnClickListener {
                onDailyItemClickListener?.invoke(item, position == 0)
                if (lastSelectedItemPos == position) {
                    lastSelectedItemPos = -1
                }
                notifyItemChanged(lastSelectedItemPos)
                lastSelectedItemPos = position
                notifyItemChanged(position)
            }
            with(itemView) {
                //Populate views
                item_daily_tv_day.text =
                    when (position) {
                        0 -> context.getString(R.string.today)
                        1 -> context.getString(
                            R.string.tomorrow
                        )
                        else -> item.dayName.toLowerCase(Locale.getDefault()).capitalize()
                    }
                val minMax = item.minMaxTemperature.toString()
                if (minMax.isNotBlank() && minMax.contains('/')) {
                    item_daily_tv_degree_range.setColoredTextWithGivenRange(
                        minMax.indexOf('/'),
                        minMax.length,
                        minMax,
                        ContextCompat.getColor(context, R.color.secondaryTextColor)
                    )
                } else item_daily_tv_degree_range.text = minMax

                item_daily_iv_weather_state.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        item.drawableResourceId
                    )
                )
                if (lastSelectedItemPos == position) {
                    //Can animate here
                    item_daily_tv_degree_range.makeGone()
                    item_daily_iv_weather_state.makeGone()
                } else {
                    item_daily_tv_degree_range.makeVisible()
                    item_daily_iv_weather_state.makeVisible()
                }

            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<UiDailyWeather>() {
        override fun areItemsTheSame(
            oldItem: UiDailyWeather,
            newItem: UiDailyWeather
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: UiDailyWeather,
            newItem: UiDailyWeather
        ): Boolean =
            oldItem == newItem
    }
}


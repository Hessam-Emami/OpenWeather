package com.emami.openweather.forecast.ui.weather

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.emami.openweather.R
import com.emami.openweather.common.base.BaseAdapter
import com.emami.openweather.forecast.ui.model.UiHourlyWeather
import kotlinx.android.synthetic.main.item_recycler_weather_by_hour.view.*
import javax.inject.Inject

class HourlyAdapter @Inject constructor() :
    BaseAdapter<UiHourlyWeather>(
        DiffCallback
    ) {

    override fun getLayoutId(): Int {
        return R.layout.item_recycler_weather_by_hour
    }

    override fun bindView(): (item: UiHourlyWeather, itemView: View, position: Int) -> Unit {
        return { item, itemView, _ ->
            itemView.apply {
                item_hourly_iv_weather_state.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        item.drawableResourceId
                    )
                )
                item_hourly_tv_time.text = item.hourOfDay
                item_hourly_tv_degree.text =
                    context.getString(R.string.degree_value, item.temperature)
            }
        }

    }

    object DiffCallback : DiffUtil.ItemCallback<UiHourlyWeather>() {
        override fun areItemsTheSame(
            oldItem: UiHourlyWeather,
            newItem: UiHourlyWeather
        ): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: UiHourlyWeather,
            newItem: UiHourlyWeather
        ): Boolean =
            oldItem == newItem
    }
}
package com.emami.openweather.forecast.util

import androidx.annotation.DrawableRes
import com.emami.openweather.R
import com.emami.openweather.common.util.DateTimeUtil
import com.emami.openweather.forecast.data.network.dto.WeatherState
import javax.inject.Inject

class WeatherStateChecker @Inject constructor() {

    @DrawableRes
    fun getAppropriateDrawableId(
        weatherState: WeatherState,
        dayState: DateTimeUtil.DayTimeState
    ): Int =
        when (weatherState.id) {
            in 200..232 -> {
                R.drawable.ic_thunderstorm
            }
            in 300..321, in 500..504, in 520..531 -> {
                R.drawable.ic_shower_raint
            }
            //Though 511 it is part of Rain category[Freezing Rain], the api docs states snow icon for this code
            in 600..699, 511 -> {
                R.drawable.ic_snow
            }
            in 700..799 -> {
                R.drawable.ic_mist
            }
            800 -> {
                getAppropriateClearSkyDrawableId(dayState)
            }
            in 801..804 -> {
                getAppropriateCloudySkyDrawableId(dayState)
            }
            else -> {
                getAppropriateCloudySkyDrawableId(dayState)
            }
        }

    @DrawableRes
    private fun getAppropriateClearSkyDrawableId(dayState: DateTimeUtil.DayTimeState): Int =
        when (dayState) {
            DateTimeUtil.DayTimeState.DAWN -> R.drawable.ic_clear_sky_dawn
            DateTimeUtil.DayTimeState.MORNING -> R.drawable.ic_clear_sky_morning
            DateTimeUtil.DayTimeState.NOON -> R.drawable.ic_clear_sky_morning
            DateTimeUtil.DayTimeState.EVENING -> R.drawable.ic_clear_sky_evening
            DateTimeUtil.DayTimeState.NIGHT -> R.drawable.ic_clear_sky_night
        }


    @DrawableRes
    private fun getAppropriateCloudySkyDrawableId(dayState: DateTimeUtil.DayTimeState): Int =
        when (dayState) {
            DateTimeUtil.DayTimeState.DAWN -> R.drawable.ic_few_cloud_dawn
            DateTimeUtil.DayTimeState.MORNING -> R.drawable.ic_few_cloud_morning
            DateTimeUtil.DayTimeState.NOON -> R.drawable.ic_few_cloud_morning
            DateTimeUtil.DayTimeState.EVENING -> R.drawable.ic_few_cloud_evening
            DateTimeUtil.DayTimeState.NIGHT -> R.drawable.ic_few_clound_night
        }

}
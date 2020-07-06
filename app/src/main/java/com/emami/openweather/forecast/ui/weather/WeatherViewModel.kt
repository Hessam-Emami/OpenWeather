package com.emami.openweather.forecast.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.emami.openweather.common.base.BaseViewModel
import com.emami.openweather.common.util.DataResult
import com.emami.openweather.common.util.DateTimeUtil
import com.emami.openweather.forecast.data.WeatherRepository
import com.emami.openweather.forecast.data.network.dto.CurrentWeather
import com.emami.openweather.forecast.data.network.dto.DailyTemperatures
import com.emami.openweather.forecast.data.network.dto.DailyWeather
import com.emami.openweather.forecast.data.network.dto.OpenWeatherResponse
import com.emami.openweather.forecast.ui.model.UiCityLocation
import com.emami.openweather.forecast.ui.model.UiDailyWeather
import com.emami.openweather.forecast.ui.model.UiHourlyWeather
import com.emami.openweather.forecast.ui.model.UiMinMaxTemperature
import com.emami.openweather.forecast.util.WeatherStateChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository,
    private val weatherStateChecker: WeatherStateChecker
) :
    BaseViewModel() {

    private val _hourlyLiveData = MutableLiveData<List<UiHourlyWeather>>()
    val hourlyLiveData: LiveData<List<UiHourlyWeather>>
        get() = _hourlyLiveData

    private val _dailyLiveData = MutableLiveData<List<UiDailyWeather>>()
    val dailyLiveData: LiveData<List<UiDailyWeather>>
        get() = _dailyLiveData

    private val _loadingEvent = MutableLiveData<Boolean>().apply { value = false }
    val loadingEventLiveData: LiveData<Boolean>
        get() = _loadingEvent

    val dayTimeStateLiveData =
        liveData(Dispatchers.Main) {
            emit(DateTimeUtil.getCurrentDayTimeState())
        }
    val availableCities = liveData {
        val mappedCity = repo.getAvailableCities().map {
            UiCityLocation(
                it.id,
                it.name
            )
        }
        emit(mappedCity)
    }


    private fun mapCurrentWeatherToUiHourlyWeather(currentWeather: CurrentWeather): UiHourlyWeather {
        val time = DateTimeUtil.formatLocalDateTimeToHourAmPm(currentWeather.instantTime)
        val hourOfDay = DateTimeUtil.formatLocalDateTimeToHourOfDay(currentWeather.instantTime)
        Timber.d("Current weathers: $currentWeather")
        return UiHourlyWeather(
            temperature = currentWeather.temperature.toInt(),
            hourOfDay = time,
            drawableResourceId = weatherStateChecker.getAppropriateDrawableId(
                currentWeather.weather.first(),
                DateTimeUtil.getCurrentDayTimeState(hourOfDay.toInt())
            )
        )
    }

    private fun mapDailyWeatherToUiDailyWeather(dailyWeather: DailyWeather): UiDailyWeather {
        val currentHourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val day = dailyWeather.instantTime.dayOfWeek
        return UiDailyWeather(
            dayName = day.toString(),
            minMaxTemperature = UiMinMaxTemperature(
                dailyWeather.temperature.min.toInt(),
                dailyWeather.temperature.max.toInt()
            )
            ,
            temperature = getTemperatureBasedOnDayTime(
                dailyWeather.temperature,
                DateTimeUtil.getCurrentDayTimeState(currentHourOfDay)
            ).toInt(),
            drawableResourceId = weatherStateChecker.getAppropriateDrawableId(
                dailyWeather.weather.first(),
                DateTimeUtil.getCurrentDayTimeState(currentHourOfDay)
            )
        )
    }

    fun fetchDailyForecast(cityId: Int) {
        _loadingEvent.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repo.fetchWeatherForecast(cityId)) {
                is DataResult.Success -> {
                    processHourlyData(result.data)
                    processDailyData(result.data)
                }
                is DataResult.Error -> {
                    dispatchMessage(result.errorMsg)
                }
            }
            withContext(Dispatchers.Main) {
                _loadingEvent.value = false
            }
        }
    }

    private fun processDailyData(result: OpenWeatherResponse) {
        val dailyUiList = result.daily.map {
            mapDailyWeatherToUiDailyWeather(it)
        }
        _dailyLiveData.postValue(dailyUiList)
    }

    private fun processHourlyData(result: OpenWeatherResponse) {
        val hourlyUiList = result.hourly.map {
            mapCurrentWeatherToUiHourlyWeather(it)
        }
        _hourlyLiveData.postValue(hourlyUiList)
    }

    private fun getTemperatureBasedOnDayTime(
        temperatures: DailyTemperatures,
        dayState: DateTimeUtil.DayTimeState
    ): Double =
        with(temperatures) {
            when (dayState) {
                DateTimeUtil.DayTimeState.DAWN -> min
                DateTimeUtil.DayTimeState.MORNING -> morn
                DateTimeUtil.DayTimeState.NOON -> max
                DateTimeUtil.DayTimeState.EVENING -> eve
                DateTimeUtil.DayTimeState.NIGHT -> night
            }
        }

}


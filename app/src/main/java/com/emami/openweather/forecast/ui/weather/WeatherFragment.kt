package com.emami.openweather.forecast.ui.weather

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.emami.openweather.R
import com.emami.openweather.common.base.BaseFragment
import com.emami.openweather.common.util.DateTimeUtil
import com.emami.openweather.common.util.makeGone
import com.emami.openweather.common.util.makeInvisible
import com.emami.openweather.common.util.makeVisible
import com.emami.openweather.forecast.ui.model.UiCityLocation
import com.emami.openweather.forecast.ui.model.UiDailyWeather
import com.emami.openweather.forecast.ui.model.UiHourlyWeather
import kotlinx.android.synthetic.main.fragment_weather.*
import javax.inject.Inject

interface WeatherView {
    fun initRecyclerViews()
    fun renderCurrentDegree(item: UiDailyWeather)
    fun renderScreenBackgroundByDayTimeState(dayTimeState: DateTimeUtil.DayTimeState)
    fun renderDailyList(list: List<UiDailyWeather>)
    fun renderHourlyList(list: List<UiHourlyWeather>)
    fun renderCitySpinner(list: List<UiCityLocation>)
    fun showHourlyData(shouldShow: Boolean)
    fun onNewCitySelected(cityName: String)
}

class WeatherFragment @Inject constructor(
    private val dailyAdapter: DailyAdapter,
    private val hourlyAdapter: HourlyAdapter,
    vf: ViewModelProvider.Factory
) :
    BaseFragment<WeatherViewModel>(WeatherViewModel::class.java, vf), WeatherView {

    private var currentSelectedCity: UiCityLocation? = null
    private var handler: Handler? = null

    override fun getLayoutId(): Int = R.layout.fragment_weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        handler = Handler()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.dayTimeStateLiveData.observe(viewLifecycleOwner, Observer {
            renderScreenBackgroundByDayTimeState(it)
        })
        viewModel.dailyLiveData.observe(viewLifecycleOwner, Observer {
            renderDailyList(it)
            weather_swipe_to_refresh.isRefreshing = false
        })
        viewModel.hourlyLiveData.observe(viewLifecycleOwner, Observer {
            renderHourlyList(it)
        })
        viewModel.loadingEventLiveData.observe(viewLifecycleOwner, Observer {
            weather_swipe_to_refresh.isRefreshing = it
        })
        viewModel.availableCities.observe(viewLifecycleOwner, Observer {
            renderCitySpinner(it)
            requestNewData(it.first())
        })
    }

    override fun setupListeners() {
        super.setupListeners()
        weather_swipe_to_refresh.setOnRefreshListener {
            requestNewData(currentSelectedCity)
        }
        weather_spinner_city_picker.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val cityName = parent?.getItemAtPosition(position).toString()
                    onNewCitySelected(cityName)
                }
            }
    }

    private fun requestNewData(selectedCityLocation: UiCityLocation?) {
        selectedCityLocation?.let {
            if (currentSelectedCity == selectedCityLocation && dailyAdapter.itemCount != 0) {
                weather_swipe_to_refresh.isRefreshing = false
                showMessage(getString(R.string.already_up_to_date))
                return
            }
            currentSelectedCity = selectedCityLocation
            viewModel.fetchDailyForecast(selectedCityLocation.id)
            hourlyAdapter.submitList(null)
            dailyAdapter.submitList(null)
        }
    }

    override fun initRecyclerViews() {
        dailyAdapter.onDailyItemClickListener = this::onDailyItemClickedCallback
        weather_rv_weather_by_day.adapter = dailyAdapter
        weather_rv_weather_by_hour.adapter = hourlyAdapter
    }

    override fun onNewCitySelected(cityName: String) {
        val city = viewModel.availableCities.value?.find { cityName == it.name }
        requestNewData(city)
    }

    override fun renderCurrentDegree(item: UiDailyWeather) {
        weather_tv_current_degree.text = getString(R.string.degree_value, item.temperature)
        weather_tv_current_range_degree.text = item.minMaxTemperature.toString()
        weather_iv_current_weather_state.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                item.drawableResourceId
            )
        )
    }

    override fun showHourlyData(shouldShow: Boolean) {
        if (shouldShow) {
            weather_tv_error_no_forecast.makeGone()
            weather_rv_weather_by_hour.makeVisible()
        } else {
            weather_tv_error_no_forecast.makeVisible()
            weather_rv_weather_by_hour.makeInvisible()
        }
    }

    private fun onDailyItemClickedCallback(item: UiDailyWeather, isFirstItem: Boolean) {
        renderCurrentDegree(item)
        showHourlyData(isFirstItem)
    }


    override fun renderDailyList(list: List<UiDailyWeather>) {
        dailyAdapter.submitList(list)
        //Since we're only in portrait mode, I believe it's okay to use view handler and register a Runnable to it
        handler?.post {
            weather_rv_weather_by_day.findViewHolderForAdapterPosition(0)?.itemView?.callOnClick()
        }
    }

    override fun renderHourlyList(list: List<UiHourlyWeather>) {
        hourlyAdapter.submitList(list)
    }

    override fun renderCitySpinner(list: List<UiCityLocation>) {
        weather_spinner_city_picker.adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_spinner_weather_main,
                list.map { it.name }).apply {
                setDropDownViewResource(R.layout.item_spinner_weather_drop)
            }
    }

    override fun renderScreenBackgroundByDayTimeState(dayTimeState: DateTimeUtil.DayTimeState) {
        val backgroundId = when (dayTimeState) {
            DateTimeUtil.DayTimeState.DAWN ->
                R.drawable.bg_dawn_gradient
            DateTimeUtil.DayTimeState.MORNING ->
                R.drawable.bg_morning_gradient
            DateTimeUtil.DayTimeState.NOON ->
                R.drawable.bg_noon_gradient
            DateTimeUtil.DayTimeState.EVENING ->
                R.drawable.bg_evening_gradient
            DateTimeUtil.DayTimeState.NIGHT ->
                R.drawable.bg_night_gradient
        }
        weather_cl_container.background = ContextCompat.getDrawable(requireContext(), backgroundId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler = null
    }
}


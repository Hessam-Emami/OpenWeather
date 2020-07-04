package com.emami.openweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.coroutines.delay

class WeatherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        figureCurrentTimeState()
    }

    private fun figureCurrentTimeState() {
        lifecycleScope.launchWhenResumed {
            var i = 0
            repeat(24) {
//                val background = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                val background = when (i) {
                    in 0..6 -> {
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_dawn_gradient)
                    }
                    in 7..11 -> {
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_morning_gradient)
                    }
                    in 12..15 -> {
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_noon_gradient)
                    }
                    in 17..19 -> {
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_evening_gradient)
                    }
                    in 20..23 -> {
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_night_gradient)
                    }
                    else -> {
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_morning_gradient)
                    }
                }
                weatherFrameLayout.background = background
                i++
                delay(1000)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WeatherFragment()
    }
}
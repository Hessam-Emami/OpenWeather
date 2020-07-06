package com.emami.openweather.forecast.ui.model

class UiMinMaxTemperature(private val min: Int, private val max: Int) {
    override fun toString(): String {
        return "$max°/$min°"
    }
}
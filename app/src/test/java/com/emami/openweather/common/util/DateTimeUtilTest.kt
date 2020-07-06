package com.emami.openweather.common.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class DateTimeUtilTest {


    @Test
    fun `should return success with formatLocalDateTimeToHourAmPm`() {
        val testCase = LocalDateTime.of(2020, 11, 29, 23, 11)
        val result = DateTimeUtil.formatLocalDateTimeToHourAmPm(testCase)
        assertEquals("11PM", result)
    }

    @Test
    fun `should return success with formatLocalDateTimeToHourOfDay`() {
        val testCase = LocalDateTime.of(2020, 11, 29, 23, 11)
        val result = DateTimeUtil.formatLocalDateTimeToHourOfDay(testCase)
        assertEquals("23", result)
    }

    @Test
    fun `should return success with getCurrentDayTimeState`() {
        val case1 = DateTimeUtil.getCurrentDayTimeState(0)
        assertEquals(DateTimeUtil.DayTimeState.DAWN, case1)

        //failing input test
        val case2 = DateTimeUtil.getCurrentDayTimeState(55)
        assertEquals(DateTimeUtil.DayTimeState.MORNING, case2)

        val case3 = DateTimeUtil.getCurrentDayTimeState(17)
        assertEquals(DateTimeUtil.DayTimeState.EVENING, case3)

        val case4 = DateTimeUtil.getCurrentDayTimeState(20)
        assertEquals(DateTimeUtil.DayTimeState.NIGHT, case4)

        val case5 = DateTimeUtil.getCurrentDayTimeState(14)
        assertEquals(DateTimeUtil.DayTimeState.NOON, case5)
    }
}
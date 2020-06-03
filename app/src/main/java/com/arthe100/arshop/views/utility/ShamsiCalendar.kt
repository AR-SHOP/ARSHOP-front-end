package com.arthe100.arshop.views.utility

import java.time.LocalDate
import kotlin.collections.ArrayList

public class ShamsiCalendar(private var miladiDate : LocalDate) {

    var weekDayName = ""
    var monthName = ""
    var day = 0
    var month = 0
    var year = 0

    public fun currentShamsiDate() : ArrayList<Int> {
        calcSolarCalendar(miladiDate)
        return arrayListOf(year, month, day)
    }


    private fun calcSolarCalendar(MiladiDate: LocalDate) {
        val ld: Int
        val miladiYear: Int = MiladiDate.year + 1900
        val miladiMonth: Int = MiladiDate.monthValue + 1
        val miladiDate: Int = MiladiDate.dayOfYear
        val WeekDay: Int = MiladiDate.dayOfWeek.value
        val buf1 = IntArray(12)
        val buf2 = IntArray(12)
        buf1[0] = 0
        buf1[1] = 31
        buf1[2] = 59
        buf1[3] = 90
        buf1[4] = 120
        buf1[5] = 151
        buf1[6] = 181
        buf1[7] = 212
        buf1[8] = 243
        buf1[9] = 273
        buf1[10] = 304
        buf1[11] = 334
        buf2[0] = 0
        buf2[1] = 31
        buf2[2] = 60
        buf2[3] = 91
        buf2[4] = 121
        buf2[5] = 152
        buf2[6] = 182
        buf2[7] = 213
        buf2[8] = 244
        buf2[9] = 274
        buf2[10] = 305
        buf2[11] = 335
        if (miladiYear % 4 != 0) {
            day = buf1[miladiMonth - 1] + miladiDate
            if (day > 79) {
                day -= 79
                if (day <= 186) {
                    when (day % 31) {
                        0 -> {
                            month = day / 31
                            day = 31
                        }
                        else -> {
                            month = day / 31 + 1
                            day %= 31
                        }
                    }
                    year = miladiYear - 621
                } else {
                    day -= 186
                    when (day % 30) {
                        0 -> {
                            month = day / 30 + 6
                            day = 30
                        }
                        else -> {
                            month = day / 30 + 7
                            day %= 30
                        }
                    }
                    year = miladiYear - 621
                }
            } else {
                ld = if (miladiYear > 1996 && miladiYear % 4 == 1) {
                    11
                } else {
                    10
                }
                day += ld
                when (day % 30) {
                    0 -> {
                        month = day / 30 + 9
                        day = 30
                    }
                    else -> {
                        month = day / 30 + 10
                        day %= 30
                    }
                }
                year = miladiYear - 622
            }
        } else {
            day = buf2[miladiMonth - 1] + miladiDate
            ld = if (miladiYear >= 1996) {
                79
            } else {
                80
            }
            if (day > ld) {
                day -= ld
                if (day <= 186) {
                    when (day % 31) {
                        0 -> {
                            month = day / 31
                            day = 31
                        }
                        else -> {
                            month = day / 31 + 1
                            day %= 31
                        }
                    }
                    year = miladiYear - 621
                } else {
                    day -= 186
                    when (day % 30) {
                        0 -> {
                            month = day / 30 + 6
                            day = 30
                        }
                        else -> {
                            month = day / 30 + 7
                            day %= 30
                        }
                    }
                    year = miladiYear - 621
                }
            } else {
                day += 10
                when (day % 30) {
                    0 -> {
                        month = day / 30 + 9
                        day = 30
                    }
                    else -> {
                        month = day / 30 + 10
                        day %= 30
                    }
                }
                year = miladiYear - 622
            }
        }
        when (month) {
            1 -> monthName = "فروردين"
            2 -> monthName = "ارديبهشت"
            3 -> monthName = "خرداد"
            4 -> monthName = "تير"
            5 -> monthName = "مرداد"
            6 -> monthName = "شهريور"
            7 -> monthName = "مهر"
            8 -> monthName = "آبان"
            9 -> monthName = "آذر"
            10 -> monthName = "دي"
            11 -> monthName = "بهمن"
            12 -> monthName = "اسفند"
        }
        when (WeekDay) {
            0 -> this.weekDayName = "يکشنبه"
            1 -> this.weekDayName = "دوشنبه"
            2 -> this.weekDayName = "سه شنبه"
            3 -> this.weekDayName = "چهارشنبه"
            4 -> this.weekDayName = "پنج شنبه"
            5 -> this.weekDayName = "جمعه"
            6 -> this.weekDayName = "شنبه"
        }
    }
}

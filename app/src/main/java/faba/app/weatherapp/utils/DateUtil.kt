package faba.app.weatherapp.utils

import java.text.SimpleDateFormat

object DateUtil {

    fun getDay(theDate: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.parse(theDate)
        return SimpleDateFormat("EEEE").format(date)
    }
}
package faba.app.weatherapp.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import faba.app.weatherapp.R
import faba.app.weatherapp.db.CurrentWeatherData
import faba.app.weatherapp.utils.Constants.TXT_CLOUDY
import faba.app.weatherapp.utils.Constants.TXT_RAINY
import faba.app.weatherapp.utils.Constants.TXT_SUNNY
import faba.app.weatherapp.ui.theme.WeatherAppTheme
import faba.app.weatherapp.utils.Constants.DEGREE
import kotlin.math.roundToInt
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import faba.app.weatherapp.db.ForecastWeatherData
import faba.app.weatherapp.models.forecast.ForecastDays
import faba.app.weatherapp.utils.DateUtil


@Composable
fun WeatherScreen(
    currentWeatherDataList: List<CurrentWeatherData>,
    weatherForecastList: List<ForecastWeatherData>
) {

    val systemUiController = rememberSystemUiController()


    CircularProgressIndicator()

    var currentCondition = painterResource(R.drawable.sea_sunny)
    var currentBg = colorResource(R.color.bg_sea_day)
    var currentTxt = "CLOUDY"
    lateinit var temp: String
    var condition = ""
    lateinit var city: String

    val sunny = painterResource(R.drawable.sea_sunny)
    val cloudy = painterResource(R.drawable.sea_cloudy)
    val rainy = painterResource(R.drawable.sea_rainy)
    val bgSunny = colorResource(R.color.bg_sea_day)
    val bgCloudy = colorResource(R.color.bg_sea_day_cloudy)
    val bgRainy = colorResource(R.color.bg_sea_day_rainy)
    val bgSunnyStatusBar = colorResource(R.color.header)

    if (currentWeatherDataList.isNotEmpty()) {
        val weatherData = currentWeatherDataList[0].weatherData
        val mainObj = weatherData.main
        temp = mainObj.temp.roundToInt().toString()

        val weatherArray = weatherData.weather
        condition = weatherArray[0].main
        city = weatherData.name

    } else {
        temp = "0"
        city = ""
        condition = "Clear"
    }



    when (condition) {
        "Clear" -> {
            currentCondition = sunny
            currentBg = bgSunny
            currentTxt = TXT_SUNNY
        }
        "Clouds" -> {
            currentCondition = cloudy
            currentBg = bgCloudy
            currentTxt = TXT_CLOUDY
        }
        "Rain" -> {
            currentCondition = rainy
            currentBg = bgRainy
            currentTxt = TXT_RAINY
        }
    }

    SideEffect {

        if (condition == "Clear") {
            currentBg = bgSunnyStatusBar
        }

        systemUiController.setStatusBarColor(
            color = currentBg
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box {
            Image(
                painter = currentCondition,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
            )

            Column(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(bottom = 95.dp)
            ) {
                Text(
                    text = city,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "$temp$DEGREE",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 60.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = currentTxt,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 35.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }


        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = currentBg)
        ) {

            CurrentWeatherData(currentWeatherDataList)
            ForeCastWeatherData(weatherForecastList)

        }

    }

}

@Composable
fun CurrentWeatherData(currentWeatherDataList: List<CurrentWeatherData>) {

    lateinit var temp: String
    lateinit var tempMin: String
    lateinit var tempMax: String

    if (currentWeatherDataList.isNotEmpty()) {
        val mainObj = currentWeatherDataList[0].weatherData.main
        temp = mainObj.temp.roundToInt().toString()
        tempMin = mainObj.temp_min.roundToInt().toString()
        tempMax = mainObj.temp_max.roundToInt().toString()
    } else {
        temp = "0"
        tempMin = "0"
        tempMax = "0"
    }



    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {

        Column(
            modifier = Modifier.padding(start = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$tempMin$DEGREE",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,

                )

            Text(
                text = "min",
                color = Color.White,
                fontSize = 20.sp,

                )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$temp$DEGREE",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
            )

            Text(
                text = "Current",
                color = Color.White,
                fontSize = 20.sp,

                )
        }

        Column(
            modifier = Modifier.padding(end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "$tempMax$DEGREE",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
            )

            Text(
                text = "max",
                color = Color.White,
                fontSize = 20.sp,

                )
        }


    }

    Divider(
        color = Color.White,
        thickness = 1.dp,
        modifier = Modifier.padding(vertical = 10.dp)
    )

}

@Composable
fun ForeCastWeatherData(weatherForecastList: List<ForecastWeatherData>) {

    var forecastList: MutableList<ForecastDays> = mutableListOf()
    if (weatherForecastList.isNotEmpty()) {
        forecastList = weatherForecastList[0].forecastData.list.toMutableList()
    }

    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 20.dp)
    ) {
        items(forecastList) { forecastItem ->
            if (forecastItem.dt_txt.contains("12:00:00"))
                ForeCastWeatherDataItem(forecastItem)
        }
    }

}


@Composable
fun ForeCastWeatherDataItem(forecastDays: ForecastDays) {

    val sunny = painterResource(R.drawable.clear)
    val cloudy = painterResource(R.drawable.partlysunny)
    val rainy = painterResource(R.drawable.rain)
    var currentCondition = painterResource(R.drawable.sea_sunny)

    val weatherArray = forecastDays.weather
    val condition = weatherArray[0].main

    val mainObj = forecastDays.main
    val currentTemp = mainObj.temp.roundToInt().toString()

    val dayOfWeek = DateUtil.getDay(forecastDays.dt_txt)


    when (condition) {
        "Clear" -> {
            currentCondition = sunny
        }
        "Clouds" -> {
            currentCondition = cloudy
        }
        "Rain" -> {
            currentCondition = rainy
        }
    }


    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

        val (icon, day, temp) = createRefs()

        Text(
            text = dayOfWeek,
            color = Color.White,
            fontSize = 22.sp,
            modifier = Modifier
                .constrainAs(day) {
                    start.linkTo(parent.start)
                }
                .padding(start = 20.dp)
        )

        Image(
            painter = currentCondition,
            contentDescription = "clear",
            modifier = Modifier
                .size(width = 27.dp, height = 27.dp)
                .padding(start = 5.dp, top = 5.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = "$currentTemp$DEGREE",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            modifier = Modifier
                .constrainAs(temp) {
                    end.linkTo(parent.end)
                }
                .padding(end = 20.dp)
        )


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherScreen(listOf(), listOf())
    }

}
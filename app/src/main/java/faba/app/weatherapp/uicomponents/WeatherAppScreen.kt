package faba.app.weatherapp.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun WeatherScreen(currentWeatherDataList: List<CurrentWeatherData>) {

    var currentCondition = painterResource(R.drawable.sea_sunny)
    var currentBg = colorResource(R.color.bg_sea_day)
    var currentTxt = "SUNNY"
    lateinit var temp: String
    lateinit var condition: String


    val sunny = painterResource(R.drawable.sea_sunny)
    val cloudy = painterResource(R.drawable.sea_cloudy)
    val rainy = painterResource(R.drawable.sea_rainy)
    val bgSunny = colorResource(R.color.bg_sea_day)
    val bgCloudy = colorResource(R.color.bg_sea_day_cloudy)
    val bgRainy = colorResource(R.color.bg_sea_day_rainy)

    if (currentWeatherDataList.isNotEmpty()) {
        val mainObj = currentWeatherDataList[0].weatherData.main
        temp = ((mainObj.temp - 273.15).roundToInt()).toString()

        val weatherArray = currentWeatherDataList[0].weatherData.weather
        condition = weatherArray[0].main

    } else {
        temp = "0"
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
                    .padding(bottom = 70.dp)
            ) {
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
        temp = ((mainObj.temp - 273.15).roundToInt()).toString()
        tempMin = ((mainObj.temp_min - 273.15).roundToInt()).toString()
        tempMax = ((mainObj.temp_max - 273.15).roundToInt()).toString()
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherScreenPreview() {
    WeatherAppTheme {
        //WeatherScreen("sunny")
    }

}
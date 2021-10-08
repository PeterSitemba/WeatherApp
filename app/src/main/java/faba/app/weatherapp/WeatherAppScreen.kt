package faba.app.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import faba.app.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherScreen(condition: String) {

    var currentCondition = painterResource(R.drawable.sea_sunny)
    var currentBg = colorResource(R.color.bg_sea_day)
    var currentTxt ="SUNNY"

    val sunny = painterResource(R.drawable.sea_sunny)
    val cloudy = painterResource(R.drawable.sea_cloudy)
    val rainy = painterResource(R.drawable.sea_rainy)
    val bgSunny = colorResource(R.color.bg_sea_day)
    val bgCloudy = colorResource(R.color.bg_sea_day_cloudy)
    val bgRainy = colorResource(R.color.bg_sea_day_rainy)
    val txtSunny = "SUNNY"
    val txtCloudy = "CLOUDY"
    val txtRainy = "RAINY"

    when (condition) {
        "sunny" -> {
            currentCondition = sunny
            currentBg = bgSunny
            currentTxt = txtSunny
        }
        "cloudy" -> {
            currentCondition = cloudy
            currentBg = bgCloudy
            currentTxt = txtCloudy
        }
        "rainy" -> {
            currentCondition = rainy
            currentBg = bgRainy
            currentTxt = txtRainy
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

            Column(modifier = Modifier.align(alignment = Alignment.Center).padding(bottom = 70.dp)) {
                Text(
                    text = "25",
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
                //.offset(y = (-8).dp)
        ) {

        }

    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherScreen("cloudy")
    }

}
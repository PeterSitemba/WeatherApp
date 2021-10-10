package faba.app.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import faba.app.weatherapp.db.CurrentWeatherData
import faba.app.weatherapp.ui.theme.WeatherAppTheme
import faba.app.weatherapp.viewmodel.WeatherViewModel
import androidx.compose.runtime.livedata.observeAsState
import faba.app.weatherapp.uicomponents.WeatherScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            weatherViewModel.getCurrentWeatherForecast(
                -1.278319,
                36.784644,
                "2a5ac244383461b7c2225b066ef65029"
            )

            weatherViewModel.getRowCount()?.observe(this, {
                //Log.e("No of rows", it.toString())
            })

            weatherViewModel.weatherForecastResponse.observe(this, {

                weatherViewModel.insertCurrentWeatherData(
                    CurrentWeatherData(
                        it.id,
                        it
                    )
                )
            })


            WeatherAppTheme {
                WeatherActivityScreen(weatherViewModel)
            }

/*
            WeatherAppTheme {
                WeatherScreen("sunny")
            }
*/
        }
    }
}

@Composable
fun WeatherActivityScreen(weatherViewModel: WeatherViewModel) {

    val weatherItems: List<CurrentWeatherData> by weatherViewModel.roomCurrentWeatherData()
        .observeAsState(listOf())

    WeatherScreen("sunny", weatherItems)


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        //WeatherScreen("sunny")
    }
}
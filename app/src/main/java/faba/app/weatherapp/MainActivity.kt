package faba.app.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import faba.app.weatherapp.db.CurrentWeatherData
import faba.app.weatherapp.ui.theme.WeatherAppTheme
import faba.app.weatherapp.viewmodel.WeatherViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import faba.app.weatherapp.db.ForecastWeatherData
import faba.app.weatherapp.uicomponents.WeatherScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val PERMISSION_ID = 42
    lateinit var latitude: String
    lateinit var longitude: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val weatherViewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            getLastLocation()

            WeatherAppTheme {
                WeatherActivityScreen(weatherViewModel)
            }

            weatherViewModel.errorMessage.observe(this, {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

        }
    }

    private fun initCurrentWeather(lat: Double, lon: Double) {

        Log.e("lat", lat.toString())
        Log.e("lon", lon.toString())

        weatherViewModel.getCurrentWeather(
            lat,
            lon,
            "2a5ac244383461b7c2225b066ef65029"
        )

        weatherViewModel.getRowCount()?.observe(this, {
            //Log.e("No of rows", it.toString())
        })

        weatherViewModel.currentWeatherResponse.observe(this, {
            weatherViewModel.insertCurrentWeatherData(
                CurrentWeatherData(
                    1,
                    it
                )
            )

        })
    }

    private fun initWeatherForecast(lat: Double, lon: Double) {
        weatherViewModel.getWeatherForecast(
            lat,
            lon,
            "2a5ac244383461b7c2225b066ef65029"
        )

        weatherViewModel.getForecastRowCount()?.observe(this, {
            //Log.e("No of rows", it.toString())
        })

        weatherViewModel.weatherForecastResponse.observe(this, {
            weatherViewModel.insertForecast(
                ForecastWeatherData(
                    1,
                    it
                )
            )

        })
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        latitude = location.latitude.toString()
                        initCurrentWeather(location.latitude, location.longitude)
                        initWeatherForecast(location.latitude, location.longitude)

                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            latitude = mLastLocation.latitude.toString()
            initCurrentWeather(mLastLocation.latitude, mLastLocation.longitude)
            initWeatherForecast(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}


@Composable
fun WeatherActivityScreen(weatherViewModel: WeatherViewModel) {

    val weatherItems: List<CurrentWeatherData> by weatherViewModel.roomCurrentWeatherData()
        .observeAsState(listOf())

    val weatherForecastItems: List<ForecastWeatherData> by weatherViewModel.roomWeatherForecastData()
        .observeAsState(listOf())

    WeatherAppTheme {
        WeatherScreen(weatherItems, weatherForecastItems)
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        //WeatherScreen("sunny")
    }
}
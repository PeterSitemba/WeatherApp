package faba.app.weatherapp.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import faba.app.weatherapp.db.CurrentWeatherData
import faba.app.weatherapp.db.ForecastWeatherData
import faba.app.weatherapp.models.current.WeatherData
import faba.app.weatherapp.models.forecast.ForecastData
import faba.app.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.*
import java.lang.Exception

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val currentWeatherResponse = MutableLiveData<WeatherData>()
    val weatherForecastResponse = MutableLiveData<ForecastData>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")

    }

    //current
    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        appId: String
    ) {
        viewModelScope.launch {
            try {
                loading.value = true
                withContext(Dispatchers.IO + exceptionHandler) {
                    val response = weatherRepository.getCurrentWeather(lat, lon, appId)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            currentWeatherResponse.postValue(response.body())
                            loading.value = false
                        } else {
                            onError("Error : ${response.message()}")
                        }
                    }
                }

            } catch (e: Exception) {
                onError(e.message!!)
            }
        }
    }

    //forecast
    fun getWeatherForecast(
        lat: Double,
        lon: Double,
        appId: String
    ) {
        viewModelScope.launch {
            try {
                loading.value = true
                withContext(Dispatchers.IO + exceptionHandler) {
                    val response = weatherRepository.getWeatherForecast(lat, lon, appId)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            weatherForecastResponse.postValue(response.body())
                            loading.value = false
                        } else {
                            onError("Error : ${response.message()}")
                        }
                    }
                }

            } catch (e: Exception) {
                onError(e.message!!)
            }
        }
    }



    //current

    fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData) = viewModelScope.launch {
        weatherRepository.insert(currentWeatherData)
    }


    fun roomCurrentWeatherData(): LiveData<List<CurrentWeatherData>> {
        return weatherRepository.roomCurrentWeatherList.asLiveData()
    }

    fun getRowCount(): LiveData<Int?>? = weatherRepository.getRowCount?.asLiveData()


    //forecast
    fun insertForecast(forecastWeatherData: ForecastWeatherData) = viewModelScope.launch {
        weatherRepository.insertForecast(forecastWeatherData)
    }

    fun roomWeatherForecastData(): LiveData<List<ForecastWeatherData>> {
        return weatherRepository.roomWeatherForecastList.asLiveData()
    }

    fun getForecastRowCount(): LiveData<Int?>? = weatherRepository.getForecastRowCount?.asLiveData()


    //Error
    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

}
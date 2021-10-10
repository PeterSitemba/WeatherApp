package faba.app.weatherapp.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import faba.app.weatherapp.db.CurrentWeatherData
import faba.app.weatherapp.model.WeatherData
import faba.app.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.*

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()
    val weatherForecastResponse = MutableLiveData<WeatherData>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")

    }

    fun getCurrentWeatherForecast(
        lat: Double,
        lon: Double,
        appId: String
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO + exceptionHandler) {
                val response = weatherRepository.getCurrentWeatherForecast(lat, lon, appId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        weatherForecastResponse.postValue(response.body())
                        loading.value = false
                    } else {
                        onError("Error : ${response.message()}")
                    }
                }
            }
        }
    }

    fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData) = viewModelScope.launch {
        weatherRepository.insert(currentWeatherData)
    }


    fun roomCurrentWeatherData(): LiveData<List<CurrentWeatherData>> {
        return weatherRepository.roomCurrentWeatherList.asLiveData()
    }

    fun getRowCount(): LiveData<Int?>? = weatherRepository.getRowCount?.asLiveData()

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

}
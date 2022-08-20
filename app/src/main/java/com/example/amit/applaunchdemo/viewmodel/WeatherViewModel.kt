package com.example.amit.applaunchdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amit.applaunchdemo.model.Weather
import com.example.amit.applaunchdemo.repository.WeatherRepository
import kotlinx.coroutines.*

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val temp = MutableLiveData<Double>()
    val weatherType = MutableLiveData<List<Weather>>()
    val humidity = MutableLiveData<Int>()
    val windSpeed = MutableLiveData<Double>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getWeatherData() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllWeatherData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.let {
                        temp.postValue(response.body()?.current?.temp ?: 0.0)
                        weatherType.postValue(response.body()?.current?.weather)
                        humidity.postValue(response.body()?.current?.humidity ?: 0)
                        windSpeed.postValue(response.body()?.current?.windSpeed ?: 0.0)
                    }

                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}
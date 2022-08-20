package com.example.amit.applaunchdemo.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amit.applaunchdemo.repository.UserListRepository
import com.example.amit.applaunchdemo.repository.WeatherRepository


class WeatherDataModelFactory(private val repository: WeatherRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(WeatherRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {

        }
        return super.create(modelClass)
    }
}

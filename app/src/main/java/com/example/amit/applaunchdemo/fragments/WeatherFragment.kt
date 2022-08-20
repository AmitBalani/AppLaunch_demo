package com.example.amit.applaunchdemo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.amit.applaunchdemo.R
import com.example.amit.applaunchdemo.database.base.UserDatabase
import com.example.amit.applaunchdemo.databinding.FragmentLoginBinding
import com.example.amit.applaunchdemo.databinding.FragmentWeatherBinding
import com.example.amit.applaunchdemo.databinding.FragmentWelcomeBinding
import com.example.amit.applaunchdemo.repository.UserListRepository
import com.example.amit.applaunchdemo.repository.WeatherRepository
import com.example.amit.applaunchdemo.retrofit.RetrofitService
import com.example.amit.applaunchdemo.storage.AppPref
import com.example.amit.applaunchdemo.viewmodel.UserListViewModel
import com.example.amit.applaunchdemo.viewmodel.WeatherViewModel
import com.example.amit.applaunchdemo.viewmodelfactory.UserViewModelFactory
import com.example.amit.applaunchdemo.viewmodelfactory.WeatherDataModelFactory

class WeatherFragment : Fragment() {

    private val TAG = "WeatherFragment"

    lateinit var binding: FragmentWeatherBinding
    lateinit var viewModel: WeatherViewModel
    private lateinit var repository: WeatherRepository
    private lateinit var factory: WeatherDataModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        return view
    }

    private fun init() {
        initializeAllObjects()
        observeAllData()
        loadData()
        clickListeners()
    }

    private fun initializeAllObjects() {
        val retrofitService = RetrofitService.getInstance()
        repository = WeatherRepository(retrofitService)
        factory = WeatherDataModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
    }

    private fun observeAllData() {
        viewModel.temp.observe(requireActivity()) {
            val temp = getString(R.string.temp,it)
            binding.tvTemp.text = temp
        }

        viewModel.weatherType.observe(requireActivity(), Observer { list->
            run {
                for (model in list) {
                    val weather = getString(R.string.weatherName,model.main)
                    val weatherDescription = getString(R.string.weatherName,model.description)
                    binding.tvWeatherName.text = weather
                    binding.tvWeatherDescription.text = weatherDescription
                }
            }
        })

        viewModel.humidity.observe(requireActivity()) {
            val humidity = getString(R.string.humidity,it)
            binding.tvHumidity.text = humidity
        }

        viewModel.windSpeed.observe(requireActivity()) {
            val windSpeed = getString(R.string.windSpeed,it)
            binding.tvWindSpeed.text = windSpeed
        }

        viewModel.errorMessage.observe(requireActivity()) {
            binding.tvError.visibility = View.VISIBLE
            binding.llContent.visibility = View.GONE
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(requireActivity(), Observer {
            if (it) {
                binding.llLoading.visibility = View.VISIBLE
                binding.llContent.visibility = View.GONE
            } else {
                binding.llLoading.visibility = View.GONE
                binding.llContent.visibility = View.VISIBLE
            }
        })
    }

    private fun loadData() {
        viewModel.getWeatherData()
    }

    private fun clickListeners() {

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.clLogout.setOnClickListener {
            AppPref.getInstance().setValue(AppPref.IS_USER_LOGGED_IN,false)
            findNavController().navigate(R.id.actionWeatherDataToLogin)
        }
    }

}
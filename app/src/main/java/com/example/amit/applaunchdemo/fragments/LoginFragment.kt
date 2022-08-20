package com.example.amit.applaunchdemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.amit.applaunchdemo.R
import com.example.amit.applaunchdemo.databinding.FragmentLoginBinding
import com.example.amit.applaunchdemo.interfaces.ButtonClickListener
import com.example.amit.applaunchdemo.storage.AppPref
import com.example.amit.applaunchdemo.utils.SUCCESS
import com.example.amit.applaunchdemo.viewmodel.LoginViewModel

class LoginFragment : Fragment(), ButtonClickListener {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        return view
    }

    private fun init() {
        initializeViewModel()
        observeData()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        binding.buttonClick = this
    }

    private fun observeData() {
        viewModel.getLogInResult().observe(requireActivity(), Observer { result ->
            if (result == SUCCESS) {
                AppPref.getInstance().setValue(AppPref.IS_USER_LOGGED_IN,true)
                findNavController().navigate(R.id.actionLoginToUserList)
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.invalid_credentials_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onClick() {
        viewModel.performValidation()
    }


}

package com.example.amit.applaunchdemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.amit.applaunchdemo.R
import com.example.amit.applaunchdemo.databinding.FragmentWelcomeBinding
import com.example.amit.applaunchdemo.storage.AppPref

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        return view
    }

    private fun init() {
        checkIfUserAlreadyLoggedIn()
        onClickListeners()
    }

    private fun checkIfUserAlreadyLoggedIn() {
        if (AppPref.getInstance().getValue(AppPref.IS_USER_LOGGED_IN,false) == true){
            findNavController().navigate(R.id.actionWelcomeToUserList)
        }
    }


    private fun onClickListeners() {
        binding.clLogin.setOnClickListener {
            findNavController().navigate(R.id.actionWelcomeToLogin)
        }
    }

}
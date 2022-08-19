package com.example.amit.applaunchdemo.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.amit.applaunchdemo.R


class UserListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override
                fun handleOnBackPressed() {
                    requireActivity().finishAndRemoveTask()
                }
            })
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }



}
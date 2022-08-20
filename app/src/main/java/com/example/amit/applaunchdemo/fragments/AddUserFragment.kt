package com.example.amit.applaunchdemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.amit.applaunchdemo.R
import com.example.amit.applaunchdemo.database.base.UserDatabase
import com.example.amit.applaunchdemo.database.entities.UserModel
import com.example.amit.applaunchdemo.databinding.FragmentAddUserBinding
import com.example.amit.applaunchdemo.interfaces.ButtonClickListener
import com.example.amit.applaunchdemo.repository.UserListRepository
import com.example.amit.applaunchdemo.utils.SUCCESS
import com.example.amit.applaunchdemo.viewmodel.AddUserViewModel
import com.example.amit.applaunchdemo.viewmodel.UserListViewModel
import com.example.amit.applaunchdemo.viewmodelfactory.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUserFragment : Fragment(), ButtonClickListener {


    private lateinit var binding: FragmentAddUserBinding
    private lateinit var viewModel: AddUserViewModel
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var userDatabase: UserDatabase
    private lateinit var repository: UserListRepository
    private lateinit var factory: UserViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        return view
    }

    private fun init() {
        initializeViewModel()
        initializeAllObjects()
        observeData()
        clickListener()
    }


    private fun initializeAllObjects() {
        userDatabase = UserDatabase.getInstance(requireActivity())!!
        repository = UserListRepository(userDatabase)
        factory = UserViewModelFactory(repository)
        userListViewModel = ViewModelProvider(this, factory)[UserListViewModel::class.java]
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
        binding.viewModel = viewModel
        binding.buttonClick = this
    }


    private fun observeData() {
        viewModel.getUserValidation().observe(requireActivity()) { result ->
            if (result == SUCCESS) {
                addUserDataAndNavigateToUserList()
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.valid_info_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addUserDataAndNavigateToUserList() {
        CoroutineScope(Dispatchers.Main).launch {
            userListViewModel.insertUser(
                UserModel(
                    emailAddress = viewModel.emailId,
                    firstName = viewModel.firstName,
                    lastName = viewModel.lastName
                )
            ).also {
                findNavController().popBackStack()
            }
        }

    }

    private fun clickListener() {

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.clCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun onClick() {
        viewModel.performValidation()
    }


}
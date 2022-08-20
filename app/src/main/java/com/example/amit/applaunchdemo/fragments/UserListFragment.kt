package com.example.amit.applaunchdemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amit.applaunchdemo.R
import com.example.amit.applaunchdemo.adapter.UserListAdapter
import com.example.amit.applaunchdemo.database.base.UserDatabase
import com.example.amit.applaunchdemo.database.entities.UserModel
import com.example.amit.applaunchdemo.databinding.FragmentUserListBinding
import com.example.amit.applaunchdemo.interfaces.UserItemClickListener
import com.example.amit.applaunchdemo.repository.UserListRepository
import com.example.amit.applaunchdemo.utils.SwipeToDeleteCallback
import com.example.amit.applaunchdemo.utils.showMultiPurposeDialog
import com.example.amit.applaunchdemo.viewmodel.UserListViewModel
import com.example.amit.applaunchdemo.viewmodelfactory.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class UserListFragment : Fragment(), UserItemClickListener {

    private lateinit var binding: FragmentUserListBinding
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var viewModel: UserListViewModel
    private lateinit var userDatabase: UserDatabase
    private lateinit var repository: UserListRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var userListAdapter: UserListAdapter
    private var lstUserList: MutableList<UserModel> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        return view
    }

    private fun init() {
        initializeAllObjects()
        setRecyclerView()
        fetchAllUserData()
        addTouchListener()
        onClickListener()
        onBackPressCall()
    }


    private fun initializeAllObjects() {
        userDatabase = UserDatabase.getInstance(requireActivity())!!
        repository = UserListRepository(userDatabase)
        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[UserListViewModel::class.java]
    }

    private fun setRecyclerView() {
        binding.rvUserList.layoutManager = LinearLayoutManager(requireActivity())
        userListAdapter = UserListAdapter(requireActivity(), lstUserList, this)
        binding.rvUserList.adapter = userListAdapter
    }

    private fun fetchAllUserData() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllUsers().observe(requireActivity()) { lstUserList ->
                run {
                    this@UserListFragment.lstUserList = lstUserList
                    userListAdapter.updateList(lstUserList)
                    if (lstUserList.isEmpty()) {
                        binding.llEmptyView.visibility = View.VISIBLE
                    } else {
                        binding.llEmptyView.visibility = View.GONE
                    }
                }
            }
        }
    }


    private fun onClickListener() {

        binding.ivBack.setOnClickListener {
            requireActivity().finishAndRemoveTask()
        }

        binding.ivAddUser.setOnClickListener {
            findNavController().navigate(R.id.actionUserListToAddUser)
        }
    }


    private fun onBackPressCall() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override
                fun handleOnBackPressed() {
                    requireActivity().finishAndRemoveTask()
                }
            })
    }


    private fun addTouchListener() {

        var model: UserModel?
        var position: Int?
        val swipeHandler = object : SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                model = lstUserList[viewHolder.adapterPosition]
                position = viewHolder.adapterPosition

                val adapter = binding.rvUserList.adapter as UserListAdapter
                adapter.removeItem(viewHolder.adapterPosition)

                showMultiPurposeDialog(
                    requireActivity(),
                    getString(R.string.delete),
                    true,
                    getString(R.string.delete_msg),
                    {
                        deleteUserFromList(model!!)
                    }, {
                        adapter.restoreItem(model!!, position!!)
                    },
                    getString(R.string.delete),
                    getString(R.string.cancel)
                )

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvUserList)

    }

    private fun deleteUserFromList(model: UserModel) {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.deleteUser(model)
        }
    }

    override fun userItemClick(position: Int, userModel: UserModel) {
        findNavController().navigate(R.id.actionUserListToWeather)
    }


}
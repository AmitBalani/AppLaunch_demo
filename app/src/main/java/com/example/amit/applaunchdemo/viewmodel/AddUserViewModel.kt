package com.example.amit.applaunchdemo.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amit.applaunchdemo.utils.EMAIL_ID
import com.example.amit.applaunchdemo.utils.ERROR
import com.example.amit.applaunchdemo.utils.PASSWORD
import com.example.amit.applaunchdemo.utils.SUCCESS

class AddUserViewModel : ViewModel() {

    var emailId: String = ""
    var firstName : String = ""
    var lastName : String = ""

    private val addUserValidation = MutableLiveData<Int>()

    fun getUserValidation(): LiveData<Int> = addUserValidation


    fun performValidation() {
        if (firstName.isBlank()) {
            addUserValidation.value = ERROR
            return
        }

        if (lastName.isBlank()) {
            addUserValidation.value = ERROR
            return
        }

        if (!emailId.isValidEmail()) {
            addUserValidation.value = ERROR
            return
        }

        addUserValidation.value = SUCCESS

    }


    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}

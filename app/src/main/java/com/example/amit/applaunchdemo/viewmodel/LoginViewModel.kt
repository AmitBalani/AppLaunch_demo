package com.example.amit.applaunchdemo.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amit.applaunchdemo.utils.EMAIL_ID
import com.example.amit.applaunchdemo.utils.ERROR
import com.example.amit.applaunchdemo.utils.PASSWORD
import com.example.amit.applaunchdemo.utils.SUCCESS

class LoginViewModel : ViewModel() {

    var emailId: String = ""
    var password: String = ""

    private val logInResult = MutableLiveData<Int>()

    fun getLogInResult(): LiveData<Int> = logInResult


    fun performValidation() {
        if (!emailId.isValidEmail()) {
            logInResult.value = ERROR
            return
        }

        if (password.isBlank()) {
            logInResult.value = ERROR
            return
        }

        if (emailId == EMAIL_ID && password == PASSWORD){
            logInResult.value = SUCCESS
            return
        } else {
            logInResult.value = ERROR
            return
        }

    }


    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}

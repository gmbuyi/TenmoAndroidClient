package com.gmbuyi.temno.ui.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RequestViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "All Requests"
    }
    val text: LiveData<String> = _text
}
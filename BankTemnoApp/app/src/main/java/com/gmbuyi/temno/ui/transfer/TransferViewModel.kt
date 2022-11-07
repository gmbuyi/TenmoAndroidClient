package com.gmbuyi.temno.ui.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransferViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = " All Transfers"
    }
    val text: LiveData<String> = _text
}
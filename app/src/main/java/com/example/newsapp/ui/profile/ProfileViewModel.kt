package com.example.newsapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    private val _image = MutableLiveData<String>().apply {
        value="This is Gallery"

    }
    val text: LiveData<String> = _image

}
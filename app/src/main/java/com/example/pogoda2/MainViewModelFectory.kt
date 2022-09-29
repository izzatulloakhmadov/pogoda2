package com.example.pogoda2

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFectory (val networkApi: NetworkApi, val application: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass:Class<T>): T{
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(networkApi, application) as T
        }
        throw IllegalAccessException("")
    }

}
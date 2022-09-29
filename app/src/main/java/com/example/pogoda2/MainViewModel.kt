package com.example.pogoda2

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pogoda2.model.Weather
import kotlinx.coroutines.launch

class MainViewModel (val networkApi:NetworkApi,application: Application):AndroidViewModel(application) {


    val weather:MutableLiveData<NetworkResult<Weather>> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.M)
    fun getWeather(key:String, q:String, days:String) =viewModelScope.launch {
        getWeatherSafeCall(key,q,days)

    }
@RequiresApi(Build.VERSION_CODES.M)
suspend fun getWeatherSafeCall(key:String, q:String, days:String){
    weather.value=NetworkResult.Loading()
        if (hasInternetConnection()){
            val response=networkApi.getForecast(key,q,days)
            if (response.isSuccessful){
                weather.value=NetworkResult.Success(response.body())
            }else{
                weather.value=NetworkResult.Error(response.message())
            }
        }else{
            weather.value=NetworkResult.Error("No internet connection")
        }

}


@RequiresApi(Build.VERSION_CODES.M)
fun hasInternetConnection():Boolean{
    val connectivityManager=
        getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork=connectivityManager.activeNetwork ?: return false
    val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork)?: return false
    return when{
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
        else->false


    }
}





}
package com.example.kotlin_mvvm_retrofit


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AndroidViewModel: ViewModel() {
    private val mService = RetrofitService()
    fun getAndroidData() : MutableLiveData<List<Android>>?{
        Log.e("getAndroidData","yes")
        return mService.loadAndroidData()

    }
}



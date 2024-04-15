package com.example.anmp_hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.anmp_hobbyapp.model.Hobby
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HobbyViewModel(application:Application):AndroidViewModel(application) {
    var hobbiesLD = MutableLiveData<ArrayList<Hobby>>()
    var hobbyDetailLD = MutableLiveData<Hobby>()
    val hobbyLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        hobbyLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.188.132/ANMP/HobbyApp/hobbies.json"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                val sType = object : TypeToken<ArrayList<Hobby>>() {}.type
                val result = Gson().fromJson<List<Hobby>>(it, sType)
                hobbiesLD.value = result as ArrayList<Hobby>?

                Log.d("Success", it)
                loadingLD.value = false
            }, {
                loadingLD.value = false
                hobbyLoadErrorLD.value = false
                Log.d("load error", it.toString())
            }
        )

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun detail(id:String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.188.132/ANMP/HobbyApp/hobby_detail.php?id=${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                hobbyDetailLD.value = Gson().fromJson(it, Hobby::class.java)

                Log.d("Success", it)
            }, {
                Log.d("Error", it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
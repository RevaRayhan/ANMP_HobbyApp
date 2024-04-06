package com.example.anmp_hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.anmp_hobbyapp.model.User
import com.google.gson.Gson
import org.json.JSONObject

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userLD = MutableLiveData<User>()
    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

//    fun login(username:String, password:String) {
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://192.168.188.132/ANMP/HobbyApp/user_login.php"
//
//        val stringRequest = object : StringRequest(
//            Method.POST, url, {response->
//                userLD.value = Gson().fromJson(response, User::class.java)
//
//                Toast.makeText(getApplication(), "Login Successful", Toast.LENGTH_SHORT).show()
//                Log.d("Success", "Response: ${response}")
//            }, {
//                Toast.makeText(getApplication(), "Login Failed", Toast.LENGTH_SHORT).show()
//                Log.d("error", it.toString())
//            }
//        )
//        {
//            override fun getParams(): MutableMap<String, String>? {
//                val params = HashMap<String, String>()
//                params["username"] = username
//                params["password"] = password
//                return params
//            }
//        }
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
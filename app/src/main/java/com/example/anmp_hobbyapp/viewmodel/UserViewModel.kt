package com.example.anmp_hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
    val registerLD = MutableLiveData<Boolean>()
    val updateLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun checkLogin(username:String, password:String) {
        queue = Volley.newRequestQueue(getApplication())
        //IP Change
        val url = "http://192.168.188.132/ANMP/HobbyApp/user_login.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                userLD.value = Gson().fromJson(response, User::class.java)

                Toast.makeText(getApplication(), "Login Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${response}")
            }, {
                Toast.makeText(getApplication(), "Login Failed", Toast.LENGTH_SHORT).show()
                Log.d("login error", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun registerUser(firstName:String, lastName:String, email:String, username:String, password:String, photoURL:String) {
        queue = Volley.newRequestQueue(getApplication())
        //IP Change
        val url = "http://192.168.188.132/ANMP/HobbyApp/user_register.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                registerLD.value = true
                Toast.makeText(getApplication(), "Register Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${response}")
            }, {
                registerLD.value = false
//                Toast.makeText(getApplication(), "Register Failed", Toast.LENGTH_SHORT).show()
                Log.d("Register error", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["first_name"] = firstName
                params["last_name"] = lastName
                params["email"] = email
                params["username"] = username
                params["password"] = password
                params["photo_url"] = photoURL
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun updateUser(id:String, firstName: String, lastName: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        //IP Change
        val url = "http://192.168.188.132/ANMP/HobbyApp/user_update.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                updateLD.value = true
                Toast.makeText(getApplication(), "Update Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${response}")
            }, {
                updateLD.value = false
//                Toast.makeText(getApplication(), "Update Failed", Toast.LENGTH_SHORT).show()
                Log.d("Update error", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = id
                params["first_name"] = firstName
                params["last_name"] = lastName
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
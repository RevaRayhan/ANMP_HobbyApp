package com.example.anmp_hobbyapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.anmp_hobbyapp.databinding.FragmentLoginBinding
import com.example.anmp_hobbyapp.model.User
import com.example.anmp_hobbyapp.viewmodel.UserViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    private var userLogin:User? = null

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            checkLogin(binding.txtUsername.editText?.text.toString(), binding.txtPassword.editText?.text.toString())
            val action = LoginFragmentDirections.actionhobbyListFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun checkLogin(username:String, password:String) {
        queue = Volley.newRequestQueue(activity)
        val url = "http://192.168.188.132/ANMP/HobbyApp/user_login.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, { response->
                userLogin = Gson().fromJson(response, User::class.java)

                Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${userLogin}")
            }, {
                Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show()
                Log.d("error", it.toString())
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
}
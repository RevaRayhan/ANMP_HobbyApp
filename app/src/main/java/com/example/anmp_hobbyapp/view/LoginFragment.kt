package com.example.anmp_hobbyapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
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
    private lateinit var viewModel: UserViewModel
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

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
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnSignIn.setOnClickListener {view->
            viewModel.checkLogin(binding.txtUsername.editText?.text.toString(), binding.txtPassword.editText?.text.toString())

            val loginaccount = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
            val loginValue = loginaccount.edit()

            viewModel.userLD.observe(viewLifecycleOwner, Observer {
                var userLogin = it

                if (userLogin != null) {
                    loginValue.putString("id", userLogin.id)
                    loginValue.putString("firstName", userLogin.first_name)
                    loginValue.putString("lastName", userLogin.last_name)
                    loginValue.putString("password", userLogin.password)
                    loginValue.putString("photo_url", userLogin.photo_url)
                    loginValue.apply()
                    viewModel.goNavigate.value = true
                }
                else {
                    Toast.makeText(activity, "No user found with that username or password", Toast.LENGTH_SHORT).show()
                    viewModel.goNavigate.value = false
                }
            })

            viewModel.goNavigate.observe(viewLifecycleOwner, Observer {nav->
                if (nav == true) {
                    mainActivity.showBottomNavDrawer()
                    val action = LoginFragmentDirections.actionhobbyListFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                else {
                    Toast.makeText(activity, "No user found with that username or password", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.btnCreateAcc.setOnClickListener {
            val action = LoginFragmentDirections.actionRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
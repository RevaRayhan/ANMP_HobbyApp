package com.example.anmp_hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.anmp_hobbyapp.R
import com.example.anmp_hobbyapp.databinding.FragmentLoginBinding
import com.example.anmp_hobbyapp.databinding.FragmentRegisterBinding
import com.example.anmp_hobbyapp.viewmodel.UserViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding:FragmentRegisterBinding
    private lateinit var viewModel:UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {view->
            var firstName = binding.txtFirstName.editText?.text.toString()
            var lastName = binding.txtLastName.editText?.text.toString()
            var email = binding.txtEmail.editText?.text.toString()
            var username = binding.txtUsername.editText?.text.toString()
            var password = binding.txtPassword.editText?.text.toString()
            var rePassword = binding.txtRePassword.editText?.text.toString()
            var photoURL = binding.txtPhotoURL.editText?.text.toString()

            if (password == rePassword) {
                viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                viewModel.registerUser(firstName, lastName, email, username, password, photoURL)

                viewModel.registerLD.observe(viewLifecycleOwner, Observer {
                    if (it == true) {
                        val action = RegisterFragmentDirections.actionLoginFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                    else {
                        Toast.makeText(activity, "Register Failed", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else {
                Toast.makeText(activity, "Password does not match Re-type Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
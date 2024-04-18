package com.example.anmp_hobbyapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.anmp_hobbyapp.R
import com.example.anmp_hobbyapp.databinding.FragmentProfileBinding
import com.example.anmp_hobbyapp.viewmodel.UserViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var viewModel:UserViewModel
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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userLogin = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        val id = userLogin.getString("id", null)
        val oldFirstName = userLogin.getString("firstName", null)
        val oldLastName = userLogin.getString("lastName", null)
        val oldPassword = userLogin.getString("password", null)
        val photo_url = userLogin.getString("photo_url", null)

        Picasso.get().load(photo_url).into(binding.imgProfile)
        binding.txtFirstName.editText?.setText(oldFirstName)
        binding.txtLastName.editText?.setText(oldLastName)

        binding.btnUpdate.setOnClickListener {
            if (oldPassword == binding.txtOldPassword.editText?.text.toString()) {
                viewModel.updateUser(id.toString(), binding.txtFirstName.editText?.text.toString(), binding.txtLastName.editText?.text.toString(), binding.txtNewPassword.editText?.text.toString())

                viewModel.updateLD.observe(viewLifecycleOwner, Observer {
                    if (it == true) {
                        binding.txtOldPassword.editText?.setText("")
                        binding.txtNewPassword.editText?.setText("")
                    }
                })
            }
        }

        binding.btnLogout.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
            val sharedPrefEdit = sharedPref.edit()
            sharedPrefEdit.remove("id")
            sharedPrefEdit.remove("firstName")
            sharedPrefEdit.remove("lastName")
            sharedPrefEdit.remove("password")
            sharedPrefEdit.remove("photo_url")

            mainActivity.hideBottomNavDrawer()
            val action = ProfileFragmentDirections.actionloginFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }
}
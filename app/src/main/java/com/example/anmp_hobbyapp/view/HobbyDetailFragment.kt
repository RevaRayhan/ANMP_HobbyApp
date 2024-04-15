package com.example.anmp_hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.anmp_hobbyapp.R
import com.example.anmp_hobbyapp.databinding.FragmentHobbyDetailBinding
import com.example.anmp_hobbyapp.viewmodel.HobbyViewModel
import com.squareup.picasso.Picasso

class HobbyDetailFragment : Fragment() {

    private lateinit var binding:FragmentHobbyDetailBinding
    private lateinit var viewModel:HobbyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHobbyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            viewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)
            viewModel.detail(HobbyDetailFragmentArgs.fromBundle(requireArguments()).id)

            observeViewModel()
        }
    }

    fun observeViewModel() {
        viewModel.hobbyDetailLD.observe(viewLifecycleOwner, Observer {
            if (it == null) {
            }
            else {
                binding.txtTitle.setText(it.title)
                binding.txtUsername.setText("@${it.username}")
                binding.txtContent.setText("") //figure how to do the multiple pages for the content
                val picasso = Picasso.Builder(binding.root.context)
                picasso.listener { picasso, uri, exception ->  exception.printStackTrace()}
                picasso.build().load(it.image_url).into(binding.imgNews)
            }
        })
    }
}
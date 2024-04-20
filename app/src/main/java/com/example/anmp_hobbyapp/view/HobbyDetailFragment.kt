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
                val picasso = Picasso.Builder(binding.root.context)
                picasso.listener { picasso, uri, exception ->  exception.printStackTrace()}
                picasso.build().load(it.image_url).into(binding.imgNews)

//                it.content?.getOrNull(1)?.let {
//                    binding.txtContent.setText(it)
//                }
                var contentSize = it.content?.size ?:0
                var index = 0
                if (contentSize > 0) {
                    binding.txtContent.setText(it.content?.get(index))
                    binding.btnPrev.isEnabled = false
                    if (contentSize == 1) {
                        binding.btnNext.isEnabled = true
                    }

                    binding.btnNext.setOnClickListener {view->
                        index += 1
                        binding.txtContent.setText(it.content?.get(index))
                        if ((index+1) == contentSize) {
                            binding.btnNext.isEnabled = false
                        }
                            binding.btnPrev.isEnabled = true
                    }
                    binding.btnPrev.setOnClickListener {view->
                        index -= 1
                        binding.txtContent.setText(it.content?.get(index))
                        if (index == 0) {
                            binding.btnPrev.isEnabled = false
                        }
                            binding.btnNext.isEnabled = true
                    }
                }
                else {
                    binding.txtContent.setText("There is no content")
                }
            }
        })
    }
}
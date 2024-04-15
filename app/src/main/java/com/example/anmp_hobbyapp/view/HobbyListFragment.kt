package com.example.anmp_hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.anmp_hobbyapp.R
import com.example.anmp_hobbyapp.databinding.FragmentHobbyListBinding
import com.example.anmp_hobbyapp.viewmodel.HobbyViewModel

class HobbyListFragment : Fragment() {

    private lateinit var binding:FragmentHobbyListBinding
    private lateinit var viewModel:HobbyViewModel
    private val hobbyListAdapter = HobbyListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHobbyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)
        viewModel.refresh()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = hobbyListAdapter

        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)

        swipe.setOnRefreshListener {
            viewModel.refresh()
            binding.recView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            swipe.isRefreshing = false
        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.hobbiesLD.observe(viewLifecycleOwner, Observer {
            hobbyListAdapter.updateHobbyList(it)
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recView.visibility = View.GONE
                binding.progressLoad.visibility = View.INVISIBLE
            }
            else {
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })

        viewModel.hobbyLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.txtError?.visibility = View.VISIBLE
            }
            else {
                binding.txtError?.visibility = View.GONE
            }
        })
    }
}
package com.example.anmp_hobbyapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.anmp_hobbyapp.databinding.HobbyListItemBinding
import com.example.anmp_hobbyapp.model.Hobby
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class HobbyListAdapter(val hobbyList:ArrayList<Hobby>):RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>() {
    class HobbyViewHolder(var binding:HobbyListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = HobbyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HobbyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        with(holder.binding) {
            val picasso = Picasso.Builder(holder.itemView.context)
            picasso.listener { picasso, uri, exception ->  exception.printStackTrace()}
            picasso.build().load(hobbyList[position].image_url).into(imgNews, object:Callback {
                override fun onSuccess() {
                    progressImage.visibility = View.INVISIBLE
                    imgNews.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }

            })

            txtTitle.text = hobbyList[position].title
            txtUsername.text = "@" + hobbyList[position].username
            txtDescription.text = hobbyList[position].description
            btnRead.setOnClickListener {
                //action to hobby detail
                val action = HobbyListFragmentDirections.actionhobbyDetailFragment(hobbyList[position].id.toString())
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    fun updateHobbyList(newHobbyList:ArrayList<Hobby>) {
        hobbyList.clear()
        hobbyList.addAll(newHobbyList)
        notifyDataSetChanged()
    }
}
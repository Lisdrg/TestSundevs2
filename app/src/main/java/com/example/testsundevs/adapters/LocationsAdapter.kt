package com.example.testsundevs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testsundevs.models.LocationCredits
import com.example.testsundevs.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_comic_detail_list.view.*

class LocationsAdapter(
    private val results: List<LocationCredits>?, private val listener:

    Listener
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(result: LocationCredits)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(result: LocationCredits, listener: Listener, position: Int) {

            itemView.setOnClickListener { listener.onItemClick(result) }
            itemView.tvName.text = result.name

            Picasso.get()
                .load(R.drawable.unnamed)
                .into(itemView.ivComicDetail)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comic_detail_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = results!!.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results!![position], listener, position)
    }


}



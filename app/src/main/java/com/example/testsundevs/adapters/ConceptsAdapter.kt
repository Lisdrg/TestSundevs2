package com.example.testsundevs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testsundevs.models.ConceptsCredits
import com.example.testsundevs.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_comic_detail_list.view.*

class ConceptsAdapter(
    private val results: List<ConceptsCredits>?, private val listener:

    Listener
) : RecyclerView.Adapter<ConceptsAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(result: ConceptsCredits)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(result: ConceptsCredits, listener: Listener, position: Int) {

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


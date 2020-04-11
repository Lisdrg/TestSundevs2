package com.example.testsundevs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testsundevs.R
import com.example.testsundevs.models.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_comic_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter(private val results: List<Result>?, private val listener :

Listener
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(result: Result)

    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(result: Result, listener: Listener, position: Int) {

            itemView.setOnClickListener{ listener.onItemClick(result) }
            itemView.tvName.text = result.volume.name

            print(result.date_added)
            var date = result.date_added
            var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val newDate: Date = spf.parse(date)
            spf = SimpleDateFormat("MMM d, yyyy")
            val newDateString = spf.format(newDate)
            itemView.tvDate.text = newDateString



            Picasso.get()
                .load(result.image.original_url)
                .into(itemView.ivComic)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comic_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = results!!.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results!![position], listener, position)
    }
}


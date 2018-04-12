package com.example.lucassong.clubsandwichnewsfeed

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.lucassong.clubsandwichnewsfeed.Post
import com.squareup.picasso.Picasso

/**
 * Created by Lucas Song on 16/2/2018.
 */

class PostsAdapter(val posts: ArrayList<Post>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_POST: Int = 0
        const val TYPE_AD: Int = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //TODO("sloppy implemtation, refactor if possible")
        if (viewType == TYPE_AD) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.ad_row, parent, false)
            return AdViewHolder(view)
        }
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if ((position % 5) == 0) {
            return TYPE_AD
        }
        else {
            return TYPE_POST
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_POST) {
            (holder as PostViewHolder)
            holder.username.text = posts[position].username
            holder.text.text = posts[position].text
            Picasso.with(context).load(posts[position].photo).into(holder.photo)
        } else if (getItemViewType(position) == TYPE_AD) {

        }
    }

    override fun getItemCount() = posts.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val text: TextView = itemView.findViewById(R.id.text)
        val photo: ImageView = itemView.findViewById(R.id.photo)
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //yeeee
    }

}
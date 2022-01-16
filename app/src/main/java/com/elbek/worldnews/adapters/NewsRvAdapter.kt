package com.elbek.worldnews.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.elbek.worldnews.R
import com.elbek.worldnews.ViewActivity
import com.elbek.worldnews.api.NewsApiJson
import com.squareup.picasso.Picasso

class NewsRvAdapter(
    private var titles: List<String>,
    private var author: List<String>,
    private var description: List<String>,
    private var imageToUrl: List<String>,
    private var links :List<String>
) :
    RecyclerView.Adapter<NewsRvAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        val newsImage = view.findViewById<ImageView>(R.id.newsImage)
        val newsAuthor = view.findViewById<TextView>(R.id.newsAuthor)
        val newsDescription = view.findViewById<TextView>(R.id.newsDescription)
        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent: Intent = Intent(itemView.context,ViewActivity::class.java)
                intent.data = Uri.parse(links[position])
                intent.putExtra("imageUrl",imageToUrl[position])
                intent.putExtra("title",titles[position])
                intent.putExtra("description",description[position])
                intent.putExtra("url",links[position])
                startActivity(itemView.context,intent,null)
//                val intent = Intent(activity, Show::class.java)
//                intent.putExtra("id", basicRvModel.id)
//                startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_item,parent,false))

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.newsAuthor.text = author[position]
        holder.newsDescription.text = titles[position]
        Picasso.get().load(imageToUrl[position]).into(holder.newsImage);

    }

    override fun getItemCount(): Int {
        return titles.size
    }

    interface OnItemClickListener {
        fun onItemClick(newsApiJson: NewsApiJson)
    }
}
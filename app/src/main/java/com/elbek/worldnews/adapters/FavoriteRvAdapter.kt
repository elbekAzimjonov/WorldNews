package com.elbek.worldnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.elbek.worldnews.R
import com.elbek.worldnews.databinding.FavoriteItemBinding
import com.elbek.worldnews.models.UserNews
import com.squareup.picasso.Picasso

class FavoriteRvAdapter(
    var list: ArrayList<UserNews>,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FavoriteRvAdapter.MyViewHolder>() {
    inner class MyViewHolder(var favoriteItemBinding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(favoriteItemBinding.root) {
        fun onBind(userNews: UserNews, position: Int) {
            favoriteItemBinding.favoriteTitle.text = userNews.title
            favoriteItemBinding.favoriteDescription.text = userNews.description
            Picasso.get().load(userNews.imageUrl).error(R.drawable.load)
                .into(favoriteItemBinding.favoriteImage);
            favoriteItemBinding.moreBtn.setOnClickListener {
                onItemClickListener.onItemMoreClick(userNews, position, favoriteItemBinding.moreBtn)
            }
            favoriteItemBinding.root.setOnClickListener {
                onItemClickListener.onItemClick(userNews)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(userNews: UserNews)
        fun onItemMoreClick(userNews: UserNews, position: Int, imageView: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            FavoriteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
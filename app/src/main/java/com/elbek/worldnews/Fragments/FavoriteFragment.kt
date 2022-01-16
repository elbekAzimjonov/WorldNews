package com.elbek.worldnews.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elbek.worldnews.R
import com.elbek.worldnews.ViewActivity
import com.elbek.worldnews.adapters.FavoriteRvAdapter
import com.elbek.worldnews.db.AppDatabase
import com.elbek.worldnews.models.UserNews

class FavoriteFragment : Fragment() {
    lateinit var databse: AppDatabase
    lateinit var favoriteRvAdapter: FavoriteRvAdapter
    lateinit var list: ArrayList<UserNews>
    var context = this
    var connectivity: ConnectivityManager? = null
    val info: NetworkInfo? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        list = ArrayList()
        databse = AppDatabase.getInstance(requireContext())!!
        list.addAll(databse.getDao().getAllNews())
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        // Inflate the layout for this fragment
        val recycler = view.findViewById<RecyclerView>(R.id.favoriteRecycler)
        favoriteRvAdapter = FavoriteRvAdapter(list, object : FavoriteRvAdapter.OnItemClickListener {
            override fun onItemClick(userNews: UserNews) {
                val intent = Intent(activity, ViewActivity::class.java)
                intent.putExtra("db", "db")
                intent.putExtra("id", userNews.id)
                intent.putExtra("url", userNews.url)
                intent.putExtra("imageUrl", userNews.imageUrl)
                intent.putExtra("title", userNews.title)
                intent.putExtra("description", userNews.description)
                startActivity(intent)
            }

            override fun onItemMoreClick(userNews: UserNews, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(activity, imageView)
                popupMenu.inflate(R.menu.menu)
                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        val itemId = item?.itemId
                        when (itemId) {
                            R.id.delete -> {
                                val dialog = AlertDialog.Builder(activity)
                                dialog.setMessage("Are you deleting news?")
                                dialog.setPositiveButton("Delete",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(p0: DialogInterface?, p1: Int) {
                                            databse.getDao().deleteNews(userNews)
                                            list.remove(userNews)
                                            favoriteRvAdapter.notifyItemRemoved(list.size)
                                            favoriteRvAdapter.notifyItemChanged(position, list.size)
                                        }
                                    })
                                dialog.setNegativeButton("No",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(p0: DialogInterface?, p1: Int) {
                                        }
                                    })
                                dialog.show()
                            }
                        }
                        return true
                    }
                })
                popupMenu.show()
            }
        })
        val itemDeclaration = DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        recycler.addItemDecoration(itemDeclaration)
        recycler.adapter = favoriteRvAdapter
        return view
    }
}
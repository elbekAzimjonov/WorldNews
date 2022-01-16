package com.elbek.worldnews

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.WorkSource
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.elbek.worldnews.db.AppDatabase
import com.elbek.worldnews.db.UserDao
import com.elbek.worldnews.models.UserNews
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class ViewActivity : AppCompatActivity() {
    lateinit var databse: AppDatabase
    lateinit var dao: UserDao
    var chekNumber: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        databse = AppDatabase.getInstance(applicationContext)!!
        val title = findViewById<TextView>(R.id.title)
        val image = findViewById<ImageView>(R.id.imageView)
        val description = findViewById<TextView>(R.id.description)
        val save = findViewById<ImageView>(R.id.save)
        val link = findViewById<ImageView>(R.id.link)
        val titleGet = intent.getStringExtra("title")
        val imageGet = intent.getStringExtra("imageUrl")
        val descriptionGet = intent.getStringExtra("description")
        val url = intent.getStringExtra("url")
        val db = intent.getStringExtra("db")
        if (db.equals("db")){
            save.visibility = View.INVISIBLE
        }
        Picasso.get().load(imageGet).into(image);
        description.text = descriptionGet
        title.text = titleGet
        link.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        //UserNews(titleGet, descriptionGet, imageGet, url)
        save.setOnClickListener {
            if (chekNumber == 0) {
                databse!!.getDao().insertNews(UserNews(titleGet, descriptionGet, imageGet, url))
                save.setImageResource(R.drawable.ic_baseline_bookmark_24)
                Snackbar.make(it, "Successfully added", Snackbar.LENGTH_SHORT).show()
                chekNumber++
            } else {
            }
        }
    }
}
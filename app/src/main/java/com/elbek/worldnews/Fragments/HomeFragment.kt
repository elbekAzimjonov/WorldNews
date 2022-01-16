package com.elbek.worldnews.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elbek.worldnews.Retrofit.ApiRequest
import com.elbek.worldnews.R
import com.elbek.worldnews.Retrofit.ApiClient
import com.elbek.worldnews.Retrofit.ApiService
import com.elbek.worldnews.adapters.NewsItemDeclaration
import com.elbek.worldnews.adapters.NewsRvAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BACE_URL = "https://newsapi.org"

class HomeFragment : Fragment() {
    lateinit var newsRvAdapter: NewsRvAdapter
    lateinit var recyclerView: RecyclerView

    //    lateinit var arrayList: ArrayList<NewsModel>
    private var titlesList = mutableListOf<String>()
    private var authorList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var urlImagesList = mutableListOf<String>()
    private var urlList = mutableListOf<String>()

    //    val fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater,null,false)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        val progressBar = view.findViewById<ProgressBar>(R.id.loadProgress)
        val apiClient = ApiClient()
        val retrofit = apiClient.getRetrofit()
        val apiService = retrofit.create(ApiService::class.java)
        recyclerView = view.findViewById(R.id.newsRecycler)


        if (checkForInternet(requireActivity())) {
            val loadProgressBar = view?.findViewById<ProgressBar>(R.id.loadProgress)
            loadProgressBar!!.visibility = View.VISIBLE
            titlesList.clear()
            try {
                makeApiRequest()
            } catch (t: IllegalThreadStateException) {

            } catch (e: InstantiationException) {

            } catch (d: RuntimeException) {

            }
        } else {
            val noInternet = view?.findViewById<TextView>(R.id.noInternet)
            noInternet!!.visibility = View.VISIBLE
        }
        return view
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun makeApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BACE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getNewsApi()
                for (article in response.articles) {
                    Log.v("NewRetrofit", "Result $article")
                    addToList(
                        article.title,
                        article.author,
                        article.description,
                        article.urlToImage,
                        article.url
                    )
                }
                withContext(Dispatchers.Main) {
                    setUpRecyclerView()

                }
            } catch (e: Exception) {

                try {
//                    noInternet!!.visibility = View.VISIBLE
                } catch (t: IllegalThreadStateException) {

                } catch (e: InstantiationException) {

                } catch (d: RuntimeException) {

                }
            }
        }
    }

    private fun setUpRecyclerView() {
        recyclerView.addItemDecoration(
            NewsItemDeclaration(
                resources.getDimension(R.dimen.dp8).toInt(),
                resources.getDimension(R.dimen.dp18).toInt()
            )
        )
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        val loadProgressBar = view?.findViewById<ProgressBar>(R.id.loadProgress)
        loadProgressBar!!.visibility = View.INVISIBLE
        recyclerView.adapter =
            NewsRvAdapter(titlesList, authorList, descList, urlImagesList, urlList)
    }

    private fun addToList(
        title: String,
        author: String,
        description: String,
        urlToImage: String,
        url: String
    ) {
        titlesList.add(title)
        authorList.add(author)
        descList.add(description)
        urlImagesList.add(urlToImage)
        urlList.add(url)
    }
}
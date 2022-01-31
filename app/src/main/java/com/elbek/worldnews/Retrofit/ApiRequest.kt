package com.elbek.worldnews.Retrofit

import com.elbek.worldnews.api.NewsApiJson
import retrofit2.http.GET

interface ApiRequest {
   @GET("/v2/everything?q=tesla&from=2021-12-15&sortBy=publishedAt&apiKey=YouApiKey")
   suspend fun getNewsApi():NewsApiJson

}

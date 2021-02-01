package com.example.androidmvi.api

import androidx.lifecycle.LiveData
import com.example.androidmvi.model.BlogPost
import com.example.androidmvi.model.User
import com.example.androidmvi.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser( @Path("userId") userId : String): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getUserBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

}
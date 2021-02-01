package com.example.androidmvi.ui.main.state

import com.example.androidmvi.model.BlogPost
import com.example.androidmvi.model.User

data class MainViewState(
    var blogPosts : List<BlogPost>? = null,
    var user: User? = null
)
package com.example.androidmvi.ui

import com.example.androidmvi.util.DataState

interface DataStateListener {

    fun onDataStateChange(datState: DataState<*>?)

}
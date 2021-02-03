package com.example.androidmvi.ui.main

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvi.R
import com.example.androidmvi.ui.DataStateListener
import com.example.androidmvi.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataStateListener {

    lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        showMainFragment()
    }

    fun showMainFragment(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), MainFragment::class.qualifiedName)
            .commit()
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let{dataState ->
            //Handle loading
            showProgressBar(dataState.loading)

            //Handle message
            dataState.message?.let {event->
                event.getContentIfNotHandled()?.let{message ->
                    showToast(message)
                }
            }

        }
    }

    fun showToast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showProgressBar(isVisible: Boolean){
        if(isVisible){
            progress_circular.visibility = View.VISIBLE
        }else
            progress_circular.visibility = View.GONE
    }
}
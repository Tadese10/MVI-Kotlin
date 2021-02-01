package com.example.androidmvi.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvi.R
import com.example.androidmvi.ui.main.state.MainStateEvent

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        subscribeObserver()
    }

    fun subscribeObserver(){
        viewModel.dataState.observe(viewLifecycleOwner,Observer{ dataState ->
            println("DEBUG: DataState: ${dataState}")

            //Handle data
            dataState.data?.let { mainViewState ->

                mainViewState.blogPosts?.let{
                    //set blog post data
                    viewModel.setBlogListData(it)
                }

                mainViewState.user?.let{
                    //set user data
                    viewModel.setUser(it)
                }

            }

            //Handle Error
            dataState.message?.let{

            }

            //Handle Loading
            dataState.loading?.let{

            }


        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.blogPosts?.let{
                //set blog post data
                println("DEBUG: Setting blog posts to RecyclerView: ${viewState}")
            }

            viewState.user?.let{
                //set user data
                println("DEBUG: Setting user data: ${viewState}")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_get_user -> triggerGetUserEvent()

            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostEvent())
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }
}
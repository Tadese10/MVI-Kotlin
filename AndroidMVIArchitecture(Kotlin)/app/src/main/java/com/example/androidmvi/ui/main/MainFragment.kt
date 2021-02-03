package com.example.androidmvi.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.androidmvi.R
import com.example.androidmvi.model.BlogPost
import com.example.androidmvi.model.User
import com.example.androidmvi.ui.DataStateListener
import com.example.androidmvi.ui.main.state.MainStateEvent
import com.example.androidmvi.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_blog_list_item.*

class MainFragment : Fragment(), BlogListAdapter.Interaction {

    lateinit var viewModel: MainViewModel

    lateinit var dataStateHandler : DataStateListener

    lateinit var blogListAdapter : BlogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacing = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacing)
            blogListAdapter = BlogListAdapter(this@MainFragment)
            adapter = blogListAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        subscribeObserver()
        initRecyclerView()
    }

    fun subscribeObserver(){
        viewModel.dataState.observe(viewLifecycleOwner,Observer{ dataState ->
            println("DEBUG: DataState: ${dataState}")

            //Handle loading and message
            dataStateHandler.onDataStateChange(dataState)

            //Handle data
            dataState.data?.let { mainViewState ->

                mainViewState.getContentIfNotHandled()?.let {
                    it.blogPosts?.let{
                        //set blog post data
                        viewModel.setBlogListData(it)
                    }

                    it.user?.let{
                        //set user data
                        viewModel.setUser(it)
                    }
                }


            }


            /*
             //Handle Error
            dataState.message?.let{

            }

            //Handle Loading
            dataState.loading?.let{

            }

             */


        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.blogPosts?.let{
                //set blog post data
                println("DEBUG: Setting blog posts to RecyclerView: ${viewState}")
                blogListAdapter.submitList(it)
            }

            viewState.user?.let{
                //set user data
                println("DEBUG: Setting user data: ${viewState}")
                setUserProperties(it)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    private fun setUserProperties(user: User){
        email.text = user.email
        username.text = user.username
        view?.let{
            Glide.with(it.context)
                .load(user.image)
                .into(image)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener")
        }
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

    override fun onItemSelected(position: Int, item: BlogPost) {

    }
}
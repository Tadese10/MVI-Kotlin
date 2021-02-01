package com.example.androidmvi.ui.main

import androidx.lifecycle.*
import com.example.androidmvi.model.BlogPost
import com.example.androidmvi.model.User
import com.example.androidmvi.repository.Repository
import com.example.androidmvi.ui.main.state.MainStateEvent
import com.example.androidmvi.ui.main.state.MainViewState
import com.example.androidmvi.util.AbsentLiveData
import com.example.androidmvi.util.DataState

class MainViewModel: ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState : MutableLiveData<MainViewState>  = MutableLiveData()//Or MediatorLiveData This is the private property

    val viewState : LiveData<MainViewState> // This property is observed
    get() = _viewState

    /*
    The above code sample is the same with the following code

     fun observeViewState():LiveData<MainViewState>{
        return _viewState
     }

     */

    //Transformation is a method that is analogous to {@link io.reactivex.Observable#map}
    val dataState : LiveData<DataState<MainViewState>> = Transformations.switchMap(_stateEvent){
        it?.let {
           handleStateEvent(it)
        }
    }

    private fun handleStateEvent(stateEvent : MainStateEvent):LiveData<DataState<MainViewState>>{
        return when(stateEvent){
            is MainStateEvent.GetBlogPostEvent -> {
                return Repository.getBlogPosts()
            }

            is MainStateEvent.GetUserEvent -> {
                return Repository.getUser(stateEvent.userId)
            }

            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPosts : List<BlogPost>){
        val update  = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setUser(user: User){
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew():MainViewState{
        return viewState.value?.let {
            it
        } ?: MainViewState()
    }

    fun setStateEvent(event : MainStateEvent){
        _stateEvent.value = event
    }

}
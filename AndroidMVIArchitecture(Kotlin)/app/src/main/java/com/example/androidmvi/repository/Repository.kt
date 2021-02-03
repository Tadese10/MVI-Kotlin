package com.example.androidmvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.androidmvi.api.MyRetrofitBuilder
import com.example.androidmvi.model.BlogPost
import com.example.androidmvi.model.User
import com.example.androidmvi.ui.main.state.MainViewState
import com.example.androidmvi.util.*

object Repository {

    /*
     fun getBlogPosts():LiveData<DataState<MainViewState>>{
        return Transformations.switchMap(MyRetrofitBuilder.apiService.getUserBlogPosts()){apiResponse->
            object : LiveData<DataState<MainViewState>>(){
                override fun onActive() {
                    super.onActive()
                    when(apiResponse){
                        is ApiSuccessResponse ->{
                            value = DataState.data(
                                message = null,
                                data = MainViewState(
                                    blogPosts = apiResponse.body
                                )
                            )
                        }

                        is ApiErrorResponse ->{
                            value = DataState.error(
                                message = apiResponse.errorMessage
                            ) //Handle error
                        }

                        is ApiEmptyResponse ->{
                            value = DataState.error(
                                message = "HTTP 204. Returned NOTHING!"
                            ) //Handle empty//error
                        }
                    }
                }
            }
        }
    }
     */

    fun getBlogPosts():LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                       data = MainViewState(
                               blogPosts = response.body
                       )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return MyRetrofitBuilder.apiService.getUserBlogPosts()
            }

        }.asLiveData()
    }

    /*
    fun getUser(userId: String):LiveData<DataState<MainViewState>>{
        return Transformations.switchMap(MyRetrofitBuilder.apiService.getUser(userId)){apiResponse->
            object : LiveData<DataState<MainViewState>>(){
                override fun onActive() {
                    super.onActive()
                    when(apiResponse){
                        is ApiSuccessResponse ->{
                            value = DataState.data(
                                message = null,
                                data = MainViewState(
                                    user = apiResponse.body
                                )
                            )
                        }

                        is ApiErrorResponse ->{
                            value = DataState.error(
                                message = apiResponse.errorMessage
                            ) //Handle error
                        }

                        is ApiEmptyResponse ->{
                            value = DataState.error(
                                message = "HTTP 204. Returned NOTHING!"
                            ) //Handle empty//error
                        }
                    }
                }
            }
        }
    }
     */

    fun getUser(userId: String):LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                        data = MainViewState(
                                user = response.body
                        )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()

    }
}
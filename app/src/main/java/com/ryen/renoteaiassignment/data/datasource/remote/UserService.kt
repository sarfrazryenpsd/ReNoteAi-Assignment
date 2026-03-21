package com.ryen.renoteaiassignment.data.datasource.remote

import com.ryen.renoteaiassignment.domain.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService{

    @GET("/users")
    suspend fun getUsers(): Response<List<User>>

}


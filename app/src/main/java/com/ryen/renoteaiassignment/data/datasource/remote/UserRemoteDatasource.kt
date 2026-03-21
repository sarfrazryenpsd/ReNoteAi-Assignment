package com.ryen.renoteaiassignment.data.datasource.remote

import com.ryen.renoteaiassignment.domain.model.User
import com.ryen.renoteaiassignment.utils.NetworkResult
import com.ryen.renoteaiassignment.utils.safeApiCall
import javax.inject.Inject

class UserRemoteDatasource @Inject constructor(
    private val api: UserService
) {
    suspend operator fun invoke(): NetworkResult<List<User>> =
        safeApiCall { api.getUsers() }
}


package com.ryen.renoteaiassignment.data.datasource.remote

import com.ryen.renoteaiassignment.data.utils.NetworkResult
import com.ryen.renoteaiassignment.data.utils.safeApiCall
import javax.inject.Inject

class UserRemoteDatasource @Inject constructor(
    private val api: UserService
) {
    suspend operator fun invoke(): NetworkResult<List<UserDto>> =
        safeApiCall { api.getUsers() }
}


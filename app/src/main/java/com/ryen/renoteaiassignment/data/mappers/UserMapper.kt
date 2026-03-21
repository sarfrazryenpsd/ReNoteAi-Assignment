package com.ryen.renoteaiassignment.data.mappers

import com.ryen.renoteaiassignment.data.datasource.local.Address
import com.ryen.renoteaiassignment.data.datasource.local.Company
import com.ryen.renoteaiassignment.data.datasource.local.UserEntity
import com.ryen.renoteaiassignment.data.datasource.remote.UserDto
import com.ryen.renoteaiassignment.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        phone = phone,
        username = username,
        isFavorite = isFavorite,
        city = address.city,
        companyName = company.name
    )
}

fun UserDto.toUserEntity(isFavorite: Boolean = false): UserEntity{
    return UserEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        username = username,
        address = Address(city = address.city),
        company = Company(name = company.name),
        isFavorite = isFavorite
    )

}
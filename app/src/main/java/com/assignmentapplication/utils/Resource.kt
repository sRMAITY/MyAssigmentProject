package com.assignmentapplication.utils


sealed class Resource<out T> {

    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object LogOutcase : Resource<Nothing>()
    data class UserList<out R>(val result: R) : Resource<R>()


}
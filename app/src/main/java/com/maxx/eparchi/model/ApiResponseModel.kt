package com.maxx.eparchi.model

import com.google.gson.annotations.SerializedName


data class LoginResponse(

    @field:SerializedName("users")
    val users: List<UsersItem?>? = null,

    @field:SerializedName("token")
    val token: String? = null
)

data class UsersItem(

    @field:SerializedName("model")
    val model: String? = null,

    @field:SerializedName("pk")
    val pk: Int? = null,

    @field:SerializedName("fields")
    val fields: Fields? = null
)

data class Fields(

    @field:SerializedName("uname")
    val uname: String? = null,

    @field:SerializedName("section")
    val section: String? = null,

    @field:SerializedName("user")
    val user: Int? = null
)
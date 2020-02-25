package com.maxx.eparchi.model

import com.google.gson.annotations.SerializedName

data class LoginPostData(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)

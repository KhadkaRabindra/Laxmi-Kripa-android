package com.maxx.eparchi.model.test

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("users")
	val users: List<UsersItem?>? = null,

	@field:SerializedName("token")
	val token: String? = null
)
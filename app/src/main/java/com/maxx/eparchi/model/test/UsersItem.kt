package com.maxx.eparchi.model.test

import com.google.gson.annotations.SerializedName

data class UsersItem(

	@field:SerializedName("model")
	val model: String? = null,

	@field:SerializedName("pk")
	val pk: Int? = null,

	@field:SerializedName("fields")
	val fields: Fields? = null
)
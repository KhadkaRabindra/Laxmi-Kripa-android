package com.maxx.eparchi.model.test

import com.google.gson.annotations.SerializedName

data class Fields(

	@field:SerializedName("uname")
	val uname: String? = null,

	@field:SerializedName("section")
	val section: String? = null,

	@field:SerializedName("user")
	val user: Int? = null
)
package com.example.bottommenudialog.login

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Password")
    val password:String
    )


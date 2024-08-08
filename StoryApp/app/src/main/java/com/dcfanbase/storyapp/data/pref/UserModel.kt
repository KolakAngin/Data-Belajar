package com.dcfanbase.storyapp.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserModel(
    val name : String,
    val email : String,
    val token : String,
    var isLogin: Boolean = false
) : Parcelable
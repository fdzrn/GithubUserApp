package com.codelab.githubuser.model

data class Users(
    var id: Int = 0,
    var photo: String? = null,
    var username: String? = null,
    var type: String? = null
)
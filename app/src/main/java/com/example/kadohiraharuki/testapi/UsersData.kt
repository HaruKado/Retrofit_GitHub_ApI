package com.example.kadohiraharuki.testapi




data class UsersData(
        val login: String,
        val avatar_url: String,
        val repos_url: String
)

data class Result (val items: ArrayList<UsersData>)

data class UsersDefaultData(
        val location: String,
        val repository: String,
        val follow: String,
        val follower: String
)


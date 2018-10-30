package com.example.kadohiraharuki.testapi



data class UsersData(val login: String, val avatar_url: String, val repos_url: String)

data class Result (val items: ArrayList<UsersData>)



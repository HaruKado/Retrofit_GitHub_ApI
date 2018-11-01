package com.example.kadohiraharuki.testapi.Data_File

data class User(val login: String, val avatar_url: String, val repos_url: String)

data class UserItem (val items: ArrayList<User>)
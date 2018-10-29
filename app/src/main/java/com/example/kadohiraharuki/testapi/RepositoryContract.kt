package com.example.kadohiraharuki.testapi

interface RepositoryContract {

    interface View {
        fun onReposAvailable(userInfo: Array<UsersData>)
    }
}
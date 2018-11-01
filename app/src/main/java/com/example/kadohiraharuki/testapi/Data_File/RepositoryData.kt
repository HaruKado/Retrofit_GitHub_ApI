package com.example.kadohiraharuki.testapi.Data_File

//TI ファイル名とクラス名を一致させる
//TI kotlin的には変数名が大文字の方がいいのか、あるいは_で分けた方がいいのか確認し統一する
//TI 変数名に複数形の意味を持たせるのはよくない（スター数の多いアプリを参考にして書き直す）
//UsersDataも同じように直す

data class ReposData(
        val repositoryName: String,
        val star:String,
        val fork: String,
        val lang: String,
        val svn_url:String
)




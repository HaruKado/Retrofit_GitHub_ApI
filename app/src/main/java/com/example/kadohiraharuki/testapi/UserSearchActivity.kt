package com.example.kadohiraharuki.testapi
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.kadohiraharuki.testapi.Adapter.UserAdapter
import com.example.kadohiraharuki.testapi.Data_File.UserItem
import com.example.kadohiraharuki.testapi.Retrofit_Difine.RetrofitSearch
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

//TI UserSearchActivity
class UserSearchActivity: AppCompatActivity(){

    val etUsername: EditText by lazy {
        findViewById<EditText>(R.id.search)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search.setOnClickListener {
            val SearchUsername = etUsername.text.toString().trim()

            RetrofitSearch.ApiClientManager.apiClient.listUser(SearchUsername).enqueue(object : Callback<UserItem> {
                override fun onResponse(call: Call<UserItem>, response: retrofit2.Response<UserItem>) {
                    if (response.isSuccessful) {
                        Log.d("check_response", "call response=${response.body()}")

                        val users = response.body()!!.items
                        listview.adapter = UserAdapter(this@UserSearchActivity, users)

                        listview.setOnItemClickListener { parent, view, position, id ->
                            //TI users_positionはUsersData型であるのに対しpositionと明記されていると誤解を招く
                            //TI val user:User = response.body()!!.items[position]
                            //TI 型に対してインスタンスは基本的に同じ名前
                            val user = response.body()!!.items[position]
                            val userName = user.login
                            Log.d("checkName", userName)

                            val intent = Intent(applicationContext, RepositoryActivity::class.java)
                            intent.putExtra("userName", userName)
                            startActivity(intent)

                        }

                    }
                }
                override fun onFailure(call: Call<UserItem>, t: Throwable) {

                }
            })
        }

    }
}


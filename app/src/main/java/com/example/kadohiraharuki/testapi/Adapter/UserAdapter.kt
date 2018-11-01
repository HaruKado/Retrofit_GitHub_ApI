package com.example.kadohiraharuki.testapi.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.kadohiraharuki.testapi.Data_File.User
import com.example.kadohiraharuki.testapi.R
import com.example.kadohiraharuki.testapi.ViewHolder.UserViewHolder
import com.squareup.picasso.Picasso

//TI スネークケースとキャメルケース両方を用いていて統一性がなく見辛い
//TI 言語によって変数名、クラス名などの付け方は変わってくるので、それに合わせた名前をつける
class UserAdapter(context: Context, users: ArrayList<User>?) : ArrayAdapter<User>(context, 0, users) {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder: UserViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, parent, false)
            holder = UserViewHolder(
                    view.findViewById(R.id.nameTextView),
                    view.findViewById(R.id.userImgView)
            )
            view.tag = holder
        } else {
            holder = view.tag as UserViewHolder
        }
        //TI positionでないため誤解を招く変数
        val user = getItem(position) as User
        Picasso.with(context).load(user.avatar_url).into(holder.userImgView)
        holder.nameTextView.text = user.login
        return view

    }
}











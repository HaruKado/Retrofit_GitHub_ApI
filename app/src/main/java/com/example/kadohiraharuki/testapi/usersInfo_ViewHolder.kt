package com.example.kadohiraharuki.testapi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class RepoAdapter(UserInfo: Array<UsersData>) : RecyclerView.Adapter<RepoAdapter.RepoVH>() {


    private val UserInfo: Array<UsersData> = UserInfo

    override fun onBindViewHolder(holder: RepoVH, position: Int) {
        holder.bind(UserInfo[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent,
                false)
        return RepoVH(viewType, itemView)

    }

    override fun getItemCount(): Int {
        return UserInfo.size
    }


    class RepoVH(repoType: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val repoType = repoType

        private val repoName by lazy {
            itemView.findViewById<TextView>(R.id.nameTextView)
        }
        private val repoDesc by lazy {
            itemView.findViewById<ImageView>(R.id.userImgView)
        }


        fun bind(UserInfo: UsersData) {
            repoName.text = UserInfo.login

        }
    }

}


/*class UsersInfo_Adapter(context: Context, users: Array<UsersData>?) : ArrayAdapter<UsersData>(context, 0, users) {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder: Users_ViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, parent, false)
            holder = Users_ViewHolder(
                    view.findViewById(R.id.nameTextView),
                    view.findViewById(R.id.userImgView),
                    view.findViewById(R.id.locTextView),
                    view.findViewById(R.id.repositoryTextView),
                    view.findViewById(R.id.followTextView),
                    view.findViewById(R.id.followerTextView),
                    view.findViewById(R.id.placepicView),
                    view.findViewById(R.id.repopicView),
                    view.findViewById(R.id.followpicView),
                    view.findViewById(R.id.followerpicView)
            )
            view.tag = holder
        } else {
            holder = view.tag as Users_ViewHolder
        }

        val usersp = getItem(position) as UsersData
        Picasso.with(context).load(usersp.avatar_url).into(holder.UserImgView)
        holder.nameTextView.text = usersp.login

*//*holder.locTextView.text = usersp.location
        holder.repositoryTextView.text = usersp.repository
        holder.followTextView.text = usersp.follow
        holder.follwerTextView.text = usersp.follower
        holder.place_picture_View.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.place))
        holder.repository_picture_View.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.repository))
        holder.follow_picture_View.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.follow))
        holder.follower_picture_View.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.follower))*//*


        return view

    }
}*/









package com.example.amit.applaunchdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amit.applaunchdemo.R
import com.example.amit.applaunchdemo.database.entities.UserModel
import com.example.amit.applaunchdemo.databinding.ItemUserListBinding
import com.example.amit.applaunchdemo.interfaces.UserItemClickListener

class UserListAdapter(
    private val context: Context,
    private var lstModel: MutableList<UserModel>,
    private var userItemClickListener: UserItemClickListener) :
    RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userModel = lstModel[position]

        val name = "${userModel.firstName} ${userModel.lastName}"

        holder.binding.tvName.text = name

        holder.binding.tvEmailId.text = userModel.emailAddress

        holder.itemView.setOnClickListener { userItemClickListener.userItemClick(position, userModel) }

    }

    override fun getItemCount(): Int {
        return lstModel.size
    }

    fun removeItem(position: Int) {
        lstModel.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: UserModel, position: Int) {
        lstModel.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }

    fun updateList(newList: MutableList<UserModel>) {
        this.lstModel = newList
        notifyDataSetChanged()
    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemUserListBinding.bind(itemView)
    }
}

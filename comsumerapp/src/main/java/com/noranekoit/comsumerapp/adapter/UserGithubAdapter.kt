package com.noranekoit.comsumerapp.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.noranekoit.comsumerapp.databinding.ItemUserBinding
import com.noranekoit.comsumerapp.model.UserGithub

class UserGithubAdapter: RecyclerView.Adapter<UserGithubAdapter.UserViewHolder>() {

    private val list =ArrayList<UserGithub>()


    fun setList(userGithubs: ArrayList<UserGithub>){
        list.clear()
        list.addAll(userGithubs)
        notifyDataSetChanged()
    }


    inner  class UserViewHolder(private val binding: ItemUserBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(userGithub : UserGithub){


            binding.apply {

                Glide.with(itemView)
                    .load(userGithub.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()

                    .into(ivUser)
                tvUsername.text = userGithub.login
                tvStatus.text =userGithub.type
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,
        false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


}
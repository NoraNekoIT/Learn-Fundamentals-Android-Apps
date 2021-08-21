package com.noranekoit.bfaausergithub3.ui.detail.repository


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.noranekoit.bfaausergithub3.data.model.RepositoryGithubResponse

import com.noranekoit.bfaausergithub3.databinding.ItemRepositoryBinding



class RepositoryAdapter :RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    private val list =ArrayList<RepositoryGithubResponse>()

    inner  class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :RecyclerView.ViewHolder(binding.root){
       fun bind(repositoryGithubResponse: RepositoryGithubResponse){
            binding.apply {
                tvNameRepository.text = repositoryGithubResponse.name
                tvDescRepository.text =repositoryGithubResponse.description
            }

        }
    }

    fun setList(repositoryGithubResponses: ArrayList<RepositoryGithubResponse>){
        list.clear()
        list.addAll(repositoryGithubResponses)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),parent,
            false)
        return RepositoryViewHolder((view))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}
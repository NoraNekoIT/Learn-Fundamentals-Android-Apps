package com.noranekoit.bfaausergithub3.ui.detail.following

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noranekoit.bfaausergithub3.R
import com.noranekoit.bfaausergithub3.databinding.FragmentFollowBinding
import com.noranekoit.bfaausergithub3.ui.detail.DetailUserGithubActivity
import com.noranekoit.bfaausergithub3.ui.main.UserGithubAdapter

class FollowingFragment: Fragment(R.layout.fragment_follow) {
    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingViewModel
    private lateinit var githubAdapter: UserGithubAdapter
    private lateinit var username : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserGithubActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowBinding.bind(view)



        githubAdapter = UserGithubAdapter()
        githubAdapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = githubAdapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner,{
            if (it!= null){
                if (it.size==0){
                    binding.tvFollowEmpty.visibility=View.VISIBLE
                }
                else{
                    binding.tvFollowEmpty.visibility=View.GONE
                    githubAdapter.setList(it)
                }
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pBar.visibility = View.VISIBLE
        } else {
            binding.pBar.visibility = View.GONE
        }
    }
}
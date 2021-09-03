package com.noranekoit.bfaausergithub3.ui.detail.repository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noranekoit.bfaausergithub3.R

import com.noranekoit.bfaausergithub3.databinding.FragmentRepositoryBinding
import com.noranekoit.bfaausergithub3.ui.detail.DetailUserGithubActivity

class RepositoryFragment : Fragment() {

    private var _binding : FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RepositoryViewModel
    private lateinit var githubAdapter: RepositoryAdapter
    private lateinit var username : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserGithubActivity.EXTRA_USERNAME).toString()

        Log.d("repository","check")
        _binding = FragmentRepositoryBinding.bind(view)

        githubAdapter = RepositoryAdapter()
        githubAdapter.notifyDataSetChanged()

        binding.apply {
            rvRepository.setHasFixedSize(true)
            rvRepository.layoutManager = LinearLayoutManager(activity)
            rvRepository.adapter = githubAdapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            RepositoryViewModel::class.java)
        viewModel.setListRepository(username)

        viewModel.getListRepository().observe(viewLifecycleOwner,{
            if (it!= null){
                if (it.size == 0){
                    binding.tvRepositoryEmpty.visibility =View.VISIBLE
                }
                else{
                    binding.tvRepositoryEmpty.visibility =View.GONE
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
package com.noranekoit.bfaausergithub3.ui.favorit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noranekoit.bfaausergithub3.R
import com.noranekoit.bfaausergithub3.data.lokal.FavoriteUser
import com.noranekoit.bfaausergithub3.data.model.UserGithub
import com.noranekoit.bfaausergithub3.databinding.ActivityFavoriteBinding
import com.noranekoit.bfaausergithub3.ui.detail.DetailUserGithubActivity
import com.noranekoit.bfaausergithub3.ui.main.UserGithubAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserGithubAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        adapter = UserGithubAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserGithub) {
                Intent(this@FavoriteActivity, DetailUserGithubActivity::class.java).also {
                    it.putExtra(DetailUserGithubActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserGithubActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserGithubActivity.EXTRA_URL, data.avatar_url)
                    it.putExtra(DetailUserGithubActivity.EXTRA_TYPE, data.type)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUserFavorite.setHasFixedSize(true)
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                if (it.isEmpty()) {
                    binding.tvFavoriteEmpty.visibility =View.VISIBLE
                } else {
                    binding.tvFavoriteEmpty.visibility =View.GONE
                    val list = mapList(it)
                    adapter.setList(list)
                }
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<UserGithub> {
        val listUser = ArrayList<UserGithub>()
        for (user in users) {
            val userMapped = UserGithub(
                user.login,
                user.id,
                user.avatar_url,
                user.type
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun setActionBar() {
        supportActionBar?.title = resources.getString(R.string.favorite)
    }
}
package com.noranekoit.bfaausergithub3.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noranekoit.bfaausergithub3.R
import com.noranekoit.bfaausergithub3.data.model.UserGithub
import com.noranekoit.bfaausergithub3.databinding.ActivityMainBinding
import com.noranekoit.bfaausergithub3.ui.detail.DetailUserGithubActivity
import com.noranekoit.bfaausergithub3.ui.favorit.FavoriteActivity
import com.noranekoit.bfaausergithub3.ui.setting.SettingActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var githubViewModel: UserGithubViewModel
    private lateinit var githubAdapter: UserGithubAdapter
    private var title: String = "UGit App 3 "
     
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar(title)

        githubAdapter = UserGithubAdapter()
        githubAdapter.notifyDataSetChanged()

        githubAdapter.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserGithub) {
                Intent(this@MainActivity,DetailUserGithubActivity::class.java).also {
                    it.putExtra(DetailUserGithubActivity.EXTRA_USERNAME,data.login)
                    it.putExtra(DetailUserGithubActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailUserGithubActivity.EXTRA_URL,data.avatar_url)
                    it.putExtra(DetailUserGithubActivity.EXTRA_TYPE,data.type)
                    startActivity(it)
                }
            }

        })
        githubViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserGithubViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = githubAdapter

        }

        githubViewModel.checkServer().observe(this,{
            if (it != true){
                binding.apply {
                    rvUser.visibility = View.GONE
                    tvFirst.visibility = View.GONE
                    ivEmpty.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    ivError.visibility = View.VISIBLE
                }


            }
        })
        githubViewModel.getSearchUsers().observe(this, {
            if (it != null) {
                if (it.size == 0){
                   binding.apply {
                       rvUser.visibility = View.GONE
                       tvError.visibility = View.GONE
                       ivError.visibility = View.GONE
                       ivEmpty.visibility = View.VISIBLE
                       tvFirst.visibility = View.VISIBLE
                   }


                }else {
                    binding.apply {

                        tvError.visibility = View.GONE
                        tvFirst.visibility = View.GONE
                        ivError.visibility = View.GONE
                        ivEmpty.visibility = View.GONE
                        rvUser.visibility = View.VISIBLE

                        githubAdapter.setList(it)
                    }
                }
                showLoading(false)
            }

        })
    }

    private fun setActionBar(title: String) {
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchUser(query)
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {
                searchUser(newText)
                return false
            }
        })
        return true


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val i = Intent(this, SettingActivity::class.java)
            startActivity(i)
        }
        if (item.itemId == R.id.favorite_menu) {
            val i = Intent(this, FavoriteActivity::class.java)
            startActivity(i)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchUser(query:String) {

        if (query.isEmpty()) return
        showLoading(true)
        githubViewModel.setSearchUsers(query)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pBar.visibility = View.VISIBLE
        } else {
            binding.pBar.visibility = View.GONE
        }
    }


}
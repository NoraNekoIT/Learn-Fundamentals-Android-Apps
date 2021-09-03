package com.noranekoit.bfaausergithub3.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.noranekoit.bfaausergithub3.R
import com.noranekoit.bfaausergithub3.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserGithubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var githubViewModel: DetailUserGithubViewModel
    private var title: String = ""
    private var _isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setActionBar(title)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val type = intent.getStringExtra(EXTRA_TYPE)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)


        githubViewModel = ViewModelProvider(this).get(DetailUserGithubViewModel::class.java)

        if (username != null) {
            githubViewModel.setUserDetail(username)
        }
        githubViewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text =resources.getString(R.string.followers,it.followers,"\n")
                    tvFollowing.text = resources.getString(R.string.following, it.following,"\n")
                    tvCompany.text =resources.getString(R.string.company, it.company)
                    tvLokasi.text = resources.getString(R.string.lokasi, it.location)
                    tvRepository.text = resources.getString(R.string.repository,it.public_repos,"\n" )
                    Glide.with(this@DetailUserGithubActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()

                        .into(ivProfile)
                }
            }
        })


        lifecycleScope.launch(Dispatchers.IO) {
            val count = githubViewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.toggleButtonFavorite.isChecked = true
                        _isChecked = true
                    }else {
                        binding.toggleButtonFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleButtonFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                    githubViewModel.addToFavorite(username!!,id,avatarUrl!!,type!! )

            }else{
                githubViewModel.removeFromFavorite(id)
            }
            binding.toggleButtonFavorite.isChecked = _isChecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this,bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setActionBar(title : String) {
            val userGithub = intent.getStringExtra(EXTRA_USERNAME)
            supportActionBar?.title = title + userGithub

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            val username = intent.getStringExtra(EXTRA_USERNAME)
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT,resources.getString(R.string.first_share,username))
            startActivity(Intent.createChooser(shareIntent,resources.getString(R.string.share)))
        }

        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TYPE ="extra_type"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_3,
            R.string.tab_2

        )
    }
}
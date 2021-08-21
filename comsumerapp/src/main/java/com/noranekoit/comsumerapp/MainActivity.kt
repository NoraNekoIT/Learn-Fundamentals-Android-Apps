package com.noranekoit.comsumerapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noranekoit.comsumerapp.adapter.UserGithubAdapter
import com.noranekoit.comsumerapp.databinding.ActivityMainBinding
import com.noranekoit.comsumerapp.favorit.FavoriteViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter : UserGithubAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        adapter = UserGithubAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)



        binding.apply {
            rvUserFavorite.setHasFixedSize(true)
            rvUserFavorite.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUserFavorite.adapter =adapter
        }
        viewModel.setFavoriteUser(this)

        viewModel.getFavoriteUser().observe(this,{
            if (it!= null){
                if (it.size==0){
                    binding.tvConsumerEmpty.visibility = View.VISIBLE
                }
                else{
                    binding.tvConsumerEmpty.visibility =View.GONE
                }
                adapter.setList(it)
            }
        })
    }

    private fun setActionBar(){
        supportActionBar?.title = resources.getString(R.string.app_name)
    }


}
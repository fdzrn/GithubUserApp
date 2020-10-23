package com.codelab.githubuser.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelab.githubuser.R
import com.codelab.githubuser.adapter.UserAdapter
import com.codelab.githubuser.model.DetailUser
import com.codelab.githubuser.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.progressBar
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FavoriteActivity : AppCompatActivity() {

    private var list = ArrayList<DetailUser>()
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: UserAdapter


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tool_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_activity -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            R.id.settings_activity -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        showRecyclerview(list)
        showLoading(true)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.readAllData.observe(this, Observer {
            favoriteAdapter.setData(it)
            showLoading(false)
        })
    }

    private fun showRecyclerview(list: ArrayList<DetailUser>) {
        favoriteAdapter = UserAdapter(list)
        favoriteAdapter.notifyDataSetChanged()
        recyclerview_favorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }

        favoriteAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailUser) {
                val usersDetailData = Intent(this@FavoriteActivity, DetailActivity::class.java)
                usersDetailData.putExtra(DetailActivity.DATA_USER, data)
                startActivity(usersDetailData)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.INVISIBLE
    }
}
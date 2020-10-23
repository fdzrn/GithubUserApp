package com.codelab.githubuser.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelab.githubuser.R
import com.codelab.githubuser.adapter.UserAdapter
import com.codelab.githubuser.model.DetailUser
import com.codelab.githubuser.viewmodel.MainVIewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainVIewModel
    private lateinit var userAdapter: UserAdapter
    private var list = ArrayList<DetailUser>()

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
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainVIewModel::class.java)

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                mainViewModel.setDataResultUser(this@MainActivity, query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter = UserAdapter(list)
                userAdapter.setData(list)
                return false
            }
        })

        observeMainViewModel()
    }



    private fun observeMainViewModel() {
        mainViewModel.getDataSearchUser().observe(this@MainActivity, Observer {
            if (it != null) {
                list = it
                showRecyclerview(list)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerview(list: ArrayList<DetailUser>) {
        userAdapter = UserAdapter(list)
        userAdapter.notifyDataSetChanged()
        recyclerview_user.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
            setHasFixedSize(true)
        }

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailUser) {
                val usersDetailData = Intent(this@MainActivity, DetailActivity::class.java)
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

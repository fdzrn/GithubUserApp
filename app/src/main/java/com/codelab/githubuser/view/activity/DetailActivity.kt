package com.codelab.githubuser.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.codelab.githubuser.R
import com.codelab.githubuser.adapter.SectionPageAdapter
import com.codelab.githubuser.database.FavoriteDatabase
import com.codelab.githubuser.model.DetailUser
import com.codelab.githubuser.viewmodel.DetailViewModel
import com.codelab.githubuser.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var isFavorite: Boolean = false

    companion object { const val DATA_USER = "data_user" }

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
        setContentView(R.layout.activity_detail_user)

        val person = intent.getParcelableExtra<DetailUser>(DATA_USER)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        if (person != null) { observeDatabase(person.id) }
        if (person != null) { observeDetailViewModel(person.username) }
        if (person != null) { pagerAdapter(person.username) }


    }


    private fun observeDetailViewModel(username: String?) {
        detailViewModel.setDataDetailUser(this, username)
        detailViewModel.getDataDetailUser().observe(this, Observer {
            setDataFavoriteUser(it)
            inputDataDetailUser(it)
        })
    }

    private fun inputDataDetailUser(user: DetailUser) {
        Glide.with(this).load(user.photoProfile).into(user_image)
        template_name.text = user.name
        template_location.text = user.location
        template_company.text = user.company
        template_ID.text = user.id.toString()
        template_followers.text = user.followers.toString()
        template_following.text = user.following.toString()
        template_url.text = user.homepage
    }

    private fun pagerAdapter(username: String?) {
        val sectionsPagerAdapter = SectionPageAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

    private fun observeDatabase(userId: Int) {
        val database = FavoriteDatabase.getDatabase(applicationContext)
        val favoriteDAO = database.favoriteDAO()
        favoriteDAO.getUserByID(userId).observe(this, Observer{ liveDataUser ->
            if (liveDataUser.isNotEmpty() && liveDataUser[0].id == userId) {
                isFavorite = true
                changeIcon(isFavorite)
            } else {
                isFavorite = false
                changeIcon(isFavorite)
            }
        })
    }

    private fun changeIcon(favorite: Boolean) {
        if (favorite) button_favorite_user.setImageResource(R.drawable.ic_favorite)
        else button_favorite_user.setImageResource(R.drawable.ic_favorite_border)
    }

    private fun setDataFavoriteUser(detail: DetailUser) {
        button_favorite_user.setOnClickListener {
            observeDatabase(detail.id)
            if (!isFavorite) {
                favoriteViewModel.addToFavorite(
                    id = detail.id,
                    photo = detail.photoProfile,
                    username = detail.username,
                    name = detail.name,
                    company = detail.company,
                    location = detail.location,
                    followers = detail.followers,
                    following = detail.following,
                    homepage = detail.homepage)
                Toast.makeText(this@DetailActivity, "Added to Favourite", Toast.LENGTH_SHORT).show()
            } else {
                favoriteViewModel.removeFromFavorite(
                    id = detail.id,
                    photo = detail.photoProfile,
                    username = detail.username,
                    name = detail.name,
                    company = detail.company,
                    location = detail.location,
                    followers = detail.followers,
                    following = detail.following,
                    homepage = detail.homepage)
                Toast.makeText(this@DetailActivity, "Removed from Favourite", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
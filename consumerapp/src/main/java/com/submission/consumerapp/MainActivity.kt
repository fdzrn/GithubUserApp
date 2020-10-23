package com.submission.consumerapp

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        getFavoriteUserData()
    }

    @SuppressLint("Recycle")
    private fun getFavoriteUserData() {
        val table = "user_favorite_table"
        val authority = "com.codelab.githubuser.provider"

        val uri: Uri = Uri.Builder()
            .scheme("content")
            .authority(authority)
            .appendPath(table)
            .build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(uri,null,null,null,null)

        if (cursor != null && cursor.count > 0) {
            showRecyclerview(cursor)
        }
    }

    private fun showRecyclerview(cursor: Cursor?) {
        recyclerview_favorite.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserAdapter(convertCursor(cursor))
            setHasFixedSize(true)
        }
    }

    private fun convertCursor(cursor: Cursor?): ArrayList<DetailUser> {
        val favoriteList = ArrayList<DetailUser>()

        cursor?.apply {
            while(moveToNext()) {
                val photo = getString(getColumnIndexOrThrow("photoProfile"))
                val username = getString(getColumnIndexOrThrow("username"))
                val location = getString(getColumnIndexOrThrow("location"))
                val id = getInt(getColumnIndexOrThrow("id"))
                favoriteList.add(DetailUser(id,photo,username,location))
            }
        }
        return favoriteList
    }


}
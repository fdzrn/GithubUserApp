package com.codelab.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.codelab.githubuser.database.FavoriteDatabase
import com.codelab.githubuser.database.FavoriteRepository
import com.codelab.githubuser.model.DetailUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch


@InternalCoroutinesApi
class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<DetailUser>>
    private val repository: FavoriteRepository

    init {
        val favoriteDAO = FavoriteDatabase.getDatabase(application).favoriteDAO()
        repository = FavoriteRepository(favoriteDAO)
        readAllData = repository.readAllData
    }

    fun addToFavorite(
        id: Int,
        photo: String,
        username: String,
        name: String,
        company: String,
        location: String,
        followers: Int,
        following: Int,
        homepage: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorite(
                DetailUser(
                    id,
                    photo,
                    username,
                    name,
                    company,
                    location,
                    followers,
                    following,
                    homepage
                )
            )
        }
    }

    fun removeFromFavorite(
        id: Int,
        photo: String,
        username: String,
        name: String,
        company: String,
        location: String,
        followers: Int,
        following: Int,
        homepage: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(
                DetailUser(
                    id,
                    photo,
                    username,
                    name,
                    company,
                    location,
                    followers,
                    following,
                    homepage
                )
            )
        }
    }
}
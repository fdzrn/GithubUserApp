package com.codelab.githubuser.database

import androidx.lifecycle.LiveData
import com.codelab.githubuser.model.DetailUser

class FavoriteRepository(private val favoriteDAO: FavoriteDAO) {
    val readAllData: LiveData<List<DetailUser>> = favoriteDAO.selectAllData()

    suspend fun addToFavorite(detailUser: DetailUser) {
        favoriteDAO.addFavorite(detailUser)
    }

    suspend fun deleteFavorite(detailUser: DetailUser) {
        favoriteDAO.removeFavorite(detailUser)
    }

}
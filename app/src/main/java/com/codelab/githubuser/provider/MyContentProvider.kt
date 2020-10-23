package com.codelab.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.codelab.githubuser.database.FavoriteDAO
import com.codelab.githubuser.database.FavoriteDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.UnsupportedOperationException

@InternalCoroutinesApi
class MyContentProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val AUTHORITY = "com.codelab.githubuser.provider"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "user_favorite_table", USER)
        }
    }
    @InternalCoroutinesApi
    private val favoriteDAO: FavoriteDAO by lazy {
        FavoriteDatabase.getDatabase(requireNotNull(context)).favoriteDAO()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(uriMatcher.match(uri)) {
            USER -> favoriteDAO.cursorGetAll()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }
}

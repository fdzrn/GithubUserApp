package com.codelab.githubuser.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codelab.githubuser.model.DetailUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    private val liveDataDetail = MutableLiveData<DetailUser>()

    fun getDataDetailUser(): MutableLiveData<DetailUser> = liveDataDetail

    fun setDataDetailUser(context: Context, username: String?) {
        val url = "https://api.github.com/users/$username"
        val client = AsyncHttpClient()
        //TODO = untuk dapat menjalankan aplikasi ini anda harus
        client.addHeader("Authorization", "ISI GITHUB TOKEN ANDA DISINI")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val response = JSONObject(result)
                    val detailUser = DetailUser(
                        id = response.getInt("id"),
                        photoProfile = response.getString("avatar_url"),
                        username = response.getString("login"),
                        name = response.getString("name"),
                        company = response.getString("company"),
                        location = response.getString("location"),
                        followers = response.getInt("followers"),
                        following = response.getInt("following"),
                        homepage = response.getString("html_url")
                    )
                    liveDataDetail.postValue(detailUser)

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
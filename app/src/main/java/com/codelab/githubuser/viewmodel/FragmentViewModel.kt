package com.codelab.githubuser.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codelab.githubuser.model.Users
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FragmentViewModel: ViewModel() {
    private val liveDataFollow = MutableLiveData<ArrayList<Users>>()

    fun getDataFollowUser(): LiveData<ArrayList<Users>> = liveDataFollow

    fun setDataListFollowers(context:Context,username: String?) {
        val listItems = ArrayList<Users>()
        val url = "https://api.github.com/users/$username/followers"
        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token ISI GITHUB TOKEN ANDA DISINI")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val users = Users()
                        users.username = jsonObject.getString("login")
                        users.photo = jsonObject.getString("avatar_url")
                        users.id = jsonObject.getInt("id")
                        users.type = jsonObject.getString("type")
                        listItems.add(users)
                    }
                    liveDataFollow.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
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

    fun setDataListFollowing(context: Context,username: String?) {
        val listItems = ArrayList<Users>()
        val url = "https://api.github.com/users/$username/following"
        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token be41a7f7575fef16d0ddbcd538362350ac0d6052")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val users = Users()
                        users.username = jsonObject.getString("login")
                        users.photo = jsonObject.getString("avatar_url")
                        users.id = jsonObject.getInt("id")
                        users.type = jsonObject.getString("type")
                        listItems.add(users)
                    }
                    liveDataFollow.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
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
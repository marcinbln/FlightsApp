package com.example.flightsapp.core.network

import android.util.Log
import com.example.flightsapp.core.data.source.network.token.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val modifiedRequest = chain.request()
            .newBuilder()
            .header("Authorization", "Bearer ${tokenManager.getToken()}")
            .build()

        try {
            return chain.proceed(modifiedRequest)
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "Socket Timeout Exception: ${e.message}")
            throw e
        } catch (e: IOException) {
            Log.e(TAG, "IO Exception: ${e.message}")
            throw e
        }
    }

    companion object {
        private const val TAG = "TokenInterceptor"
    }
}

package com.example.flightsapp.core.data.source.network.token

import com.example.flightsapp.core.data.di.AppDispatchers
import com.example.flightsapp.core.data.di.Dispatcher
import com.example.flightsapp.core.network.TokenService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManagerImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val tokenApi: TokenService
) : TokenManager {

    private var token: String? = null

    override fun getToken(): String? = token

    override suspend fun refreshToken() {
        val response = tokenApi.obtainToken(clientId = apiKey, clientSecret = apiSecret)
            .execute()

        token = with(response) {
            if (isSuccessful) {
                response.body()?.accessToken
            } else {
                throw IOException("Access token not found in the response")
            }
        }

        token = withContext(ioDispatcher) {
            with(response) {
                if (isSuccessful) {
                    response.body()?.accessToken
                } else {
                    throw IOException("Access token not found in the response")
                }
            }
        }
    }

    companion object {
        private const val apiKey = "4qNa5OaUvuxvtLOCx6H9N0xswOvDPfjb"
        private const val apiSecret = "C6RbWNW7NjvwAhks"
    }
}

package com.example.flightsapp.core.network

import com.example.flightsapp.core.data.source.network.token.TokenManager
import com.example.flightsapp.core.data.di.AppDispatchers
import com.example.flightsapp.core.data.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val tokenManager: TokenManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        runBlocking(ioDispatcher) {
            tokenManager.refreshToken()
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${tokenManager.getToken()}")
            .build()
    }
}

package com.coroutines.sample

import kotlinx.coroutines.withTimeout
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkResource {

    private val service: MainNetwork by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coinpaprika.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MainNetwork::class.java)
    }

    /** Main network interface which will fetch list of coins */
    interface MainNetwork {
        @GET("coins")
        suspend fun fetchCoins(): List<Coin>
    }

    suspend fun refreshListOfCoins(
        onSuccess:((List<Coin>) -> Unit),
        onError: ((Throwable) -> Unit)
    ) {
        try {
            val result = withTimeout(5_000) {
                service.fetchCoins()
            }
            onSuccess.invoke(result)
        } catch (error: Throwable) {
            onError.invoke(error)
        }
    }
}
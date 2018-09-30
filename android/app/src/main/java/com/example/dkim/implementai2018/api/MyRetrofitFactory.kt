package com.example.dkim.implementai2018.api

import com.example.dkim.implementai2018.api.plantDisease.ICustomVisionService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object MyRetrofitFactory : IMyRetrofitFactory {
    private val myMsGraphService by lazy {
        retrofit.create(ICustomVisionService::class.java)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(ICustomVisionService.PREDICTION_ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    private val interceptor : HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message -> Timber.tag("Okhttp").d(message) })
                .apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
    }

    private val okHttpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
    }

    override fun getCustomVisionService(): ICustomVisionService {
        return myMsGraphService
    }

}
package com.innov.wakasinglebase.signin.utils


import com.innov.wakasinglebase.data.repository.authentification.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response

 class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                TokenRepository.getToken()?.let { token ->

                           addHeader("Authorization", "Bearer $token")


                }
            }
            .build()
        return chain.proceed(request)
    }
}
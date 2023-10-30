package com.innov.wakasinglebase.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.data.repository.authentification.TokenRepository
import com.innov.wakasinglebase.data.repository.tickets.TicketRepository
import com.innov.wakasinglebase.data.source.TicketDataSource
import com.innov.wakasinglebase.data.source.UserDataSource
import com.innov.wakasinglebase.data.source.VideoDataSource
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import com.innov.wakasinglebase.domain.ticket.TicketRepositoryImpl
import com.innov.wakasinglebase.signin.utils.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {


    @Provides
    @Singleton
    fun provideTokenRepo(): TokenRepository {
        return TokenRepository
    }

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        // TokenRepository.init(context)
        val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
        return ApolloClient.Builder()
            .httpServerUrl("https://wakabase-w4ax2xiweq-ew.a.run.app/query")
            .addHttpHeader("Content-Type", "application/json").also {
//
                it.okHttpClient(
                    OkHttpClient.Builder()

                        .addInterceptor(AuthorizationInterceptor())

                        .build()
                )
//
            }.normalizedCache(cacheFactory)

            .build()
    }

    @Provides
    @Singleton
    fun provideUserDataSource(apolloClient: ApolloClient): UserDataSource {
        return UserDataSource(apolloClient)
    }

    @Provides
    @Singleton
    fun provideVideoDataSource(apolloClient: ApolloClient): VideoDataSource {
        return VideoDataSource(apolloClient)
    }

    @Provides
    @Singleton
    fun provideTicketDataSource(apolloClient: ApolloClient): TicketDataSource {
        return TicketDataSource(apolloClient)
    }



    @Singleton
    @Provides
    fun providesAuthRepository(dataSource: UserDataSource):AuthRepository{
        return AuthRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun providesTicketRepository(dataSource: TicketDataSource):TicketRepository{
        return TicketRepositoryImpl(dataSource)
    }













}
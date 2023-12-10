package com.innov.wakasinglebase.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import com.innov.wakasinglebase.data.repository.NotificationRepository
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.data.repository.authentification.TokenRepository
import com.innov.wakasinglebase.data.repository.competition.CompetitionRepository
import com.innov.wakasinglebase.data.source.CommentDataSource
import com.innov.wakasinglebase.data.source.CompetitionDataSource
import com.innov.wakasinglebase.data.source.NotificationDataSource
import com.innov.wakasinglebase.data.source.ThreadDataSource
import com.innov.wakasinglebase.data.source.UserDataSource
import com.innov.wakasinglebase.data.source.VideoDataSource
import com.innov.wakasinglebase.domain.CompetitionRepositoryImpl
import com.innov.wakasinglebase.domain.NotificationRepositoryImpl
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
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
    fun provideChatDataSource(apolloClient: ApolloClient): CompetitionDataSource {
        return CompetitionDataSource(apolloClient)
    }

    @Provides
    @Singleton
    fun provideCommentDataSource(apolloClient: ApolloClient): CommentDataSource {
        return CommentDataSource(apolloClient)
    }


    @Provides
    @Singleton
    fun provideThreadDataSource(apolloClient: ApolloClient): ThreadDataSource {
        return ThreadDataSource(apolloClient)
    }

    @Provides
    @Singleton
    fun provideVideoDataSource(apolloClient: ApolloClient): VideoDataSource {
        return VideoDataSource(apolloClient)
    }

    @Provides
    @Singleton
    fun provideNotificationDataSource(apolloClient: ApolloClient): NotificationDataSource {
        return NotificationDataSource(apolloClient)
    }



    @Provides
    @Singleton
    fun provideCompetitionRepository(dataSource: CompetitionDataSource): CompetitionRepository {
        return CompetitionRepositoryImpl(dataSource)
    }



    @Singleton
    @Provides
    fun providesAuthRepository(dataSource: UserDataSource):AuthRepository{
        return AuthRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun providesCommunityRepository(dataSource: NotificationDataSource):NotificationRepository{
        return NotificationRepositoryImpl(dataSource)
    }













}
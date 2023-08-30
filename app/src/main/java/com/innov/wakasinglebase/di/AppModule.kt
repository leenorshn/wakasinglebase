package com.innov.wakasinglebase.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.signin.utils.GoogleAuthUiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Singleton
    @Provides
    fun providesAuthRepository(@ApplicationContext context: Context,auth: FirebaseAuth):AuthRepository{
        return AuthRepository(auth)
    }

    @Singleton
    @Provides
    fun providesFirebaseAuth(@ApplicationContext context: Context):FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun providesFirebaseFirestore(@ApplicationContext context: Context):FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun providesSignedClient(@ApplicationContext context: Context): SignInClient{
        return Identity.getSignInClient(context)
    }

    @Provides
    fun providesGoogleAuthSignInClient(
        signInClient: SignInClient,
        @ApplicationContext context: Context
    ) : GoogleAuthUiHelper {
        return GoogleAuthUiHelper(context, signInClient)
    }

    @Provides
    @Singleton
    fun provideAppDB():FirebaseFirestore{
        return Firebase.firestore;
    }


}
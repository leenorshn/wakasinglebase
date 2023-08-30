package com.innov.wakasinglebase.domain.cameramedia

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.innov.wakasinglebase.data.model.TemplateModel
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.signin.AuthData
import com.innov.wakasinglebase.signin.Result
import com.innov.wakasinglebase.signin.utils.getCurrentUser
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAuthorUserCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() =flow {
        emit(Result.Loading)
        val user = authRepository.getSignedInUser()
        emit(Result.Success(user))
    }.catch { error->
        emit(Result.Error(error))
    }
}
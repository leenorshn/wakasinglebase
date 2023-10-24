package com.innov.wakasinglebase.domain.cameramedia



import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import com.innov.wakasinglebase.signin.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAuthorUserCase @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) {
    operator fun invoke() =flow {
        emit(Result.Loading)
        val user = authRepository.me()
        emit(Result.Success(user))
    }.catch { error->
        emit(Result.Error(error))
    }
}
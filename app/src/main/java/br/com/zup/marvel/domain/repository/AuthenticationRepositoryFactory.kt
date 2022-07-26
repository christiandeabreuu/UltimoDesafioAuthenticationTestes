package br.com.zup.marvel.domain.repository

object AuthenticationRepositoryFactory {

    fun create(): AuthenticationRepository{
        return AuthenticationRepository()
    }
}
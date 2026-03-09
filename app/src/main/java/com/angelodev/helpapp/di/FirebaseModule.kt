package com.angelodev.helpapp.di

import com.angelodev.helpapp.data.repository.AuthRepository
import com.angelodev.helpapp.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepository()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepository()
}

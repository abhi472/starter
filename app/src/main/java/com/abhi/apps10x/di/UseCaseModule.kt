package com.abhi.apps10x.di

import com.abhi.apps10x.repository.MainRepository
import com.abhi.apps10x.usecase.MainUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun provideMainUseCase(repository: MainRepository): MainUseCase {
        return MainUseCase(repository)
    }

}
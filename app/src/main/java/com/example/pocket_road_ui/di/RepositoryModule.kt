package com.example.pocket_road_ui.di

import com.example.pocket_road_ui.data.repository.AuthRepository
import com.example.pocket_road_ui.data.repository.IAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepo(impl: AuthRepository): IAuthRepository
}
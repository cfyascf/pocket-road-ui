package com.example.pocket_road_ui.di

import com.example.pocket_road_ui.data.interfaces.IAuthRepository
import com.example.pocket_road_ui.data.interfaces.ICardexRepository
import com.example.pocket_road_ui.data.repository.AuthRepository
import com.example.pocket_road_ui.data.repository.CardexRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepo(impl: AuthRepository): IAuthRepository

    @Binds
    abstract fun bindCardexRepo(impl: CardexRepository): ICardexRepository
}
package com.example.controledeestoque_v2.core.di

import com.example.controledeestoque_v2.data.datasource.UsuarioLocalDataSource
import com.example.controledeestoque_v2.data.local.dao.UsuarioDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

   @Provides
   fun provideLocalDataSource(dao: UsuarioDao): UsuarioLocalDataSource = UsuarioLocalDataSource(dao)

}
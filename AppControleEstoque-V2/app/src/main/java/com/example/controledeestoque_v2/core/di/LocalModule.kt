package com.example.controledeestoque_v2.core.di

import android.content.Context
import androidx.room.Room
import com.example.controledeestoque_v2.data.local.dao.ProdutoDao
import com.example.controledeestoque_v2.data.local.database.AppDatabase
import com.example.controledeestoque_v2.data.repository.ProdutoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(db : AppDatabase): ProdutoDao = db.produtoDao()

    @Provides
    @Singleton
    fun provideProdutoRepository(dao: ProdutoDao): ProdutoRepository = ProdutoRepository(dao)


}
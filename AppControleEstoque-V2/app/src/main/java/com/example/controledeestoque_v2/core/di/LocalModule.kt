package com.example.controledeestoque_v2.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.controledeestoque_v2.data.datasource.UsuarioLocalDataSource
import com.example.controledeestoque_v2.data.local.dao.ProdutoDao
import com.example.controledeestoque_v2.data.local.dao.UsuarioDao
import com.example.controledeestoque_v2.data.local.database.AppDatabase
import com.example.controledeestoque_v2.data.repository.ProdutoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_token")

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    fun provideLocalDataSource(dao: UsuarioDao): UsuarioLocalDataSource = UsuarioLocalDataSource(dao)


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

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {

        return context.dataStore
    }


}

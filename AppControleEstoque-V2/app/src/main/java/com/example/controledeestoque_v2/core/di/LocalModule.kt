package com.example.controledeestoque_v2.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.controledeestoque_v2.core.datastore.GerenciadorDeToken
import com.example.controledeestoque_v2.data.datasource.UsuarioLocalDataSource
import com.example.controledeestoque_v2.data.datasource.UsuarioRemotoDataSouce
import com.example.controledeestoque_v2.data.local.dao.ClienteDao
import com.example.controledeestoque_v2.data.local.dao.ProdutoDao
import com.example.controledeestoque_v2.data.local.dao.UsuarioDao
import com.example.controledeestoque_v2.data.local.database.AppDatabase
import com.example.controledeestoque_v2.data.repository.ClienteRepository
import com.example.controledeestoque_v2.data.repository.ProdutoRepository
import com.example.controledeestoque_v2.data.repository.UsuarioRepository
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
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideProdutoDao(db : AppDatabase): ProdutoDao = db.produtoDao()

    @Provides
    @Singleton
    fun provideUsuarioDao(db : AppDatabase): UsuarioDao = db.usuarioDao()

    @Provides
    @Singleton
    fun provideClienteDao(db: AppDatabase): ClienteDao = db.clienteDao()

    @Provides
    @Singleton
    fun provideProdutoRepository(dao: ProdutoDao): ProdutoRepository = ProdutoRepository(dao)

    @Provides
    @Singleton
    fun provideClienteRepository(dao: ClienteDao): ClienteRepository = ClienteRepository(dao)

    @Provides
    @Singleton
    fun provideUsuarioRepository(
        usuarioRemotoDataSource: UsuarioRemotoDataSouce,
        usuarioLocalDataSource: UsuarioLocalDataSource,
        gerenciadorDeToken: GerenciadorDeToken
    ): UsuarioRepository = UsuarioRepository(usuarioRemotoDataSource, usuarioLocalDataSource, gerenciadorDeToken)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

}

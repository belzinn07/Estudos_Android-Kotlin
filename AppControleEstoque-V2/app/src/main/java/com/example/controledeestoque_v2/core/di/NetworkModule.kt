package com.example.controledeestoque_v2.core.di

import com.example.controledeestoque_v2.core.datastore.GerenciadorDeToken
import com.example.controledeestoque_v2.data.datasource.UsuarioLocalDataSource
import com.example.controledeestoque_v2.data.local.dao.UsuarioDao
import com.example.controledeestoque_v2.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

   private  val BASE_URL = "http://192.168.0.109:8080"

   // Interceptor para logar requisições
   @Provides
   @Singleton
   fun provideLoggingInterceptor(): HttpLoggingInterceptor {
      return HttpLoggingInterceptor().apply {
         level = HttpLoggingInterceptor.Level.BODY
      }
   }

   // Interceptor para adicionar o token JWT automaticamente
   @Provides
   @Singleton
   fun provideAuthInterceptor(gerenciarToken: GerenciadorDeToken): Interceptor {
      return Interceptor { chain ->
         val requestBuilder = chain.request().newBuilder()

         val token = gerenciarToken
         if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
         }

         chain.proceed(requestBuilder.build())
      }
   }

   // Cliente HTTP configurado
   @Provides
   @Singleton
   fun provideOkHttpClient(
      logging: HttpLoggingInterceptor,
      authInterceptor: Interceptor
   ): OkHttpClient {
      return OkHttpClient.Builder()
         .addInterceptor(logging)
         .addInterceptor(authInterceptor)
         .build()
   }

   // Retrofit configurado
   @Provides
   @Singleton
   fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
      return Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .client(okHttpClient)
         .build()
   }

   // ApiService pronto para injeção
   @Provides
   @Singleton
   fun provideApiService(retrofit: Retrofit): ApiService {
      return retrofit.create(ApiService::class.java)
   }

}
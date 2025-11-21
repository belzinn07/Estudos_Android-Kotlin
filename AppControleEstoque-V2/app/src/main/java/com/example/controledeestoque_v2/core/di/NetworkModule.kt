package com.example.controledeestoque_v2.core.di

import com.example.controledeestoque_v2.core.datastore.GerenciadorDeToken
import com.example.controledeestoque_v2.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

   private  val BASE_URL = "http://10.0.2.2:8080"


   @Provides
   @Singleton
   fun provideLoggingInterceptor(): HttpLoggingInterceptor {
      return HttpLoggingInterceptor().apply {
         level = HttpLoggingInterceptor.Level.BODY
      }
   }


   @Provides
   @Singleton
   fun provideAuthInterceptor(gerenciarToken: GerenciadorDeToken): Interceptor {
      return Interceptor { chain ->
         val requestBuilder = chain.request().newBuilder()

         val token = runBlocking { gerenciarToken.token.firstOrNull() }
         if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
         }

         chain.proceed(requestBuilder.build())
      }
   }


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


   @Provides
   @Singleton
   fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
      return Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .client(okHttpClient)
         .build()
   }


   @Provides
   @Singleton
   fun provideApiService(retrofit: Retrofit): ApiService {
      return retrofit.create(ApiService::class.java)
   }

}
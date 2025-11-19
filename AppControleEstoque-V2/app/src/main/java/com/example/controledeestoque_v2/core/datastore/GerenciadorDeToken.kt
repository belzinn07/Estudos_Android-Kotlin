package com.example.controledeestoque_v2.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class GerenciadorDeToken @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    companion object{
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
    }


    val token: Flow<String?> = dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    suspend fun salvarToken(value: String) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = value
        }
    }

    suspend fun limparToken() {
        dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

}

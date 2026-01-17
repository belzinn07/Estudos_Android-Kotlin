package com.example.controledeestoque_v2.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.controledeestoque_v2.data.local.dao.ClienteDao
import com.example.controledeestoque_v2.data.local.dao.ProdutoDao
import com.example.controledeestoque_v2.data.local.dao.UsuarioDao
import com.example.controledeestoque_v2.data.local.entities.Cliente
import com.example.controledeestoque_v2.data.local.entities.Converters
import com.example.controledeestoque_v2.data.local.entities.Produto
import com.example.controledeestoque_v2.data.local.entities.Usuario


@Database(entities = [Produto::class, Usuario::class, Cliente::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
    abstract fun clienteDao(): ClienteDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val  instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "token_usuario")
    val token: String? = null,

    @ColumnInfo(name = "nome_usuario")
    val nome: String,

    @ColumnInfo(name = "email_usuario")
    val email: String,

    @ColumnInfo(name = "senha_usuario")
    val senha: String
)

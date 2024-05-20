package lydia.yuan.chatbotdemo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var message: String,
    var sender: String,
    var date: Date
)
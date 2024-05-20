package lydia.yuan.chatbotdemo

import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class ChatbotRepository @Inject constructor(
    private val messageDao: MessageDao
) {

    fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages()
    }

    fun insertMessage(sender: String, message: String) {
        messageDao.insert(Message(message = message, sender = sender, date = Date()))
    }
}
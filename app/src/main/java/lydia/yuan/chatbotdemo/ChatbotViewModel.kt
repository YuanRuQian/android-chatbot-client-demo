package lydia.yuan.chatbotdemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ChatbotViewModel @Inject constructor(
    private val repository: ChatbotRepository
) : ViewModel() {

    val messages: Flow<List<Message>> = repository.getAllMessages()

    fun sendMessage(msg: String, onStart: () -> Unit, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                onStart()
                // first add the user message to the list
                repository.insertMessage("User", msg)

                // then send the message to the API
                delay(2000)
                repository.insertMessage("Chatbot", "This is a response from Chatbot")

                onComplete()
            } catch (e: HttpException) {
                Log.d("ChatbotViewModel", "HttpException: ${e.message}")
            } catch (e: IOException) {
                Log.d("ChatbotViewModel", "IOException: ${e.message}")
            }
        }
    }
}

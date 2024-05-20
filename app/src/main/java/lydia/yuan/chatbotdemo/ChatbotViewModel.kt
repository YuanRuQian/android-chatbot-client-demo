package lydia.yuan.chatbotdemo

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class ChatbotViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    // Use MutableSharedFlow to hold and emit messages
    private val _messages = MutableSharedFlow<List<MessageModal>>(replay = 1)
    val messages = _messages.asSharedFlow()

    // A list to keep track of all messages
    private val messageList = mutableListOf<MessageModal>()

    // Function to send a message and receive a response
    fun sendMessage(msg: String, onStart: () -> Unit, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                onStart()
                // first add the user message to the list
                messageList.add(MessageModal(msg, "User", false))
                _messages.emit(messageList.toList())

                // then send the message to the API
                val response = MessageModal("Hello", "Chatbot", true)
                messageList.add(response)
                _messages.emit(messageList.toList())
                // val response = apiService.getMessage(BuildConfig.BID, BuildConfig.KEY, BuildConfig.UID, msg)
                delay(2000)

                val prevIndex = messageList.indexOf(response)
                messageList[prevIndex] = response.copy(isLoading = false, message = "This is a response from Chatbot")
                _messages.emit(messageList.toList())

                onComplete()
//                if (response.success) {
//                    response.message.let {
//                        messageList.add(MessageModal(it, "Chatbot"))
//                        _messages.emit(messageList.toList())
//                    }
//                } else {
//                    Log.d("ChatbotViewModel", "Response not successful")
//                }
            } catch (e: HttpException) {
                Log.d("ChatbotViewModel", "HttpException: ${e.message}")
            } catch (e: IOException) {
                Log.d("ChatbotViewModel", "IOException: ${e.message}")
            }
        }
    }
}

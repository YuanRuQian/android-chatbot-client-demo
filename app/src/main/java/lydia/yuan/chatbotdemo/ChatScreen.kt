package lydia.yuan.chatbotdemo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(viewModel: ChatbotViewModel, paddingValues: PaddingValues) {

    val messages by viewModel.messages.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            stickyHeader {
                Text(
                    text = "My Chat",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp)
                )
            }
            items(messages.size) { index ->
                MessageItem(message = messages[index])
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Center
        ) {
            ChatRow(onSend = viewModel::sendMessage)
        }
    }
}

@Composable
fun ChatRow(onSend: (String, () -> Unit, () -> Unit) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val messageText = remember { mutableStateOf("") }
        val buttonEnabled = remember {
            mutableStateOf(true)
        }
        val textFieldEnabled = remember { mutableStateOf(true) }

        val onStartSend = {
            buttonEnabled.value = false
            textFieldEnabled.value = false
        }
        val onCompleteSend = {
            buttonEnabled.value = true
            textFieldEnabled.value = true
            messageText.value = ""
        }

        TextField(
            enabled = textFieldEnabled.value,
            modifier = Modifier.fillMaxWidth(0.85f).padding(horizontal = 8.dp),
            value = messageText.value,
            onValueChange = { messageText.value = it },
            placeholder = { Text("Enter your message") },
            singleLine = true
        )

        IconButton(
            enabled = buttonEnabled.value,
            onClick = { onSend(messageText.value, onStartSend, onCompleteSend) },
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = Color.White
            )
        }
    }
}



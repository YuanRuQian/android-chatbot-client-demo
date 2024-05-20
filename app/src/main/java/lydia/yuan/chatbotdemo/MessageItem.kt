package lydia.yuan.chatbotdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MessageItem(message: MessageModal) {
    if (message.sender == "User") {
        UserMessageItem(message)
    } else {
        ChatbotMessageItem(message)
    }
}

@Composable
fun UserMessageItem(message: MessageModal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Left side - User profile
        Image(
            painter = painterResource(id = R.drawable.current_user_profile),
            contentDescription = "User Profile",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        // Right side - Message text
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            Text(
                text = message.sender,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (message.isLoading) {
                Text(
                    text = "Sending...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            } else
                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
        }
    }
}

@Composable
fun ChatbotMessageItem(message: MessageModal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End // Ensure text starts from the left
    ) {
        // Left side: message text
        Column(
            modifier = Modifier.weight(1f) // Use weight to fill available space
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = message.sender,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (message.isLoading) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Waiting for response from Chatbot...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.End
                )
            } else
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = message.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Right side: chatbot profile
        Image(
            painter = painterResource(id = R.drawable.chatbot_profile),
            contentDescription = "Chatbot Profile",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}


@Preview
@Composable
fun UserMessageItemPreview() {
    UserMessageItem(
        message = MessageModal("Hello", "User", false)
    )
}

@Preview
@Composable
fun ChatbotMessageItemPreview() {
    ChatbotMessageItem(
        message = MessageModal("Hi there!", "Chatbot", false)
    )
}
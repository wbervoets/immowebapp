package be.wimbervoets.immowebapp.ui.composables.error

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.wimbervoets.immowebservice.data.datasource.FailureReason

@Composable
fun Failure(failureReason: FailureReason?, modifier: Modifier = Modifier) {
    if (failureReason != null) {
        Column(modifier = modifier) {
            Text(text = failureReason.title)
            Text(text = failureReason.description)
        }
    }
}
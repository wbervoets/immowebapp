package be.wimbervoets.immowebapp.ui.composables.snackbar


import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import be.wimbervoets.immowebapp.R


@Composable
fun FailureSnackbar(text: String, onClick: () -> Unit) {
    Snackbar(
        action = {
            TextButton(onClick = { onClick }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    ) {
        Text(text = text)
    }
}


@Composable
fun SnackbarHostState() {
    // LENGTH_INDEFINITE
    // show / hide

}
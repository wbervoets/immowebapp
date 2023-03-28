package be.wimbervoets.immowebapp.ui.composables.button

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.wimbervoets.immowebapp.R

@Composable
fun RefreshButton(visible: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    if (visible) {
        FloatingActionButton(onClick = onClick, modifier = modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(id = R.string.refresh)
            )
        }
    }
}
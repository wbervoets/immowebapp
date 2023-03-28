package be.wimbervoets.immowebapp.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = blue,
    onPrimary = white
)

// Darktheme could be added instead by using useDarkTheme boolean

@Composable
fun AppTheme(
  content: @Composable() () -> Unit
) {
  MaterialTheme(
        colorScheme = LightColors,
        content = content
  )
}
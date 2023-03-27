package be.wimbervoets.immowebapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import be.wimbervoets.immowebapp.ui.composables.ListingsScreen
import be.wimbervoets.immowebapp.ui.viewmodel.ListingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImmowebActivity : ComponentActivity() {

    private val viewModel: ListingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ListingsScreen(viewModel = viewModel) { viewModel.fetchCurrentListings() }
            }
        }

        viewModel.failureReason.observe(this) {
            if (it == null) {
                // hide snackbar
//                failureSnackbar.dismiss()
            } else {
                // set text in snackbar and show
//                failureSnackbar.setText(it.description)
//                failureSnackbar.show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchCurrentListings()
    }

//    private val failureSnackbar: Snackbar by lazy {
////        return@lazy Snackbar.make(
////            viewBinding.root,
////            "",
////            Snackbar.LENGTH_INDEFINITE
////        ).setAction(R.string.retry) {
////            viewModel.fetchCurrentListings()
////        }
//    }
}
package be.wimbervoets.immowebapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import be.wimbervoets.immowebapp.ui.composables.App
import be.wimbervoets.immowebapp.ui.viewmodel.ListingDetailViewModel
import be.wimbervoets.immowebapp.ui.viewmodel.ListingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImmowebActivity : ComponentActivity() {

    private val listingsViewModel: ListingsViewModel by viewModels()
    private val listingDetailViewModel: ListingDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App(listingsViewModel = listingsViewModel, listingDetailViewModel = listingDetailViewModel)
            }
        }
    }
}
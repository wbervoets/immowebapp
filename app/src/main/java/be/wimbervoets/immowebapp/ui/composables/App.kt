package be.wimbervoets.immowebapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.wimbervoets.immowebapp.ui.composables.listing.ListingDetailScreen
import be.wimbervoets.immowebapp.ui.composables.listing.ListingsScreen
import be.wimbervoets.immowebapp.ui.viewmodel.ListingDetailViewModel
import be.wimbervoets.immowebapp.ui.viewmodel.ListingsViewModel


@Composable
fun App(listingsViewModel: ListingsViewModel, listingDetailViewModel: ListingDetailViewModel) {
    val navController = rememberNavController() // stateful

    // Each NavController must be associated with a single NavHost composable.
    // The NavHost links the NavController with a navigation graph
    // that specifies the composable destinations that you should be able to navigate between.
    // As you navigate between composables, the content of the NavHost is automatically recomposed.
    // Each composable destination in your navigation graph is associated with a route.

    // All navigation (calls to navcontroller.navigate) is centralized here

    NavHost(navController = navController, startDestination = "listings") {
        composable("listings") {
            ListingsScreen(
                viewModel = listingsViewModel,
                onNavigateToDetail = {
                    id -> navController.navigate("listingdetail/$id")
                }
            )
        }
        composable(
            route ="listingDetail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType }   // id needs to be passed to List detail screen
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getLong("id")?.let {
                ListingDetailScreen(
                    viewModel = listingDetailViewModel,
                    id = it
                )
            }
        }
    }
}

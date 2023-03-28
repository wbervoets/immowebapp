package be.wimbervoets.immowebapp.ui.composables.listing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.constraintlayout.compose.ConstraintLayout
import be.wimbervoets.immowebapp.R
import be.wimbervoets.immowebapp.ui.composables.button.RefreshButton
import be.wimbervoets.immowebapp.ui.composables.error.Failure
import be.wimbervoets.immowebapp.ui.viewmodel.ListingsViewModel

@Composable
fun ListingsScreen(
    viewModel: ListingsViewModel,
    onNavigateToDetail: (Long) -> Unit) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.blue))
    ) {

        val (progressIndicator,
            listingsList,
            error,
            refreshButton) = createRefs()


        val visible = viewModel.loading.observeAsState().value ?: false
        if (visible) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.constrainAs(progressIndicator) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        Listings(
            list = viewModel.listings.observeAsState(initial = emptyList()),
            onNavigateToDetail = onNavigateToDetail,
            modifier = Modifier
                .constrainAs(listingsList) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Failure(
            failureReason = viewModel.failureReason.observeAsState().value, modifier = Modifier.constrainAs(error) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        RefreshButton(
            onClick = { viewModel.fetchCurrentListings() },
            visible = viewModel.loading.observeAsState().value?.not() ?: false,
            modifier = Modifier.constrainAs(refreshButton) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}


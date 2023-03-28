package be.wimbervoets.immowebapp.ui.composables.listing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.wimbervoets.immowebapp.R
import be.wimbervoets.immowebapp.data.model.Listing

@Composable
fun ListingDetailItem(listing: Listing, modifier: Modifier = Modifier) {
    val paddingTopModifier = Modifier.padding(top = 8.dp)

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
            .fillMaxSize()
    ) {
        ListingImage(
            url = listing.listingImageURL,
            contentDescription = "Listing image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(200.dp)
        )
        Header(text = stringResource(R.string.for_sale, listing.propertyType))
        Price(price = listing.price, currency = listing.currency, modifier = paddingTopModifier)

        Header(text = stringResource(R.string.overview))

        SurfaceArea(surfaceArea = listing.surface, showIcon = true)
        BedRooms(nrOfBedRooms = listing.nrOfBedrooms, modifier = paddingTopModifier, showIcon = true)
        Rooms(nrOfRooms = listing.nrOfRooms, modifier = paddingTopModifier, showIcon = true)
        City(city = listing.city, modifier = paddingTopModifier)

        Header(text = stringResource(R.string.financial))

        Price(price = listing.price, currency = listing.currency, modifier = paddingTopModifier)
    }
}


@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
    )
}
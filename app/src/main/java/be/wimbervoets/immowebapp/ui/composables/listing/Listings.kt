package be.wimbervoets.immowebapp.ui.composables.listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.wimbervoets.immowebapp.data.model.Listing
import be.wimbervoets.immowebservice.data.service.dto.PropertyType
import java.util.*

@Composable
fun Listings(list: State<List<Listing>>, onNavigateToDetail: (Long) -> Unit, modifier: Modifier = Modifier) {
    // could pass state value also (https://stackoverflow.com/questions/71070913/passing-state-value-or-state-as-composable-function-parameter)
    // the Lazy option lets you lay out the components when they’re visible in the widget viewport (cfr Recyclerview alternative)
    LazyColumn(modifier = modifier) {
        items(list.value) {
            ListingItem(listing = it, onNavigateToDetail)
        }
    }
}

@Composable
fun ListingItem(listing: Listing, onNavigateToDetail: (Long) -> Unit) {
    val paddingTopModifier = Modifier.padding(top = 8.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
               onNavigateToDetail(listing.id) // when clicking on the listing, call the onclick handler and pass the id
            },
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth() // fill the whole available space
                .wrapContentHeight(),
        ) {
            ListingImage(
                url = listing.listingImageURL,
                contentDescription = "Listing image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(200.dp)
            )
            PropertyType(propertyType = listing.propertyType)
            Price(price = listing.price, currency = listing.currency, modifier = paddingTopModifier)
            Row(modifier = paddingTopModifier) {
                BedRooms(listing.nrOfBedrooms, modifier = Modifier.padding(end = 8.dp))
                SurfaceArea(surfaceArea = listing.surface)
            }
            City(city = listing.city, modifier = paddingTopModifier)
        }
    }
}

@Preview
@Composable
fun ListingPreview() {
    MaterialTheme {
        ListingItem(
            listing = Listing(
                id = 1,
                nrOfBedrooms = 2,
                nrOfRooms = 4,
                surface = 100.0,
                price = 20000.0,
                professional = "Luxe property investment group",
                listingImageURL = "https://v.seloger.com/s/crop/590x330/visuels/2/a/l/s/2als8bgr8sd2vezcpsj988mse4olspi5rfzpadqok.jpg",
                propertyType = PropertyType.VILLA,
                city = "Brussels",
                currency = Currency.getInstance("EUR")
            ),
            onNavigateToDetail = { /* noop */ }
        )
    }
}
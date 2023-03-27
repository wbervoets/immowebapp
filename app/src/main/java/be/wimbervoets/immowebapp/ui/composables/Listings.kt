package be.wimbervoets.immowebapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.wimbervoets.immowebapp.R
import be.wimbervoets.immowebapp.data.model.Listing
import be.wimbervoets.immowebservice.data.service.dto.PropertyType
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.NumberFormat
import java.util.*

@Composable
fun PropertyType(propertyType: PropertyType, modifier: Modifier = Modifier) {
    Text(
        text = propertyType.name,
        color = colorResource(id = R.color.grey),
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Start,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun Price(price: Double, currency: Currency, modifier: Modifier = Modifier) {
    val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance()
    currencyFormat.maximumFractionDigits = 0
    currencyFormat.currency = currency

    Text(
        text = currencyFormat.format(price),
        color = colorResource(id = R.color.grey),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Start,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun BedRooms(nrOfBedRooms: Int?, modifier: Modifier = Modifier) {
    if (nrOfBedRooms != null) {
        Text(
            text = "$nrOfBedRooms slp",
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .wrapContentSize()
        )
    }
}

@Composable
fun SurfaceArea(surfaceArea: Double?, modifier: Modifier = Modifier) {
    if (surfaceArea != null) {
        Text(
            text = "$surfaceArea m²",
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .wrapContentSize()
        )
    }
}

@Composable
fun City(city: String, modifier: Modifier = Modifier) {
    Text(
        text = city,
        color = colorResource(id = R.color.black),
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .wrapContentSize()
    )
}

@Composable
fun ListingImage(url: String?, contentDescription: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.FillBounds,
        fallback = painterResource(id = R.drawable.placeholder_house)
    )
}


@Composable
fun Listings(list: State<List<Listing>>, modifier: Modifier = Modifier) {
    // could pass state value also (https://stackoverflow.com/questions/71070913/passing-state-value-or-state-as-composable-function-parameter)
    // the Lazy option lets you lay out the components when they’re visible in the widget viewport (cfr Recyclerview alternative)
    LazyColumn(modifier = modifier) {
        items(list.value) {
            ListingItem(listing = it)
        }
    }
}

@Composable
fun ListingItem(listing: Listing) {
    val paddingTopModifier = Modifier.padding(top = 8.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(), // fill the whole available space
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
fun PropertyTypePreview() {
    MaterialTheme {
        PropertyType(propertyType = PropertyType.VILLA)
    }
}


@Preview
@Composable
fun PricePreview() {
    MaterialTheme {
        Price(price = 10000.0, currency = Currency.getInstance("EUR"))
    }
}

@Preview
@Composable
fun BedroomsPreview() {
    MaterialTheme {
        BedRooms(nrOfBedRooms = 5)
    }
}

@Preview
@Composable
fun SurfaceAreaPreview() {
    MaterialTheme {
        SurfaceArea(surfaceArea = 250.0)
    }
}

@Preview
@Composable
fun CityPreview() {
    MaterialTheme {
        City(city = "Amsterdam")
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
            )
        )
    }
}
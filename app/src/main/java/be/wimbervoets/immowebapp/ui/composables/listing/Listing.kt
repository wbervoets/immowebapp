package be.wimbervoets.immowebapp.ui.composables.listing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.wimbervoets.immowebapp.R
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
fun BedRooms(nrOfBedRooms: Int?, modifier: Modifier = Modifier, showIcon: Boolean = false) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (nrOfBedRooms != null) {
            if (showIcon) {
                Icon(
                    painter = painterResource(R.drawable.ic_bed),
                    contentDescription = "Bed icon"
                )
            }

            Text(
                text = "$nrOfBedRooms slp",
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier
                    .wrapContentSize()
            )
        }
    }
}

@Composable
fun Rooms(nrOfRooms: Int?, modifier: Modifier = Modifier, showIcon: Boolean = false) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (nrOfRooms != null) {
            if (showIcon) {
                Icon(
                    painter = painterResource(R.drawable.ic_room),
                    contentDescription = "Room icon"
                )
            }

            Text(
                text = stringResource(R.string.nr_of_rooms, nrOfRooms),
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier
                    .wrapContentSize()
            )
        }
    }
}

@Composable
fun SurfaceArea(surfaceArea: Double?, modifier: Modifier = Modifier, showIcon: Boolean = false) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (surfaceArea != null) {
            if (showIcon) {
                Icon(
                    painter = painterResource(R.drawable.ic_house),
                    contentDescription = "House icon"
                )
            }

            Text(
                text = "$surfaceArea m²",
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier
                    .wrapContentSize()
            )
        }
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
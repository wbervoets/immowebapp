package be.wimbervoets.immowebservice.data.service.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.*
import java.util.Currency

/*
{
    "bedrooms": 4,
    "city": "Villers-sur-Mer",
    "id": 1,
    "area": 250.0,
    "url": "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg",
    "price": 1500000.0,
    "professional": "GSL EXPLORE",
    "propertyType": "Maison - Villa",
    "offerType": 1,
    "rooms": 8
}

 */

@Serializable
data class ListingDTO(
    val id: Long,
    val city: String,

    @SerialName("price")
    private val _price: Double, // private, use public price which includes currency

    @SerialName("area")
    val surface : Double, // in m2

    val propertyType: PropertyType, // "Maison - Villa"

    val professional: String, // "GSL EXPLORE", GSL STICKINESS, GSL OWNERS, GSL CONTACTING

    val offerType: Int, // 1, 2 maybe Buying or Renting ??

    // Optional fields
    @SerialName("bedrooms")
    val nrOfBedrooms: Int? = null,
    @SerialName("rooms")
    val nrOfRooms: Int? = null,

    @SerialName("url")
    val imageURL: String? = null,
    // https://v.seloger.com/s/crop/1024x590/visuels/2/a/l/s/2als8bgr8sd2vezcpsj988mse4olspi5rfzpadqok.jpg
    // in a real app we should replace the width & height or add this as parameters dynamically so depending on the device an optimal size is downloaded

    private val currencyIsoCode: String = "EUR" // backend should send us the currency code

) {

    @Transient
    // price should always include currency to be i18n compatible
    val price: Pair<Double, Currency> = Pair(_price, Currency.getInstance(currencyIsoCode))
}

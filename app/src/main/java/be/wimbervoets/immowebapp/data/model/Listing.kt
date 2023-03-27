package be.wimbervoets.immowebapp.data.model

import be.wimbervoets.immowebservice.data.service.dto.PropertyType
import java.util.Currency

/*
    Listing class for use by the UI layer

    DTO classes from Immowebservice library module are not used directly in the UI as
     - they could contain information we don't need in the UI (unnecessary complexity)
     - could contain info but not yet in a format that is easy to use in the UI
     - changes or a different API on the backend do not propagate to all UI screens
     (only DTO mapper needs changes)

     (for this example our Listing class is actually quite similar to ListingDTO, so I could have skipped this step)

*/
data class Listing(
    val id: Long,
    val city: String,
    val price: Double,
    val currency: Currency,
    val surface: Double,
    val propertyType: PropertyType,
    val professional: String,
    val nrOfBedrooms: Int? = null,
    val nrOfRooms: Int? = null,
    val listingImageURL: String? = null
)

package be.wimbervoets.immowebservice.mothers

import be.wimbervoets.immowebservice.data.service.dto.ListingDTO
import be.wimbervoets.immowebservice.data.service.dto.ListingsDTO
import be.wimbervoets.immowebservice.data.service.dto.PropertyType

object ListingMother {
    val listing1 = ListingDTO(
        id = 1,
        nrOfBedrooms = 2,
        nrOfRooms = 4,
        surface = 100.0,
        _price = 20000.0,
        professional = "Luxe property investment group",
        propertyType = PropertyType.VILLA,
        offerType = 1,
        city = "Brussels"
    )
    val listing2 = ListingDTO(
        id = 2,
        surface = 500.0,
        _price = 20000.0,
        professional = "Luxe property investment group",
        propertyType = PropertyType.VILLA,
        offerType = 1,
        city = "Leuven",
        imageURL = "https://v.seloger.com/s/crop/1024x590/visuels/2/a/l/s/2als8bgr8sd2vezcpsj988mse4olspi5rfzpadqok.jpg"
    )

    val listingsDTO: ListingsDTO = ListingsDTO(
        listOf(
            listing1, listing2
        )
    )
}
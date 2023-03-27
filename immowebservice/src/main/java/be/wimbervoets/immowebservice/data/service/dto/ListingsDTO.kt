package be.wimbervoets.immowebservice.data.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class ListingsDTO(
    val items: List<ListingDTO> = emptyList()
)


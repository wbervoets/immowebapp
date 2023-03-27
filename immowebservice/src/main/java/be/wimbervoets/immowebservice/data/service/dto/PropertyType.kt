package be.wimbervoets.immowebservice.data.service.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PropertyType {
    @SerialName("Maison - Villa") VILLA
}
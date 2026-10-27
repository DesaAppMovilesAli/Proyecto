
package cr.ac.utn.proyecto_plantcare.domain.model

import java.time.LocalDate

data class Plant(
    val id: String,
    val name: String,
    val species: String,
    val location: PlantLocation,
    val wateringFrequency: WateringFrequency,
    val notes: String,
    val photo: String,
    val wateringDate: LocalDate,
    val locationName: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
)

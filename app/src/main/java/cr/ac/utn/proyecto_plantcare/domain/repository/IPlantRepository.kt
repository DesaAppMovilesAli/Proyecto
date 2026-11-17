
package cr.ac.utn.proyecto_plantcare.domain.repository

import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency

interface IPlantRepository {

    // Add a plant
    fun addPlant(plant: Plant)

    // Delete a plant
    fun deletePlant(id: String)

    // Update a plant
    fun updatePlant(plant: Plant)

    // Get all plants
    fun getPlants(): List<Plant>

    // Get a plant by ID
    fun getById(id: String): Plant?

    // Search and filter plants
    fun filterPlants(
        search: String,
        location: PlantLocation?,
        frequency: WateringFrequency?
    ): List<Plant>
}




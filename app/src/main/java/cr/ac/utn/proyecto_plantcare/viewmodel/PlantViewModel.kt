
package cr.ac.utn.proyecto_plantcare.viewmodel

import androidx.lifecycle.ViewModel
import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency
import cr.ac.utn.proyecto_plantcare.domain.repository.IPlantRepository

class PlantViewModel(
    private val repository: IPlantRepository
) : ViewModel() {

    // Get all plants
    fun getPlants(): List<Plant> {
        return repository.getPlants()
    }

    // Get a plant by ID
    fun getPlantById(id: String): Plant? {
        return repository.getById(id)
    }

    // Create a plant
    fun createPlant(plant: Plant) {
        if (repository.getById(plant.id) == null) {
            repository.addPlant(plant)
        } else {
            throw Exception("The plant already exists.")
        }
    }

    // Update a plant
    fun updatePlant(plant: Plant) {
        if (repository.getById(plant.id) != null) {
            repository.updatePlant(plant)
        } else {
            throw Exception("The plant does not exist.")
        }
    }

    // Delete a plant
    fun removePlant(id: String) {
        if (repository.getById(id) != null) {
            repository.deletePlant(id)
        } else {
            throw Exception("The plant does not exist.")
        }
    }

    // Search and filter plants
    fun filterPlants(
        search: String,
        location: PlantLocation?,
        frequency: WateringFrequency?,
    ): List<Plant> {

        return repository.filterPlants(
            search,
            location,
            frequency
        )
    }
}

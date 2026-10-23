
package cr.ac.utn.proyecto_plantcare.viewmodel

import androidx.lifecycle.ViewModel
import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.repository.IPlantRepository

class PlantViewModel(
    private val repository: IPlantRepository
) : ViewModel() {

    fun getPlants(): List<Plant> {
        return repository.getPlants()
    }

    fun getPlantById(id: String): Plant? {
        return repository.getById(id)
    }

    fun createPlant(plant: Plant) {
        if (repository.getById(plant.id) == null) {
            repository.addPlant(plant)
        } else {
            throw Exception("The plant already exists.")
        }
    }

    fun updatePlant(plant: Plant) {
        if (repository.getById(plant.id) != null) {
            repository.updatePlant(plant)
        } else {
            throw Exception("The plant does not exist.")
        }
    }

    fun removePlant(id: String) {
        if (repository.getById(id) != null) {
            repository.deletePlant(id)
        } else {
            throw Exception("The plant does not exist.")
        }
    }

    fun searchPlants(name: String): List<Plant> {
        return repository.searchPlants(name)
    }

    fun getPlantsByLocation(
        location: PlantLocation
    ): List<Plant> {
        return repository.getPlantsByLocation(location)
    }

    fun getPlantsBySpecies(species: String): List<Plant> {
        return repository.getPlantsBySpecies(species)
    }

    fun getPlantsByNextWatering(): List<Plant> {
        return repository.getPlantsByNextWatering()
    }
}


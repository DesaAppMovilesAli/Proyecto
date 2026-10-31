package cr.ac.utn.proyecto_plantcare.viewmodel

import androidx.lifecycle.ViewModel
import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency
import cr.ac.utn.proyecto_plantcare.domain.repository.IPlantRepository

class PlantViewModel(
    private val repository: IPlantRepository
) : ViewModel() {

    // Obtener todas las plantas
    fun getPlants(): List<Plant> {
        return repository.getPlants()
    }

    // Obtener una planta por ID
    fun getPlantById(id: String): Plant? {
        return repository.getById(id)
    }

    // Crear una planta
    fun createPlant(plant: Plant) {
        if (repository.getById(plant.id) == null) {
            repository.addPlant(plant)
        } else {
            throw Exception("The plant already exists.")
        }
    }

    // Actualizar una planta
    fun updatePlant(plant: Plant) {
        if (repository.getById(plant.id) != null) {
            repository.updatePlant(plant)
        } else {
            throw Exception("The plant does not exist.")
        }
    }

    // Eliminar una planta
    fun removePlant(id: String) {
        if (repository.getById(id) != null) {
            repository.deletePlant(id)
        } else {
            throw Exception("The plant does not exist.")
        }
    }

    // Buscar plantas por nombre o ID
    fun searchPlants(name: String): List<Plant> {
        return repository.searchPlants(name)
    }

    // Filtrar por ubicación
    fun getPlantsByLocation(
        location: PlantLocation
    ): List<Plant> {
        return repository.getPlantsByLocation(location)
    }

    // Filtrar por especie
    fun getPlantsBySpecies(
        species: String
    ): List<Plant> {
        return repository.getPlantsBySpecies(species)
    }

    // Obtener las especies registradas por el usuario
    fun getRegisteredSpecies(): List<String> {
        return repository.getRegisteredSpecies()
    }

    // Aplicar los filtros seleccionados
    fun filterPlants(
        search: String,
        location: PlantLocation?,
        frequency: WateringFrequency?,
        species: String
    ): List<Plant> {

        return repository.filterPlants(
            search,
            location,
            frequency,
            species
        )
    }
}


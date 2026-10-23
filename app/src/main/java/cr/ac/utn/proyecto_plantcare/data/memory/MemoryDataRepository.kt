
package cr.ac.utn.proyecto_plantcare.data.memory

import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.repository.IPlantRepository
import cr.ac.utn.proyecto_plantcare.Util.getNextWateringDate

class MemoryDataRepository : IPlantRepository {

    private val plants = mutableListOf<Plant>()

    override fun addPlant(plant: Plant) {
        plants.add(plant)
    }

    override fun deletePlant(id: String) {
        plants.removeIf { it.id.trim() == id.trim() }
    }

    override fun updatePlant(plant: Plant) {
        deletePlant(plant.id)
        addPlant(plant)
    }

    override fun getPlants() = plants

    override fun getById(id: String): Plant? {
        val result = plants.filter { it.id.trim() == id.trim() }
        return if (result.any()) result[0] else null
    }

    override fun searchPlants(name: String): List<Plant> {
        return plants.filter {
            it.name.contains(name.trim(), ignoreCase = true)
        }
    }

    override fun getPlantsByLocation(
        location: PlantLocation
    ): List<Plant> {
        return plants.filter { it.location == location }
    }

    override fun getPlantsBySpecies(
        species: String
    ): List<Plant> {
        return plants.filter {
            it.species.contains(species.trim(), ignoreCase = true)
        }
    }

    override fun getPlantsByNextWatering(): List<Plant> {
        return plants.sortedBy {
            getNextWateringDate(
                it.wateringDate,
                it.wateringFrequency
            )
        }
    }
}


package cr.ac.utn.proyecto_plantcare.data.memory

import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency
import cr.ac.utn.proyecto_plantcare.domain.repository.IPlantRepository

class MemoryDataRepository : IPlantRepository {

    companion object {
        val shared = MemoryDataRepository()
    }

    private val plants = mutableListOf<Plant>()

    // Add a plant
    override fun addPlant(plant: Plant) {
        plants.add(plant)
    }

    // Delete a plant by ID
    override fun deletePlant(id: String) {
        plants.removeIf {
            it.id.trim().equals(
                id.trim(),
                ignoreCase = true
            )
        }
    }

    // Update a plant
    override fun updatePlant(plant: Plant) {
        deletePlant(plant.id)
        addPlant(plant)
    }

    // Get all plants
    override fun getPlants(): List<Plant> {
        return plants
    }

    // Get a plant by ID
    override fun getById(id: String): Plant? {
        return plants.find {
            it.id.trim().equals(
                id.trim(),
                ignoreCase = true
            )
        }
    }

    // Search and filter plants
    override fun filterPlants(
        search: String,
        location: PlantLocation?,
        frequency: WateringFrequency?
    ): List<Plant> {

        val searchText = search.trim()

        return plants.filter { plant ->

            val matchesSearch =
                searchText.isEmpty() ||
                        plant.name.contains(
                            searchText,
                            ignoreCase = true
                        ) ||
                        plant.id.equals(
                            searchText,
                            ignoreCase = true
                        )

            val matchesLocation =
                location == null ||
                        plant.location == location

            val matchesFrequency =
                frequency == null ||
                        plant.wateringFrequency == frequency


            matchesSearch &&
                    matchesLocation &&
                    matchesFrequency
        }
    }
}

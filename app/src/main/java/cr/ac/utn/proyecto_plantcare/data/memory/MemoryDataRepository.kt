
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

    // Agregar una planta
    override fun addPlant(plant: Plant) {
        plants.add(plant)
    }

    // Eliminar una planta por ID

    override fun deletePlant(id: String) {
        plants.removeIf {
            it.id.trim().equals(
                id.trim(),
                ignoreCase = true
            )
        }
    }


    // Actualizar una planta
    override fun updatePlant(plant: Plant) {
        deletePlant(plant.id)
        addPlant(plant)
    }

    // Obtener todas las plantas
    override fun getPlants(): List<Plant> {
        return plants
    }

    // Buscar una planta por ID
    override fun getById(id: String): Plant? {
        return plants.find {
            it.id.trim().equals(
                id.trim(),
                ignoreCase = true
            )
        }
    }

    // Buscar plantas por nombre o ID
    override fun searchPlants(name: String): List<Plant> {

        val search = name.trim()

        return plants.filter {
            it.name.contains(
                search,
                ignoreCase = true
            ) ||
                    it.id.equals(
                        search,
                        ignoreCase = true
                    )
        }
    }

    // Filtrar plantas por ubicación
    override fun getPlantsByLocation(
        location: PlantLocation
    ): List<Plant> {

        return plants.filter {
            it.location == location
        }
    }

    // Filtrar plantas por especie
    override fun getPlantsBySpecies(
        species: String
    ): List<Plant> {

        return plants.filter {
            it.species.contains(
                species.trim(),
                ignoreCase = true
            )
        }
    }

    // Obtener las especies registradas sin repetir
    override fun getRegisteredSpecies(): List<String> {

        return plants
            .map { it.species.trim() }
            .filter { it.isNotEmpty() }
            .distinctBy { it.lowercase() }
            .sorted()
    }

    // Filtrar por nombre o ID, ubicación,
    // frecuencia de riego y especie
    override fun filterPlants(
        search: String,
        location: PlantLocation?,
        frequency: WateringFrequency?,
        species: String
    ): List<Plant> {

        val searchText = search.trim()
        val speciesText = species.trim()

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

            val matchesSpecies =
                speciesText.isEmpty() ||
                        plant.species.equals(
                            speciesText,
                            ignoreCase = true
                        )

            matchesSearch &&
                    matchesLocation &&
                    matchesFrequency &&
                    matchesSpecies
        }
    }
}

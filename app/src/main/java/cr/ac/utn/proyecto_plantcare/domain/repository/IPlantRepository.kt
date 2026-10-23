
package cr.ac.utn.proyecto_plantcare.domain.repository

import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation

interface IPlantRepository {

    fun addPlant(plant: Plant)

    fun deletePlant(id: String)

    fun updatePlant(plant: Plant)

    fun getPlants(): List<Plant>

    fun getById(id: String): Plant?

    fun searchPlants(name: String): List<Plant>

    fun getPlantsByLocation(location: PlantLocation): List<Plant>

    fun getPlantsBySpecies(species: String): List<Plant>

    fun getPlantsByNextWatering(): List<Plant>
}


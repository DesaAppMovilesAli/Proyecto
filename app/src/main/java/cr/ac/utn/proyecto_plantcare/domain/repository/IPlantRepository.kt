package cr.ac.utn.proyecto_plantcare.domain.repository

import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency

interface IPlantRepository {

    // Agregar una planta
    fun addPlant(plant: Plant)

    // Eliminar una planta
    fun deletePlant(id: String)

    // Actualizar una planta
    fun updatePlant(plant: Plant)

    // Obtener todas las plantas
    fun getPlants(): List<Plant>

    // Obtener una planta por ID
    fun getById(id: String): Plant?

    // Buscar plantas por nombre o ID
    fun searchPlants(name: String): List<Plant>

    // Filtrar por ubicación
    fun getPlantsByLocation(
        location: PlantLocation
    ): List<Plant>

    // Filtrar por especie
    fun getPlantsBySpecies(
        species: String
    ): List<Plant>

    // Obtener las especies registradas por el usuario
    fun getRegisteredSpecies(): List<String>

    // Combinar los filtros
    fun filterPlants(
        search: String,
        location: PlantLocation?,
        frequency: WateringFrequency?,
        species: String
    ): List<Plant>
}



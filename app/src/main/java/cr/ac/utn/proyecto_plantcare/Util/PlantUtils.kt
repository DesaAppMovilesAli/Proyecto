
package cr.ac.utn.proyecto_plantcare.Util

import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency
import java.time.LocalDate

fun getWateringDays(frequency: WateringFrequency): Int {
    return when (frequency) {
        WateringFrequency.DAILY -> 1
        WateringFrequency.EVERY_THREE_DAYS -> 3
        WateringFrequency.WEEKLY -> 7
        WateringFrequency.EVERY_FIFTEEN_DAYS -> 15
    }
}

fun isValidPlantName(name: String): Boolean {
    return name.trim().isNotEmpty()
}

fun getNextWateringDate(
    wateringDate: LocalDate,
    frequency: WateringFrequency
): LocalDate {
    val days = getWateringDays(frequency)
    return wateringDate.plusDays(days.toLong())
}


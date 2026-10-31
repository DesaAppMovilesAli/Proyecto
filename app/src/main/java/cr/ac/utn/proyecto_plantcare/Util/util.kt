package cr.ac.utn.proyecto_plantcare.Util

import android.content.Context
import android.content.Intent
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency

class util {

    companion object {

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

        fun openActivity(context: Context, classScreen: Class<*>) {
            val intent = Intent(context, classScreen)
            context.startActivity(intent)
        }
    }
}

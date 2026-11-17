package cr.ac.utn.proyecto_plantcare.Util

import android.content.Context
import android.content.Intent

class util {

    companion object {

        fun openActivity(
            context: Context,
            classScreen: Class<*>
        ) {
            val intent = Intent(context, classScreen)
            context.startActivity(intent)
        }
    }
}


package cr.ac.utn.proyecto_plantcare

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.proyecto_plantcare.data.memory.MemoryDataRepository
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency
import cr.ac.utn.proyecto_plantcare.viewmodel.PlantViewModel

class PlantDetailActivity : AppCompatActivity() {

    private val plantViewModel =
        PlantViewModel(MemoryDataRepository.shared)

    private var plantId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_plant_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get selected plant ID
        plantId = intent.getStringExtra("plant_id") ?: ""

        // Edit plant button
        val btnEditPlant = findViewById<Button>(
            R.id.btnEditPlant
        )

        btnEditPlant.setOnClickListener(View.OnClickListener { view ->

            val intent = Intent(
                this,
                EditPlantActivity::class.java
            )

            intent.putExtra("plant_id", plantId)

            startActivity(intent)
        })

        // Delete plant button
        val btnDeletePlant = findViewById<Button>(
            R.id.btnDeletePlant
        )

        btnDeletePlant.setOnClickListener(View.OnClickListener { view ->
            confirmDeletePlant()
        })

        // Home button
        val btnHome = findViewById<Button>(
            R.id.btnHome
        )

        btnHome.setOnClickListener(View.OnClickListener { view ->
            finish()
        })
    }

    override fun onResume() {
        super.onResume()
        loadPlant()
    }

    private fun confirmDeletePlant() {

        val builder = AlertDialog.Builder(this)

        builder.setMessage(R.string.dialog_delete)

        builder.setPositiveButton(
            R.string.dialog_yes
        ) { dialog, which ->

            try {

                plantViewModel.removePlant(plantId)

                Toast.makeText(
                    this,
                    getString(R.string.msg_plant_deleted),
                    Toast.LENGTH_LONG
                ).show()

                finish()

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    getString(R.string.delete_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        builder.setNegativeButton(
            R.string.dialog_no
        ) { dialog, which ->

            dialog.dismiss()
        }

        builder.show()
    }

    private fun loadPlant() {

        val plant = plantViewModel.getPlantById(plantId)

        if (plant != null) {

            // Plant ID
            val tvPlantId = findViewById<TextView>(
                R.id.tvPlantId
            )

            tvPlantId.text = plant.id

            // Plant name
            val tvPlantName = findViewById<TextView>(
                R.id.tvPlantName
            )

            tvPlantName.text = plant.name

            // Plant species
            val tvSpecie = findViewById<TextView>(
                R.id.tvSpecie
            )

            tvSpecie.text = plant.species

            // Plant location
            val tvLocation = findViewById<TextView>(
                R.id.tvLocation
            )

            val locationText = when (plant.location) {

                PlantLocation.INDOOR ->
                    getString(R.string.plant_indoor)

                PlantLocation.OUTDOOR ->
                    getString(R.string.plant_outdoor)
            }

            tvLocation.text = locationText

            // Watering frequency
            val tvWateringFrequency = findViewById<TextView>(
                R.id.tvWateringFrequency
            )

            val frequencyText = when (plant.wateringFrequency) {

                WateringFrequency.DAILY ->
                    getString(R.string.watering_daily)

                WateringFrequency.EVERY_THREE_DAYS ->
                    getString(R.string.watering_three_days)

                WateringFrequency.WEEKLY ->
                    getString(R.string.watering_weekly)

                WateringFrequency.EVERY_FIFTEEN_DAYS ->
                    getString(R.string.watering_fifteen_days)
            }

            tvWateringFrequency.text = frequencyText

            // Acquisition date
            val tvAcqDate = findViewById<TextView>(
                R.id.tvAcqDate
            )

            tvAcqDate.text = plant.acquisitionDate.toString()

            // Plant notes
            val tvNotes = findViewById<TextView>(
                R.id.tvNotes
            )

            tvNotes.text = plant.notes

        } else {

            Toast.makeText(
                this,
                getString(R.string.load_error),
                Toast.LENGTH_LONG
            ).show()

            finish()
        }
    }
}


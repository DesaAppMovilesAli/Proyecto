
package cr.ac.utn.proyecto_plantcare

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.proyecto_plantcare.data.memory.MemoryDataRepository
import cr.ac.utn.proyecto_plantcare.domain.model.Plant
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency
import cr.ac.utn.proyecto_plantcare.viewmodel.PlantViewModel
import java.time.LocalDate

class EditPlantActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener {

    private val plantViewModel =
        PlantViewModel(MemoryDataRepository.shared)

    private var plantId: String = ""

    private lateinit var currentPlant: Plant

    private lateinit var txtName: EditText
    private lateinit var txtSpecies: EditText
    private lateinit var txtNotes: EditText

    private lateinit var tvPlantName: TextView
    private lateinit var tvPlantSpecie: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvWateringFrequency: TextView
    private lateinit var tvAcqDate: TextView
    private lateinit var tvNotes: TextView

    private var newAcquisitionDate: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_plant)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get selected plant ID
        plantId = intent.getStringExtra("plant_id") ?: ""

        // Current plant information
        tvPlantName = findViewById(R.id.tvPlantName)
        tvPlantSpecie = findViewById(R.id.tvPlantSpecie)
        tvLocation = findViewById(R.id.tvLocation)
        tvWateringFrequency =
            findViewById(R.id.tvWateringFrequency)
        tvAcqDate = findViewById(R.id.tvAcqDate)
        tvNotes = findViewById(R.id.tvNotes)

        // New plant information
        txtName = findViewById(R.id.etEditPlantName)
        txtSpecies = findViewById(R.id.etEditPlantSpecies)
        txtNotes = findViewById(R.id.etEditPlantNotes)

        // Load plant information
        if (!loadPlant()) {
            finish()
            return
        }

        // Acquisition date button
        val btnAcqDate = findViewById<ImageButton>(
            R.id.btnAcqDate
        )

        btnAcqDate.setOnClickListener(View.OnClickListener { view ->
            showDatePickerDialog()
        })

        // Save changes button
        val btnSaveChanges = findViewById<Button>(
            R.id.btnSaveChanges
        )

        btnSaveChanges.setOnClickListener(View.OnClickListener { view ->
            confirmUpdatePlant()
        })

        // Cancel button
        val btnCancel = findViewById<Button>(
            R.id.btnEditCancel
        )

        btnCancel.setOnClickListener(View.OnClickListener { view ->
            finish()
        })

        // Back button
        val btnBack = findViewById<Button>(
            R.id.btnBack
        )

        btnBack.setOnClickListener(View.OnClickListener { view ->
            finish()
        })
    }

    private fun loadPlant(): Boolean {

        val plant = plantViewModel.getPlantById(plantId)

        if (plant == null) {

            Toast.makeText(
                this,
                getString(R.string.load_error),
                Toast.LENGTH_LONG
            ).show()

            return false
        }

        currentPlant = plant

        // Current name
        tvPlantName.text = plant.name

        // Current species
        tvPlantSpecie.text = plant.species

        // Current location
        val locationText = when (plant.location) {

            PlantLocation.INDOOR ->
                getString(R.string.plant_indoor)

            PlantLocation.OUTDOOR ->
                getString(R.string.plant_outdoor)
        }

        tvLocation.text = locationText

        // Current watering frequency
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

        // Current acquisition date
        tvAcqDate.text = formatDate(
            plant.acquisitionDate
        )

        // Current notes
        tvNotes.text = plant.notes

        return true
    }

    private fun formatDate(date: LocalDate): String {

        return "${date.dayOfMonth}/${date.monthValue}/${date.year}"
    }

    private fun showDatePickerDialog() {

        val date = newAcquisitionDate
            ?: currentPlant.acquisitionDate

        val datePickerDialog = DatePickerDialog(
            this,
            this,
            date.year,
            date.monthValue - 1,
            date.dayOfMonth
        )

        datePickerDialog.show()
    }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {

        newAcquisitionDate = LocalDate.of(
            year,
            month + 1,
            dayOfMonth
        )

        tvAcqDate.text =
            formatDate(currentPlant.acquisitionDate) +
                    "  ->  " +
                    formatDate(newAcquisitionDate!!)
    }

    private fun getSelectedLocation(): PlantLocation {

        return when {

            findViewById<RadioButton>(
                R.id.rbIndoor
            ).isChecked ->
                PlantLocation.INDOOR

            findViewById<RadioButton>(
                R.id.rbOutdoor
            ).isChecked ->
                PlantLocation.OUTDOOR

            else -> currentPlant.location
        }
    }

    private fun getSelectedFrequency(): WateringFrequency {

        return when {

            findViewById<RadioButton>(
                R.id.rbWaterDaily
            ).isChecked ->
                WateringFrequency.DAILY

            findViewById<RadioButton>(
                R.id.rbWaterThreeDays
            ).isChecked ->
                WateringFrequency.EVERY_THREE_DAYS

            findViewById<RadioButton>(
                R.id.rbWaterWeekly
            ).isChecked ->
                WateringFrequency.WEEKLY

            findViewById<RadioButton>(
                R.id.rbWaterFifteenDays
            ).isChecked ->
                WateringFrequency.EVERY_FIFTEEN_DAYS

            else -> currentPlant.wateringFrequency
        }
    }

    private fun isValidationData(): Boolean {

        val name = txtName.text.toString().trim()
        val species = txtSpecies.text.toString().trim()

        val finalName = if (name.isNotBlank()) {
            name
        } else {
            currentPlant.name
        }

        val finalSpecies = if (species.isNotBlank()) {
            species
        } else {
            currentPlant.species
        }

        return finalName.isNotBlank()
                && finalSpecies.isNotBlank()
    }

    private fun confirmUpdatePlant() {

        if (!isValidationData()) {

            Toast.makeText(
                this,
                getString(R.string.invalid_data),
                Toast.LENGTH_LONG
            ).show()

            return
        }

        val builder = AlertDialog.Builder(this)

        builder.setMessage(R.string.dialog_update)

        builder.setPositiveButton(
            R.string.dialog_yes
        ) { dialog, which ->

            updatePlant()
        }

        builder.setNegativeButton(
            R.string.dialog_no
        ) { dialog, which ->

            dialog.dismiss()
        }

        builder.show()
    }

    private fun updatePlant() {

        try {

            val name = txtName.text.toString().trim()
            val species = txtSpecies.text.toString().trim()
            val notes = txtNotes.text.toString().trim()

            // Keep previous values when fields are empty
            val finalName = if (name.isNotBlank()) {
                name
            } else {
                currentPlant.name
            }

            val finalSpecies = if (species.isNotBlank()) {
                species
            } else {
                currentPlant.species
            }

            val finalNotes = if (notes.isNotBlank()) {
                notes
            } else {
                currentPlant.notes
            }

            // Create the updated plant
            val updatedPlant = Plant(
                id = currentPlant.id,
                name = finalName,
                species = finalSpecies,
                location = getSelectedLocation(),
                wateringFrequency = getSelectedFrequency(),
                notes = finalNotes,
                photo = currentPlant.photo,
                acquisitionDate = newAcquisitionDate
                    ?: currentPlant.acquisitionDate,
                locationName = currentPlant.locationName,
                latitude = currentPlant.latitude,
                longitude = currentPlant.longitude
            )

            // Update the existing plant
            plantViewModel.updatePlant(updatedPlant)

            Toast.makeText(
                this,
                getString(R.string.msg_plant_updated),
                Toast.LENGTH_LONG
            ).show()

            finish()

        } catch (e: Exception) {

            Toast.makeText(
                this,
                getString(R.string.update_error),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

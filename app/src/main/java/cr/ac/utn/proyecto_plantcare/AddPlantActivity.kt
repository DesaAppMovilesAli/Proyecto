package cr.ac.utn.proyecto_plantcare

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
import java.util.Calendar

class AddPlantActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener {

    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtSpecies: EditText
    private lateinit var txtNotes: EditText
    private lateinit var tvAcqDate: TextView

    private var acqDay: Int = 0
    private var acqMonth: Int = 0
    private var acqYear: Int = 0

    private val plantViewModel =
        PlantViewModel(MemoryDataRepository.shared)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_plant)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // PlantCare code

        txtId = findViewById(R.id.etPlantID)
        txtName = findViewById(R.id.etPlantName)
        txtSpecies = findViewById(R.id.etPlantSpecies)
        txtNotes = findViewById(R.id.etPlantNotes)
        tvAcqDate = findViewById(R.id.tvAcqDate)

        resetDate()

        val btnAcqDate =
            findViewById<ImageButton>(R.id.btnAcqDate)

        val btnSave =
            findViewById<Button>(R.id.btnSave)

        val btnCancel =
            findViewById<Button>(R.id.btnCancel2)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnAcqDate.setOnClickListener(View.OnClickListener { view ->
            showDatePickerDialog()
        })

        btnSave.setOnClickListener(View.OnClickListener { view ->
            savePlant()
        })

        btnCancel.setOnClickListener(View.OnClickListener { view ->
            finish()
        })

        btnBack.setOnClickListener(View.OnClickListener { view ->
            finish()
        })
    }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        acqDay = dayOfMonth
        acqMonth = month
        acqYear = year

        tvAcqDate.text = getDateFormatString(
            acqDay,
            acqMonth,
            acqYear
        )
    }

    private fun resetDate() {
        val calendar = Calendar.getInstance()

        acqDay = calendar.get(Calendar.DAY_OF_MONTH)
        acqMonth = calendar.get(Calendar.MONTH)
        acqYear = calendar.get(Calendar.YEAR)

        tvAcqDate.text = getDateFormatString(
            acqDay,
            acqMonth,
            acqYear
        )
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            this,
            acqYear,
            acqMonth,
            acqDay
        )

        datePickerDialog.show()
    }

    private fun getDateFormatString(
        day: Int,
        month: Int,
        year: Int
    ): String {
        return "$day/${month + 1}/$year"
    }

    private fun isValidationData(): Boolean {
        return txtId.text.toString().trim().isNotBlank()
                && txtName.text.toString().trim().isNotBlank()
                && txtSpecies.text.toString().trim().isNotBlank()
                && tvAcqDate.text.toString().trim().isNotBlank()
                && getSelectedLocation() != null
                && getSelectedFrequency() != null
    }

    private fun getSelectedLocation(): PlantLocation? {

        return when {
            findViewById<RadioButton>(
                R.id.rbIndoor
            ).isChecked -> PlantLocation.INDOOR

            findViewById<RadioButton>(
                R.id.rbOutdoor
            ).isChecked -> PlantLocation.OUTDOOR

            else -> null
        }
    }

    private fun getSelectedFrequency(): WateringFrequency? {

        return when {
            findViewById<RadioButton>(
                R.id.rbWaterDaily
            ).isChecked -> WateringFrequency.DAILY

            findViewById<RadioButton>(
                R.id.rbWaterThreeDays
            ).isChecked ->
                WateringFrequency.EVERY_THREE_DAYS

            findViewById<RadioButton>(
                R.id.rbWaterWeekly
            ).isChecked -> WateringFrequency.WEEKLY

            findViewById<RadioButton>(
                R.id.rbWaterFifteenDays
            ).isChecked ->
                WateringFrequency.EVERY_FIFTEEN_DAYS

            else -> null
        }
    }

    private fun savePlant() {
        try {
            if (isValidationData()) {

                val id = txtId.text.toString().trim()

                val existingPlant =
                    plantViewModel.getPlantById(id)

                if (existingPlant != null) {

                    Toast.makeText(
                        this,
                        getString(R.string.duplicate_plant),
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    val acquisitionDate = LocalDate.of(
                        acqYear,
                        acqMonth + 1,
                        acqDay
                    )

                    val plant = Plant(
                        id = id,
                        name = txtName.text.toString().trim(),
                        species = txtSpecies.text.toString().trim(),
                        location = getSelectedLocation()!!,
                        wateringFrequency = getSelectedFrequency()!!,
                        notes = txtNotes.text.toString().trim(),
                        photo = "",
                        acquisitionDate = acquisitionDate
                    )

                    plantViewModel.createPlant(plant)

                    Toast.makeText(
                        this,
                        getString(R.string.msg_plant_saved),
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }

            } else {

                Toast.makeText(
                    this,
                    getString(R.string.invalid_data),
                    Toast.LENGTH_LONG
                ).show()
            }

        } catch (e: Exception) {

            Toast.makeText(
                this,
                e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

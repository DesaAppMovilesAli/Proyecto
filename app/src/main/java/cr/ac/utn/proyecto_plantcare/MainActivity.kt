package cr.ac.utn.proyecto_plantcare

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.proyecto_plantcare.Util.util
import cr.ac.utn.proyecto_plantcare.data.memory.MemoryDataRepository
import cr.ac.utn.proyecto_plantcare.domain.model.PlantLocation
import cr.ac.utn.proyecto_plantcare.domain.model.WateringFrequency
import cr.ac.utn.proyecto_plantcare.viewmodel.PlantViewModel

class MainActivity : AppCompatActivity() {

    private val plantViewModel =
        PlantViewModel(MemoryDataRepository.shared)

    private var selectedLocation: PlantLocation? = null
    private var selectedFrequency: WateringFrequency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Add plant button

        val btnAddPlant = findViewById<Button>(
            R.id.btnAddPlant
        )

        btnAddPlant.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(
                this,
                AddPlantActivity::class.java
            )
        })

        // Map button

        val btnMap = findViewById<Button>(
            R.id.btnMap
        )

        btnMap.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(
                this,
                MapActivity::class.java
            )
        })

        // Search plants

        val etSearchPlant = findViewById<EditText>(
            R.id.etSearchPlant
        )

        etSearchPlant.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    showPlants()
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )

        // Filter buttons

        val btnAllPlants = findViewById<Button>(
            R.id.btnAllPlants
        )

        val btnIndoorPlants = findViewById<Button>(
            R.id.btnIndoorPlants
        )

        val btnOutdoorPlants = findViewById<Button>(
            R.id.btnOutdoorPlants
        )

        val btnDailyPlants = findViewById<Button>(
            R.id.btnDailyPlants
        )

        val btnThreeDaysPlants = findViewById<Button>(
            R.id.btnThreeDaysPlants
        )

        val btnWeeklyPlants = findViewById<Button>(
            R.id.btnWeeklyPlants
        )

        val btnFifteenDaysPlants = findViewById<Button>(
            R.id.btnFifteenDaysPlants
        )

        // All plants

        btnAllPlants.setOnClickListener(View.OnClickListener { view ->

            selectedLocation = null
            selectedFrequency = null

            updateFilterButtons()
            showPlants()
        })

        // Indoor plants

        btnIndoorPlants.setOnClickListener(View.OnClickListener { view ->

            if (selectedLocation == PlantLocation.INDOOR) {
                selectedLocation = null
            } else {
                selectedLocation = PlantLocation.INDOOR
            }

            updateFilterButtons()
            showPlants()
        })

        // Outdoor plants

        btnOutdoorPlants.setOnClickListener(View.OnClickListener { view ->

            if (selectedLocation == PlantLocation.OUTDOOR) {
                selectedLocation = null
            } else {
                selectedLocation = PlantLocation.OUTDOOR
            }

            updateFilterButtons()
            showPlants()
        })

        // Daily watering

        btnDailyPlants.setOnClickListener(View.OnClickListener { view ->

            if (selectedFrequency == WateringFrequency.DAILY) {
                selectedFrequency = null
            } else {
                selectedFrequency = WateringFrequency.DAILY
            }

            updateFilterButtons()
            showPlants()
        })

        // Every 3 days

        btnThreeDaysPlants.setOnClickListener(View.OnClickListener { view ->

            if (selectedFrequency == WateringFrequency.EVERY_THREE_DAYS) {
                selectedFrequency = null
            } else {
                selectedFrequency = WateringFrequency.EVERY_THREE_DAYS
            }

            updateFilterButtons()
            showPlants()
        })

        // Weekly watering

        btnWeeklyPlants.setOnClickListener(View.OnClickListener { view ->

            if (selectedFrequency == WateringFrequency.WEEKLY) {
                selectedFrequency = null
            } else {
                selectedFrequency = WateringFrequency.WEEKLY
            }

            updateFilterButtons()
            showPlants()
        })

        // Every 15 days

        btnFifteenDaysPlants.setOnClickListener(View.OnClickListener { view ->

            if (selectedFrequency == WateringFrequency.EVERY_FIFTEEN_DAYS) {
                selectedFrequency = null
            } else {
                selectedFrequency = WateringFrequency.EVERY_FIFTEEN_DAYS
            }

            updateFilterButtons()
            showPlants()
        })

        // Initial filter colors

        updateFilterButtons()

        // Show registered plants

        showPlants()
    }

    override fun onResume() {
        super.onResume()
        showPlants()
    }

    private fun updateFilterButtons() {

        val btnAllPlants = findViewById<Button>(
            R.id.btnAllPlants
        )

        val btnIndoorPlants = findViewById<Button>(
            R.id.btnIndoorPlants
        )

        val btnOutdoorPlants = findViewById<Button>(
            R.id.btnOutdoorPlants
        )

        val btnDailyPlants = findViewById<Button>(
            R.id.btnDailyPlants
        )

        val btnThreeDaysPlants = findViewById<Button>(
            R.id.btnThreeDaysPlants
        )

        val btnWeeklyPlants = findViewById<Button>(
            R.id.btnWeeklyPlants
        )

        val btnFifteenDaysPlants = findViewById<Button>(
            R.id.btnFifteenDaysPlants
        )

        val allSelected =
            selectedLocation == null &&
                    selectedFrequency == null

        updateButtonColor(btnAllPlants, allSelected)

        updateButtonColor(
            btnIndoorPlants,
            selectedLocation == PlantLocation.INDOOR
        )

        updateButtonColor(
            btnOutdoorPlants,
            selectedLocation == PlantLocation.OUTDOOR
        )

        updateButtonColor(
            btnDailyPlants,
            selectedFrequency == WateringFrequency.DAILY
        )

        updateButtonColor(
            btnThreeDaysPlants,
            selectedFrequency == WateringFrequency.EVERY_THREE_DAYS
        )

        updateButtonColor(
            btnWeeklyPlants,
            selectedFrequency == WateringFrequency.WEEKLY
        )

        updateButtonColor(
            btnFifteenDaysPlants,
            selectedFrequency == WateringFrequency.EVERY_FIFTEEN_DAYS
        )
    }

    private fun updateButtonColor(
        button: Button,
        isSelected: Boolean
    ) {

        if (isSelected) {

            button.backgroundTintList = ColorStateList.valueOf(
                getColor(R.color.plant_green)
            )

            button.setTextColor(
                getColor(R.color.plant_white)
            )

        } else {

            button.backgroundTintList = ColorStateList.valueOf(
                getColor(R.color.plant_pink)
            )

            button.setTextColor(
                getColor(R.color.plant_green_dark)
            )
        }
    }

    private fun showPlants() {

        val plantContainer = findViewById<LinearLayout>(
            R.id.plantContainer
        )

        plantContainer.removeAllViews()

        // Get search text

        val etSearchPlant = findViewById<EditText>(
            R.id.etSearchPlant
        )

        val searchText = etSearchPlant.text.toString().trim()

        // Search and filter plants

        val plants = plantViewModel.filterPlants(
            searchText,
            selectedLocation,
            selectedFrequency,

        )

        if (plants.isEmpty()) {

            val tvMessage = TextView(this)

            tvMessage.setText(R.string.no_data_found)
            tvMessage.textSize = 17f

            tvMessage.setTextColor(
                getColor(R.color.plant_gray)
            )

            plantContainer.addView(tvMessage)

        } else {

            for (plant in plants) {

                // Plant card

                val plantCard = LinearLayout(this)

                plantCard.orientation = LinearLayout.VERTICAL

                plantCard.setPadding(24, 20, 24, 20)

                plantCard.setBackgroundColor(
                    getColor(R.color.plant_white)
                )

                // Plant name

                val tvName = TextView(this)

                tvName.text = plant.name + "  >"
                tvName.textSize = 21f

                tvName.setTextColor(
                    getColor(R.color.plant_green_dark)
                )

                tvName.setTypeface(
                    null,
                    Typeface.BOLD
                )

                plantCard.addView(tvName)

                // Plant species

                val tvSpecies = TextView(this)

                tvSpecies.text = plant.species
                tvSpecies.textSize = 14f

                tvSpecies.setTextColor(
                    getColor(R.color.plant_gray)
                )

                tvSpecies.setPadding(0, 5, 0, 5)

                plantCard.addView(tvSpecies)

                // Plant location

                val tvLocation = TextView(this)

                val locationText = when (plant.location) {

                    PlantLocation.INDOOR ->
                        getString(R.string.plant_indoor)

                    PlantLocation.OUTDOOR ->
                        getString(R.string.plant_outdoor)
                }

                tvLocation.text = locationText
                tvLocation.textSize = 15f

                tvLocation.setTextColor(
                    getColor(R.color.plant_green)
                )

                tvLocation.setPadding(0, 8, 0, 8)

                plantCard.addView(tvLocation)

                // Watering frequency

                val tvFrequency = TextView(this)

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

                tvFrequency.text =
                    getString(R.string.watering_frequency) +
                            ": " + frequencyText

                tvFrequency.textSize = 14f

                tvFrequency.setTextColor(
                    getColor(R.color.plant_green_dark)
                )

                plantCard.addView(tvFrequency)

                // Plant ID

                val tvId = TextView(this)

                tvId.text =
                    getString(R.string.plant_id) +
                            ": " + plant.id

                tvId.textSize = 12f

                tvId.setTextColor(
                    getColor(R.color.plant_gray)
                )

                tvId.setPadding(0, 10, 0, 0)

                plantCard.addView(tvId)

                // Open plant details

                plantCard.setOnClickListener(
                    View.OnClickListener { view ->

                        val intent = Intent(
                            this,
                            PlantDetailActivity::class.java
                        )

                        intent.putExtra(
                            "plant_id",
                            plant.id
                        )

                        startActivity(intent)
                    }
                )

                // Space between cards

                val cardParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                cardParams.setMargins(
                    0,
                    0,
                    0,
                    16
                )

                plantContainer.addView(
                    plantCard,
                    cardParams
                )
            }
        }
    }
}

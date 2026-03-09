package com.angelodev.helpapp.ui.project

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angelodev.helpapp.R
import com.angelodev.helpapp.data.local.entity.CircuitEntity
import com.angelodev.helpapp.data.model.ProjectConfig
import com.angelodev.helpapp.databinding.ActivityInputingBinding
import com.angelodev.helpapp.ui.home.MenuActivity
import com.angelodev.helpapp.ui.schedule.LoadScheduleActivity
import com.angelodev.helpapp.util.CircuitDefaults
import com.angelodev.helpapp.util.Constants
import com.angelodev.helpapp.util.ElectricalCalculator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.math.RoundingMode
import java.text.DecimalFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CircuitInputActivity : AppCompatActivity() {

    private val viewModel: CircuitInputViewModel by viewModels()
    private lateinit var binding: ActivityInputingBinding

    // Views - initialized from binding in bindViews()
    private lateinit var circuitNumText: TextView
    private lateinit var itemsDropdown: AutoCompleteTextView
    private lateinit var horsepowerDropdown: AutoCompleteTextView
    private lateinit var pipeDropdown: AutoCompleteTextView
    private lateinit var wattsLODropdown: AutoCompleteTextView
    private lateinit var horsesLayout: TextInputLayout
    private lateinit var watLayout: TextInputLayout
    private lateinit var watloLayout: TextInputLayout
    private lateinit var nextButton: Button
    private lateinit var previewButton: Button
    private lateinit var preview2Button: Button
    private lateinit var updateButton: Button
    private lateinit var quantityInput: TextInputEditText
    private lateinit var wattsInput: TextInputEditText
    private lateinit var othersInput: TextInputEditText
    private lateinit var totalVAText: TextView
    private lateinit var totalAText: TextView

    // Hidden data TextViews
    private lateinit var tvOPlus: TextView
    private lateinit var tvV: TextView
    private lateinit var tvVA: TextView
    private lateinit var tvA: TextView
    private lateinit var tvP: TextView
    private lateinit var tvAT: TextView
    private lateinit var tvAF: TextView
    private lateinit var tvSNUM: TextView
    private lateinit var tvSMM: TextView
    private lateinit var tvSTYPE: TextView
    private lateinit var tvGNUM: TextView
    private lateinit var tvGMM: TextView
    private lateinit var tvGTYPE: TextView
    private lateinit var tvMMPlus: TextView
    private lateinit var tvCTYPE: TextView
    private lateinit var tvDemand: TextView
    private lateinit var tvMainPipe: TextView
    private lateinit var tvLoadName: TextView
    private lateinit var tvHighestAmp: TextView

    private var projectConfig: ProjectConfig? = null
    private var editCircuit: CircuitEntity? = null
    private var isEditMode = false

    private val decimalFormat = DecimalFormat("#.##").apply {
        roundingMode = RoundingMode.HALF_UP
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inputing)
        binding.lifecycleOwner = this

        bindViews()
        setupAdapters()

        // Get project config or edit mode data
        @Suppress("DEPRECATION")
        projectConfig = intent.getParcelableExtra(Constants.EXTRA_PROJECT_CONFIG)
        isEditMode = intent.getBooleanExtra(Constants.EXTRA_EDIT_MODE, false)

        if (isEditMode) {
            @Suppress("DEPRECATION")
            editCircuit = intent.getParcelableExtra(Constants.EXTRA_CIRCUIT)
            setupEditMode()
        } else {
            setupCreateMode()
        }

        setupItemSelection()
        setupHorsepowerSelection()
        setupPipeSelection()
        setupWattsLOSelection()
        setupButtons()
        observeViewModel()

        viewModel.loadCircuitCount()
    }

    private fun bindViews() {
        circuitNumText = binding.CircuitNum
        itemsDropdown = binding.Items
        horsepowerDropdown = binding.horse
        pipeDropdown = binding.Typeofpipe
        wattsLODropdown = binding.Wattslo
        horsesLayout = binding.horses
        watLayout = binding.Wat
        watloLayout = binding.Watlo
        nextButton = binding.next
        previewButton = binding.preview
        preview2Button = binding.preview2
        updateButton = binding.update
        quantityInput = binding.Quantity
        wattsInput = binding.Watts
        othersInput = binding.others
        totalVAText = binding.TotalVA
        totalAText = binding.TotalA

        tvOPlus = binding.OPlus
        tvV = binding.V
        tvVA = binding.VA
        tvA = binding.A
        tvP = binding.P
        tvAT = binding.AT
        tvAF = binding.AF
        tvSNUM = binding.SNUM
        tvSMM = binding.SMM
        tvSTYPE = binding.STYPE
        tvGNUM = binding.GNUM
        tvGMM = binding.GMM
        tvGTYPE = binding.GTYPE
        tvMMPlus = binding.MMPlus
        tvCTYPE = binding.CTYPE
        tvDemand = binding.demand
        tvMainPipe = binding.Mainpipe
        tvLoadName = binding.loadname
        tvHighestAmp = binding.HighestAmp
    }

    private fun setupAdapters() {
        itemsDropdown.setAdapter(ArrayAdapter(this, R.layout.drop_down_item, CircuitDefaults.ITEM_TYPES))
        horsepowerDropdown.setAdapter(ArrayAdapter(this, R.layout.drop_down_item, CircuitDefaults.HORSEPOWER_VALUES))
        pipeDropdown.setAdapter(ArrayAdapter(this, R.layout.drop_down_item, CircuitDefaults.PIPE_TYPES))
        wattsLODropdown.setAdapter(ArrayAdapter(this, R.layout.drop_down_item, CircuitDefaults.LIGHTING_WATTAGES))
    }

    private fun setupCreateMode() {
        nextButton.visibility = View.VISIBLE
        previewButton.visibility = View.VISIBLE
        horsesLayout.visibility = View.GONE
        updateButton.visibility = View.GONE
        preview2Button.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun setupEditMode() {
        val circuit = editCircuit ?: return

        circuitNumText.visibility = View.GONE
        previewButton.visibility = View.GONE
        nextButton.visibility = View.GONE
        preview2Button.visibility = View.VISIBLE
        updateButton.visibility = View.VISIBLE

        quantityInput.setText(circuit.quantity)

        // Populate item type
        val item = circuit.item
        when {
            item.startsWith("Lighting Outlet") -> {
                itemsDropdown.setText("Lighting Outlet")
                watLayout.visibility = View.GONE
                watloLayout.visibility = View.VISIBLE
                // Extract wattage from item name
                val startIdx = item.indexOf(',')
                val endIdx = item.indexOf('W')
                if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
                    wattsLODropdown.setText(item.substring(startIdx + 1, endIdx).trim())
                }
            }
            item.startsWith("ACU") -> {
                itemsDropdown.setText("ACU")
                horsesLayout.visibility = View.VISIBLE
                val startIdx = item.indexOf('U')
                val endIdx = item.indexOf("HP")
                if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
                    horsepowerDropdown.setText(item.substring(startIdx + 1, endIdx).trim())
                }
            }
            item.startsWith("Convenience Outlet") -> itemsDropdown.setText("Convenience Outlet")
            item.startsWith("Water Heater") -> itemsDropdown.setText("Water Heater")
            item.startsWith("Range") -> itemsDropdown.setText("Range")
            item.startsWith("Refrigerator") -> itemsDropdown.setText("Refrigerator")
            item.startsWith("Spare") -> itemsDropdown.setText("Spare")
        }

        // Populate pipe type
        when {
            circuit.conduitType.startsWith("EMT") -> pipeDropdown.setText("EMT")
            circuit.conduitType.startsWith("PVC") -> pipeDropdown.setText("PVC")
            circuit.conduitType.startsWith("IMC") -> pipeDropdown.setText("IMC")
        }

        // Populate watts
        val qty = circuit.quantity.toIntOrNull() ?: 1
        val va = circuit.voltAmpere.toIntOrNull() ?: 0
        wattsInput.setText(if (qty == 1) circuit.voltAmpere else (va / qty).toString())

        // Populate description
        val startParen = item.indexOf('(')
        val endParen = item.indexOf(')')
        if (startParen != -1 && endParen != -1) {
            othersInput.setText(item.substring(startParen + 1, endParen).trim())
        }
    }

    private fun setupItemSelection() {
        itemsDropdown.setOnItemClickListener { _, _, _, _ ->
            val selectedItem = itemsDropdown.text.toString()
            val config = CircuitDefaults.getConfig(selectedItem)

            tvAT.text = config.defaultAT
            tvMMPlus.text = config.defaultMmPlus
            tvSNUM.text = config.sizeNum
            tvGNUM.text = config.groundNum
            tvSTYPE.text = config.sizeType
            tvGTYPE.text = config.groundType

            if (config.defaultSizeMm.isNotEmpty()) tvSMM.text = config.defaultSizeMm
            if (config.defaultGroundMm.isNotEmpty()) tvGMM.text = config.defaultGroundMm

            // Visibility toggles
            horsesLayout.visibility = if (config.showHorsepower) View.VISIBLE else View.GONE
            if (config.showWattsDropdown) {
                watLayout.visibility = View.GONE
                watloLayout.visibility = View.VISIBLE
            } else {
                watLayout.visibility = View.VISIBLE
                watloLayout.visibility = View.GONE
            }

            // Default watts
            if (config.defaultWatts.isNotEmpty()) {
                wattsInput.setText(config.defaultWatts)
            }

            // Quantity handling
            if (!config.quantityEditable) {
                quantityInput.setText(config.defaultQuantity)
                quantityInput.isEnabled = false
            } else {
                quantityInput.isEnabled = true
                quantityInput.setText("")
            }

            // Convenience Outlet quantity limit
            if (selectedItem == "Convenience Outlet") {
                setupQuantityLimit(20)
            }
        }
    }

    private fun setupHorsepowerSelection() {
        horsepowerDropdown.setOnItemClickListener { _, _, _, _ ->
            val hp = horsepowerDropdown.text.toString()
            val spec = CircuitDefaults.HORSEPOWER_SPECS[hp] ?: return@setOnItemClickListener

            wattsInput.setText(spec.watts)
            tvSMM.text = spec.sizeMm
            tvGMM.text = spec.groundMm
            tvA.text = spec.ampere
            tvVA.text = spec.va
            tvAT.text = spec.at
            tvAF.text = spec.af
            tvMMPlus.text = spec.mmPlus
        }
    }

    private fun setupPipeSelection() {
        pipeDropdown.setOnItemClickListener { _, _, _, _ ->
            tvCTYPE.text = pipeDropdown.text.toString()
        }
    }

    private fun setupWattsLOSelection() {
        wattsLODropdown.setOnItemClickListener { _, _, _, _ ->
            val selected = wattsLODropdown.text.toString()
            wattsInput.setText(selected)

            val maxQty = CircuitDefaults.LIGHTING_MAX_QUANTITIES[selected]
            if (maxQty != null) {
                setupQuantityLimit(maxQty)
            }
        }
    }

    private fun setupQuantityLimit(max: Int) {
        quantityInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().toIntOrNull() ?: return
                if (input < 1 || input > max) {
                    quantityInput.error = "Quantity must be between 1 and $max"
                    nextButton.isEnabled = false
                    updateButton.isEnabled = false
                } else {
                    quantityInput.error = null
                    nextButton.isEnabled = true
                    updateButton.isEnabled = true
                }
            }
        })
    }

    private fun setupButtons() {
        nextButton.setOnClickListener { onNextClicked() }
        previewButton.setOnClickListener { onPreviewClicked() }
        preview2Button.setOnClickListener { onUpdatePreviewClicked() }
        updateButton.setOnClickListener { onUpdateClicked() }

        binding.back.setOnClickListener {
            showConfirmationDialog()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onNextClicked() {
        hideKeyboard()

        if (!validateInputs()) return

        val projectName = projectConfig?.projectName ?: intent.getStringExtra("ProjectName") ?: ""
        val selectedItem = buildItemName()
        computeValues()

        // Check circuit limit
        val count = viewModel.circuitCount.value ?: 0
        if (count >= Constants.MAX_CIRCUITS) {
            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("You have reached the maximum limit of ${Constants.MAX_CIRCUITS} circuits.")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    previewButton.performClick()
                }
                .show()
            return
        }

        tvOPlus.text = "1"
        tvV.text = "230"
        tvP.text = "2"
        tvAF.text = "50"

        viewModel.computeAndSaveCircuit(
            projectName = projectName,
            quantity = quantityInput.text.toString(),
            item = selectedItem,
            watts = wattsInput.text.toString(),
            ampereTrip = tvAT.text.toString(),
            sizeNum = tvSNUM.text.toString(),
            sizeMm = tvSMM.text.toString(),
            sizeType = tvSTYPE.text.toString(),
            groundNum = tvGNUM.text.toString(),
            groundMm = tvGMM.text.toString(),
            groundType = tvGTYPE.text.toString(),
            mmPlus = tvMMPlus.text.toString(),
            conduitType = tvCTYPE.text.toString(),
            precomputedVA = if (tvVA.text.isNotEmpty()) tvVA.text.toString() else null,
            precomputedA = if (tvA.text.isNotEmpty()) tvA.text.toString() else null
        )

        // Clear fields for next input
        clearInputFields()
    }

    private fun onPreviewClicked() {
        val count = viewModel.circuitCount.value ?: 0

        if (count < Constants.MIN_PREVIEW_CIRCUITS) {
            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("You need to add at least 2 items before previewing")
                .setPositiveButton("OK", null)
                .show()
            return
        }

        // Check if even number (needs spare)
        if (count % 2 == 0) {
            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("You are trying to preview with an even circuit count. Do you want to continue and add 1 spare?")
                .setPositiveButton("Yes") { _, _ ->
                    showDemandFactorDialog(addSpare = true)
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            if (count < Constants.MAX_CIRCUITS) {
                AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("Do you want to proceed?")
                    .setPositiveButton("Yes") { _, _ ->
                        showDemandFactorDialog(addSpare = false)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            } else {
                showDemandFactorDialog(addSpare = false)
            }
        }
    }

    private fun onUpdatePreviewClicked() {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage("Do you want to update this Item?")
            .setPositiveButton("Yes") { _, _ ->
                onUpdateClicked()
                navigateToScheduleForEdit()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun onUpdateClicked() {
        if (!validateInputs()) return

        val circuit = editCircuit ?: return
        val projectName = circuit.projectName
        val selectedItem = buildItemName()
        reapplyDefaultsForUpdate()
        computeValues()

        viewModel.updateCircuit(
            existingCircuit = circuit,
            projectName = projectName,
            quantity = quantityInput.text.toString(),
            item = selectedItem,
            watts = wattsInput.text.toString(),
            ampereTrip = tvAT.text.toString(),
            sizeNum = tvSNUM.text.toString(),
            sizeMm = tvSMM.text.toString(),
            sizeType = tvSTYPE.text.toString(),
            groundNum = tvGNUM.text.toString(),
            groundMm = tvGMM.text.toString(),
            groundType = tvGTYPE.text.toString(),
            mmPlus = tvMMPlus.text.toString(),
            conduitType = tvCTYPE.text.toString(),
            precomputedVA = if (tvVA.text.isNotEmpty()) tvVA.text.toString() else null,
            precomputedA = if (tvA.text.isNotEmpty()) tvA.text.toString() else null
        )

        Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
    }

    private fun reapplyDefaultsForUpdate() {
        val selectedItem = itemsDropdown.text.toString()
        val config = CircuitDefaults.getConfig(selectedItem)

        tvAT.text = config.defaultAT
        tvSNUM.text = config.sizeNum
        tvGNUM.text = config.groundNum
        tvSTYPE.text = config.sizeType
        tvGTYPE.text = config.groundType

        if (selectedItem != "ACU") {
            tvSMM.text = config.defaultSizeMm
            tvGMM.text = config.defaultGroundMm
        }
        tvMMPlus.text = config.defaultMmPlus
        tvOPlus.text = "1"
        tvV.text = "230"
        tvP.text = "2"
        tvAF.text = "50"

        // Apply HP specs if ACU
        val hp = horsepowerDropdown.text.toString()
        if (hp.isNotEmpty()) {
            CircuitDefaults.HORSEPOWER_SPECS[hp]?.let { spec ->
                wattsInput.setText(spec.watts)
                tvSMM.text = spec.sizeMm
                tvGMM.text = spec.groundMm
                tvA.text = spec.ampere
                tvVA.text = spec.va
                tvAT.text = spec.at
                tvAF.text = spec.af
                tvMMPlus.text = spec.mmPlus
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun buildItemName(): String {
        var selectedItem = itemsDropdown.text.toString()
        val description = othersInput.text.toString()
        val watts = wattsInput.text.toString()
        val hp = horsepowerDropdown.text.toString()

        when (selectedItem) {
            "Lighting Outlet" -> {
                if (description.isNotEmpty()) {
                    selectedItem += ", ${watts}W\n ($description)"
                } else if (watts.isNotEmpty()) {
                    selectedItem += ", ${watts}W"
                }
            }
            "ACU" -> {
                if (description.isNotEmpty()) {
                    selectedItem += " ${hp}HP\n ($description)"
                } else if (hp.isNotEmpty()) {
                    selectedItem += " ${hp}HP"
                }
            }
            else -> {
                if (description.isNotEmpty()) {
                    selectedItem += "\n ($description)"
                }
            }
        }
        return selectedItem
    }

    private fun computeValues() {
        val quantity = quantityInput.text.toString().toIntOrNull() ?: 1
        val watts = wattsInput.text.toString().toDoubleOrNull() ?: 0.0

        // Only compute if not already set by HP selection
        if (tvVA.text.isEmpty() || itemsDropdown.text.toString() != "ACU") {
            val va = ElectricalCalculator.computeVA(quantity, watts)
            tvVA.text = decimalFormat.format(va)
        }

        if (tvA.text.isEmpty() || itemsDropdown.text.toString() != "ACU") {
            val vaVal = tvVA.text.toString().toDoubleOrNull() ?: 0.0
            val ampere = ElectricalCalculator.computeAmpere(vaVal)
            tvA.text = decimalFormat.format(ampere)
        }
    }

    private fun validateInputs(): Boolean {
        if (quantityInput.text.toString().isEmpty()) {
            showFieldAlert("Please fill Quantity")
            return false
        }
        if (horsesLayout.visibility == View.VISIBLE && horsepowerDropdown.text.toString().isEmpty()) {
            showFieldAlert("Please fill Horsepower")
            return false
        }
        if (itemsDropdown.text.toString().isEmpty()) {
            showFieldAlert("Please fill the Item")
            return false
        }
        if (wattsInput.text.toString().isEmpty()) {
            showFieldAlert("Please fill Watts")
            return false
        }
        if (pipeDropdown.text.toString().isEmpty()) {
            showFieldAlert("Please fill the type of pipe")
            return false
        }
        return true
    }

    private fun showFieldAlert(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun showDemandFactorDialog(addSpare: Boolean) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_input_percent, null)
        val loadNameInput = dialogView.findViewById<EditText>(R.id.editTextLoadname)
        val percentInput = dialogView.findViewById<EditText>(R.id.editTextPercent)
        val pipeAutoComplete = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompletepipe)

        pipeAutoComplete.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, CircuitDefaults.PIPE_TYPES))

        val dialog = AlertDialog.Builder(this)
            .setTitle("Please fill this out.")
            .setView(dialogView)
            .setPositiveButton("OK", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val percent = percentInput.text.toString().toIntOrNull()
                    val pipeSelected = pipeAutoComplete.text.toString().isNotEmpty()
                    val loadName = loadNameInput.text.toString().isNotEmpty()
                    positiveButton.isEnabled = percent != null && percent in 1..100 && pipeSelected && loadName
                }
            }
            percentInput.addTextChangedListener(textWatcher)
            pipeAutoComplete.addTextChangedListener(textWatcher)
            loadNameInput.addTextChangedListener(textWatcher)

            positiveButton.setOnClickListener {
                val percent = percentInput.text.toString().toIntOrNull() ?: return@setOnClickListener
                val demandFactor = percent / 100.0
                val loadName = loadNameInput.text.toString().trim()
                val mainPipe = pipeAutoComplete.text.toString().trim()

                val projectName = projectConfig?.projectName ?: ""

                if (addSpare) {
                    viewModel.addSpareAndProceed(projectName, demandFactor, loadName, mainPipe)
                } else {
                    viewModel.proceedToSchedule(demandFactor, loadName, mainPipe)
                }
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun navigateToScheduleForEdit() {
        val intent = Intent(this, LoadScheduleActivity::class.java)
        startActivity(intent)
    }

    private fun clearInputFields() {
        quantityInput.setText(null)
        itemsDropdown.setText(null)
        wattsInput.setText(null)
        horsepowerDropdown.setText(null)
        othersInput.setText(null)
        pipeDropdown.setText(null)
        wattsLODropdown.setText(null)
        watLayout.visibility = View.VISIBLE
        watloLayout.visibility = View.GONE
        horsesLayout.visibility = View.GONE
        tvVA.text = ""
        tvA.text = ""
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to proceed?")
            .setPositiveButton("Yes") { _, _ ->
                startActivity(Intent(this, MenuActivity::class.java))
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { imm.hideSoftInputFromWindow(it.windowToken, 0) }
    }

    private fun observeViewModel() {
        viewModel.circuitCount.observe(this) { count ->
            circuitNumText.text = "CIRCUIT NO. ${count + 1}"
        }

        viewModel.totalVA.observe(this) { va ->
            totalVAText.text = decimalFormat.format(va)
        }

        viewModel.totalA.observe(this) { a ->
            totalAText.text = decimalFormat.format(a)
        }

        viewModel.navigateToSchedule.observe(this) { result ->
            result?.let {
                val intent = Intent(this, LoadScheduleActivity::class.java).apply {
                    putExtra(Constants.EXTRA_TOTAL_VA, ElectricalCalculator.formatValue(it.totalVA))
                    putExtra(Constants.EXTRA_TOTAL_A, ElectricalCalculator.formatValue(it.totalAmpere))
                    putExtra(Constants.EXTRA_HIGHEST_A, ElectricalCalculator.formatValue(it.highestAmpere))
                    putExtra(Constants.EXTRA_DEMAND, it.demandFactor.toString())
                    putExtra(Constants.EXTRA_MAIN_PIPE, it.mainPipeType)
                    putExtra(Constants.EXTRA_LOAD_NAME, it.loadName)
                }
                startActivity(intent)
                viewModel.onNavigationDone()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Toast.makeText(this, "You can't back the application until the project is done", Toast.LENGTH_SHORT).show()
    }
}

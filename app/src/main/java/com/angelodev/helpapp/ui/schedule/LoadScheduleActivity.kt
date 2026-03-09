@file:Suppress("DEPRECATION")

package com.angelodev.helpapp.ui.schedule

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.angelodev.helpapp.R
import com.angelodev.helpapp.ui.home.MenuActivity
import com.angelodev.helpapp.ui.project.CircuitInputActivity
import com.angelodev.helpapp.util.Constants
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfWriter
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class LoadScheduleActivity : AppCompatActivity() {

    private val viewModel: LoadScheduleViewModel by viewModels()

    private val df = DecimalFormat("#.##")
    private val decimalFormat = DecimalFormat("#0.00")

    private var totalValue = 0.0
    private var topOneAndTwoValue = 0.0
    private var topThreeAndFourValue = 0.0
    private var underOneAndTwoValue = 0.0
    private var underThreeAndFourValue = 0.0
    private var disableMenuItem = false
    private var currentTableCount = 0
    private var PBMAINCTR = 0
    private var mScaleFactor = 1.0f
    private var mLastTouchX = 0f
    private var mLastTouchY = 0f

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CircuitListAdapter
    private lateinit var rootLayout: RelativeLayout
    private lateinit var mScaleGestureDetector: ScaleGestureDetector
    private lateinit var mGestureDetector: GestureDetector

    // -- Key TextViews --
    private lateinit var totalVATextView: TextView
    private lateinit var totalATextView: TextView
    private lateinit var HighestA: TextView
    private lateinit var HighestB: TextView
    private lateinit var totalone: TextView
    private lateinit var TotalB: TextView
    private lateinit var UnderOneAndTwo: TextView
    private lateinit var UnderThreeAndFour: TextView
    private lateinit var TotalUnder: TextView
    private lateinit var TopOneAndTwo: TextView
    private lateinit var TopThreeAndFour: TextView
    private lateinit var TotalTop: TextView
    private lateinit var MainWire: TextView
    private lateinit var FeederWire: TextView
    private lateinit var FeederWireSecond: TextView
    private lateinit var FeederWireThird: TextView
    private lateinit var FeederWireFourth: TextView
    private lateinit var FeederSize: TextView
    private lateinit var UpdatedMainWire: TextView
    private lateinit var demandfactor1: TextView
    private lateinit var demandfactor2: TextView
    private lateinit var Pipetype: TextView
    private lateinit var PB1: TextView
    private lateinit var CTRtv: TextView
    private lateinit var FEEDERWIREPASS: TextView
    private lateinit var MAINWIREPASS: TextView
    private lateinit var SaveA: TextView
    private lateinit var SAVEHIGHA: TextView
    private lateinit var LAWEHIGHA: TextView
    private lateinit var SAVEHIGHB: TextView
    private lateinit var LAWEHIGHB: TextView

    // Skeleton RelativeLayouts
    private lateinit var skeletonLayouts: Map<Int, RelativeLayout>
    private lateinit var RS30ref: RelativeLayout

    // numN_X TextViews (item names) - built dynamically
    private lateinit var itemTextViews: Map<Int, List<TextView>>

    // numN_aX TextViews (AT values) - built dynamically
    private lateinit var atTextViews: Map<Int, List<TextView>>

    // numN_a TextViews (AT display on skeleton)
    private lateinit var numAViews: List<TextView>

    // numN_ab TextViews (load name on skeleton)
    private lateinit var numAbViews: List<TextView>

    // numN_top TextViews (feeder specs on skeleton)
    private lateinit var numTopViews: List<TextView>

    // numN_bot TextViews (GEC on skeleton)
    private lateinit var numBotViews: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loadschedule)

        bindViews()
        setupToolbar()
        setupZoomPan()
        setupRecyclerView()

        // Load data
        viewModel.loadData()
        observeViewModel()
    }

    // =====================================================================
    //  VIEW BINDING
    // =====================================================================

    private fun bindViews() {
        recyclerView = findViewById(R.id.recylcer_view)
        totalVATextView = findViewById(R.id.totalVA)
        totalATextView = findViewById(R.id.totalA)
        HighestA = findViewById(R.id.HIGHA)
        HighestB = findViewById(R.id.HighestB)
        totalone = findViewById(R.id.totalONE)
        TotalB = findViewById(R.id.TotalB)
        UnderOneAndTwo = findViewById(R.id.underoneandtwo)
        UnderThreeAndFour = findViewById(R.id.underthreeandfour)
        TotalUnder = findViewById(R.id.totalunder)
        TopOneAndTwo = findViewById(R.id.TopOneAndTwo)
        TopThreeAndFour = findViewById(R.id.TopThreeAndFour)
        TotalTop = findViewById(R.id.TotalTop)
        MainWire = findViewById(R.id.MainWire)
        FeederWire = findViewById(R.id.FeederWire)
        FeederWireSecond = findViewById(R.id.FeederWireSecond)
        FeederWireThird = findViewById(R.id.FeederWireThird)
        FeederWireFourth = findViewById(R.id.FeederWireFourth)
        FeederSize = findViewById(R.id.FeederSize)
        UpdatedMainWire = findViewById(R.id.UpdatedMainWire)
        SaveA = findViewById(R.id.saveA)
        MAINWIREPASS = findViewById(R.id.MainWirePass)
        FEEDERWIREPASS = findViewById(R.id.FeederWireTypePass)
        CTRtv = findViewById(R.id.CTRtv)
        demandfactor1 = findViewById(R.id.demandfactor1)
        demandfactor2 = findViewById(R.id.demandfactor2)
        Pipetype = findViewById(R.id.Pipetype)
        PB1 = findViewById(R.id.PB1)
        SAVEHIGHA = findViewById(R.id.SAVEHIGHA)
        LAWEHIGHA = findViewById(R.id.LAWEHIGHA)
        SAVEHIGHB = findViewById(R.id.SAVEHIGHB)
        LAWEHIGHB = findViewById(R.id.LAWEHIGHB)
        rootLayout = findViewById(R.id.zoom)

        // Skeleton RelativeLayouts
        val RS2: RelativeLayout = findViewById(R.id.RS2)
        val RS4: RelativeLayout = findViewById(R.id.RS4)
        val RS6: RelativeLayout = findViewById(R.id.RS6)
        val RS8: RelativeLayout = findViewById(R.id.RS8)
        val RS10: RelativeLayout = findViewById(R.id.RS10)
        val RS12: RelativeLayout = findViewById(R.id.RS12)
        val RS14: RelativeLayout = findViewById(R.id.RS14)
        val RS16: RelativeLayout = findViewById(R.id.RS16)
        val RS18: RelativeLayout = findViewById(R.id.RS18)
        val RS20: RelativeLayout = findViewById(R.id.RS20)
        val RS22: RelativeLayout = findViewById(R.id.RS22)
        val RS24: RelativeLayout = findViewById(R.id.RS24)
        val RS26: RelativeLayout = findViewById(R.id.RS26)
        val RS28: RelativeLayout = findViewById(R.id.RS28)
        val RS30: RelativeLayout = findViewById(R.id.RS30)
        RS30ref = RS30

        skeletonLayouts = mapOf(
            2 to RS2, 4 to RS4, 6 to RS6, 8 to RS8, 10 to RS10,
            12 to RS12, 14 to RS14, 16 to RS16, 18 to RS18, 20 to RS20,
            22 to RS22, 24 to RS24, 26 to RS26, 28 to RS28, 30 to RS30
        )

        // Build item name TextViews dynamically using resource IDs
        itemTextViews = buildTextViewMap("num%d_%d")
        atTextViews = buildTextViewMap("num%d_a%d")

        // numN_a views (one per skeleton size)
        numAViews = buildSingleTextViewList("num%d_a")
        numAbViews = buildSingleTextViewList("num%d_ab")
        numTopViews = buildSingleTextViewList("num%d_top")
        numBotViews = buildSingleTextViewList("num%d_bot")
    }

    /**
     * Builds a map like { 2 -> [num2_1, num2_2], 4 -> [num4_1,...,num4_4], ... 30 -> [...] }
     */
    private fun buildTextViewMap(pattern: String): Map<Int, List<TextView>> {
        val map = mutableMapOf<Int, List<TextView>>()
        for (n in 2..30 step 2) {
            val views = mutableListOf<TextView>()
            for (i in 1..n) {
                val idName = pattern.format(n, i)
                val resId = resources.getIdentifier(idName, "id", packageName)
                if (resId != 0) {
                    views.add(findViewById(resId))
                }
            }
            map[n] = views
        }
        return map
    }

    /**
     * Builds list of single TextViews for each skeleton size: [num2_a, num4_a, ... num30_a]
     */
    private fun buildSingleTextViewList(pattern: String): List<TextView> {
        val list = mutableListOf<TextView>()
        for (n in 2..30 step 2) {
            val idName = pattern.format(n)
            val resId = resources.getIdentifier(idName, "id", packageName)
            if (resId != 0) {
                list.add(findViewById(resId))
            }
        }
        return list
    }

    // =====================================================================
    //  TOOLBAR
    // =====================================================================

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.add) {
            if (disableMenuItem) {
                Toast.makeText(
                    applicationContext,
                    "You cannot add more circuits because you have reached the maximum limit of 30.",
                    Toast.LENGTH_SHORT
                ).show()
                item.isEnabled = false
            } else {
                startActivity(Intent(this, CircuitInputActivity::class.java))
            }
            return true
        }

        // ---- SAVE / PRINT ----
        if (id == R.id.save) {
            showPaperSizeDialog()
        }

        // ---- NEXT LOAD SCHEDULE ----
        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        currentTableCount = prefs.getInt("currentTableCount", 0)

        if (id == R.id.nextLS) {
            if (currentTableCount < 3) {
                currentTableCount++
                PBMAINCTR++
                prefs.edit().putInt("currentTableCount", currentTableCount).apply()
                captureRelativeLayoutAsImage()
            }
            if (currentTableCount != 3) {
                prefs.edit().putInt("currentTableCount", currentTableCount).apply()
                val intent = Intent(this, CircuitInputActivity::class.java)
                intent.putExtra("currentTableCount", currentTableCount)
                startActivity(intent)
                viewModel.clearDatabase()
                Toast.makeText(this, "Table Saved. Num of tables: $currentTableCount", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Maximum number of tables reached", Toast.LENGTH_SHORT).show()
            }
        }

        // ---- RESET ----
        if (id == R.id.resetLS) {
            Toast.makeText(this, "reset current load schedule button", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CircuitInputActivity::class.java))
            viewModel.clearDatabase()
        }

        // ---- DISCARD ----
        if (id == R.id.discard) {
            AlertDialog.Builder(this)
                .setTitle("Discard the project?")
                .setMessage("Are you sure you want to discard the project and return to home?")
                .setPositiveButton("Yes") { _, _ ->
                    getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().clear().apply()
                    currentTableCount = 0
                    PBMAINCTR = 0
                    Toast.makeText(this, "Project discarded", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    viewModel.clearDatabase()
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                .show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // =====================================================================
    //  RECYCLERVIEW
    // =====================================================================

    private fun setupRecyclerView() {
        adapter = CircuitListAdapter { circuit ->
            val intent = Intent(this, CircuitInputActivity::class.java).apply {
                putExtra(Constants.EXTRA_CIRCUIT, circuit)
                putExtra(Constants.EXTRA_EDIT_MODE, true)
            }
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun setRecyclerView(projectTableList: List<Any>) {
        // Called from ViewModel if needed for legacy DataAdapter support
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // =====================================================================
    //  ZOOM / PAN
    // =====================================================================

    private fun setupZoomPan() {
        mScaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                mScaleFactor *= detector.scaleFactor
                mScaleFactor = mScaleFactor.coerceIn(0.1f, 5.0f)
                rootLayout.scaleX = mScaleFactor
                rootLayout.scaleY = mScaleFactor
                return true
            }
        })

        mGestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                mScaleFactor = 1.0f
                rootLayout.scaleX = mScaleFactor
                rootLayout.scaleY = mScaleFactor
                recyclerView.scaleX = mScaleFactor
                recyclerView.scaleY = mScaleFactor
                return true
            }

            override fun onDown(e: MotionEvent): Boolean {
                mLastTouchX = e.rawX
                mLastTouchY = e.rawY
                return true
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                rootLayout.x = rootLayout.x + (e2.rawX - mLastTouchX)
                rootLayout.y = rootLayout.y + (e2.rawY - mLastTouchY)
                mLastTouchX = e2.rawX
                mLastTouchY = e2.rawY
                return true
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mScaleGestureDetector.onTouchEvent(event)
        mGestureDetector.onTouchEvent(event)
        return true
    }

    // =====================================================================
    //  OBSERVE VIEWMODEL
    // =====================================================================

    private fun observeViewModel() {
        viewModel.circuits.observe(this) { circuits ->
            adapter.submitList(circuits)
        }

        // Total A
        viewModel.allAs.observe(this) { asList ->
            totalValue = 0.0
            for (item in asList) {
                try {
                    totalValue += item.toDouble()
                } catch (_: NumberFormatException) {
                }
            }
            val formattedTotalA = String.format("%.2f", totalValue)
            totalATextView.text = formattedTotalA
            totalone.text = formattedTotalA
            TotalB.text = formattedTotalA
            onTotalValueCalculated(totalValue)
        }

        // Total VA
        viewModel.allVAs.observe(this) { vasList ->
            var total = 0.0
            for (item in vasList) {
                try {
                    total += item.toDouble()
                } catch (_: NumberFormatException) {
                }
            }
            totalVATextView.text = String.format("%.0f", total)
        }

        // Display item names on skeleton
        viewModel.allItems.observe(this) { itemsList ->
            val size = itemsList.size
            val views = itemTextViews[size] ?: return@observe
            for (i in views.indices) {
                if (i < itemsList.size) {
                    // Preserve original bug: index 12 uses get(11) for sizes >= 14
                    val srcIndex = if (i == 12 && size >= 14) 11 else i
                    views[i].text = itemsList[srcIndex]
                }
            }
        }

        // Display AT values on skeleton
        viewModel.allATs.observe(this) { atsList ->
            val size = atsList.size
            val views = atTextViews[size] ?: return@observe
            for (i in views.indices) {
                if (i < atsList.size) {
                    val srcIndex = if (i == 12 && size >= 14) 11 else i
                    views[i].text = atsList[srcIndex]
                }
            }

            // Show/hide skeleton layouts based on circuit count
            skeletonLayouts.forEach { (count, layout) ->
                layout.visibility = if (count == size) View.VISIBLE else View.GONE
            }
            if (size == 30) {
                disableMenuItem = true
            }
        }

        // Highest ACU/Refrigerator A value
        viewModel.highestACUA.observe(this) { highestValue ->
            updateUIHighest(highestValue)
            sumOfLeftAndRightTop()
        }
    }

    // =====================================================================
    //  HIGHEST A VALUE UI UPDATE
    // =====================================================================

    private fun updateUIHighest(highestValue: Double) {
        if (highestValue > 0) {
            HighestA.text = String.format("%.2f", highestValue)
            HighestB.text = String.format("%.2f", highestValue)

            topThreeAndFourValue = Math.round(highestValue * 0.25 * 100.0) / 100.0
            underThreeAndFourValue = Math.round(highestValue * 1.5 * 100.0) / 100.0

            TopThreeAndFour.text = decimalFormat.format(topThreeAndFourValue)
            UnderThreeAndFour.text = decimalFormat.format(underThreeAndFourValue)
        } else {
            HighestA.text = "0.00"
            HighestB.text = "0.00"
        }
    }

    // =====================================================================
    //  ON TOTAL VALUE CALCULATED (core logic from original)
    // =====================================================================

    private fun onTotalValueCalculated(totalValue: Double) {
        val preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        var demand1 = intent.getStringExtra(Constants.EXTRA_DEMAND)
        var mainpipe = intent.getStringExtra(Constants.EXTRA_MAIN_PIPE)
        var loadnamesave = intent.getStringExtra(Constants.EXTRA_LOAD_NAME)

        // Handle load name
        if (!loadnamesave.isNullOrEmpty()) {
            PB1.text = loadnamesave
            numAbViews.forEach { it.text = loadnamesave }
            preferences.edit().putString("loadnamesave", loadnamesave).apply()
        } else {
            loadnamesave = preferences.getString("loadnamesave", "") ?: ""
            if (loadnamesave.isNotEmpty()) {
                PB1.text = loadnamesave
                numAbViews.forEach { it.text = loadnamesave }
            }
        }

        // Handle mainpipe
        if (!mainpipe.isNullOrEmpty()) {
            Pipetype.text = mainpipe
            preferences.edit().putString("mainpipe", mainpipe).apply()
        } else {
            mainpipe = preferences.getString("mainpipe", "") ?: ""
            if (mainpipe.isNotEmpty()) {
                Pipetype.text = mainpipe
            }
        }

        // Handle demand factor
        if (!demand1.isNullOrEmpty()) {
            demandfactor1.text = demand1
            demandfactor2.text = demand1
            sharedPreferences.edit().putString("DEMAND", demand1).apply()
        } else {
            demand1 = sharedPreferences.getString("DEMAND", "") ?: ""
            if (demand1.isNotEmpty()) {
                demandfactor1.text = demand1
                demandfactor2.text = demand1
            }
        }

        // Calculate top values
        val demand5 = demandfactor1.text.toString()
        val demandrer = try { demand5.toDouble() } catch (_: Exception) { 1.0 }
        topOneAndTwoValue = totalValue * demandrer

        val formattedResult = decimalFormat.format(topOneAndTwoValue)
        TopOneAndTwo.text = formattedResult
        UnderOneAndTwo.text = formattedResult
        sumOfLeftAndRightTop()

        // Setup click listeners for wire editing
        setupFeederWireClick()
        setupFeederWireSecondClick()
        setupFeederWireFourthClick()
        setupMainWireClick()
    }

    // =====================================================================
    //  FEEDER WIRE CLICK (first wire)
    // =====================================================================

    private fun setupFeederWireClick() {
        FeederWire.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_feeder_wire, null)
            val autoComplete = dialogView.findViewById<AutoCompleteTextView>(R.id.feeder)

            val feedOptions = arrayOf(
                "3.5", "5.5", "8.0", "14", "22", "30", "38", "50", "60", "80",
                "100", "125", "150", "175", "200", "250", "325", "375", "400", "500"
            )
            autoComplete.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, feedOptions))

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Update Feeder Wire")
                .setPositiveButton("OK") { _, _ ->
                    handleFeederWireSelection(autoComplete.text.toString())
                }
                .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
                .create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false
            autoComplete.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
                positiveButton.isEnabled = true
            }
        }
    }

    // =====================================================================
    //  FEEDER WIRE SECOND CLICK (pipe wire)
    // =====================================================================

    private fun setupFeederWireSecondClick() {
        FeederWireSecond.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_pipe_wire, null)
            val autoComplete = dialogView.findViewById<AutoCompleteTextView>(R.id.auto_complete_pipe_wire)

            val pipeWireOptions = arrayOf("3.5", "5.5", "8.0", "14", "22", "30", "50", "60")
            autoComplete.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, pipeWireOptions))

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Update Feeder Wire")
                .setPositiveButton("OK") { _, _ ->
                    handleFeederWireSecondSelection(autoComplete.text.toString())
                }
                .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
                .create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false
            autoComplete.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    positiveButton.isEnabled = s.toString().trim().isNotEmpty()
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    // =====================================================================
    //  FEEDER WIRE FOURTH CLICK (ground electrode / pipe diameter)
    // =====================================================================

    private fun setupFeederWireFourthClick() {
        FeederWireFourth.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_feeder_wire4, null)
            val autoComplete = dialogView.findViewById<AutoCompleteTextView>(R.id.auto_complete_feeder_wire4)

            val feederWireOptions = arrayOf("15", "20", "25", "32", "40", "50", "65", "80", "90", "100")
            autoComplete.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, feederWireOptions))

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Update Feeder Wire")
                .setPositiveButton("OK") { _, _ ->
                    handleFeederWireThirdSelection(autoComplete.text.toString())
                }
                .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
                .create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false
            autoComplete.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    positiveButton.isEnabled = s.toString().trim().isNotEmpty()
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    // =====================================================================
    //  MAIN WIRE CLICK (AT / AF selection)
    // =====================================================================

    private fun setupMainWireClick() {
        MainWire.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_main_wire, null)
            val ATWire = dialogView.findViewById<AutoCompleteTextView>(R.id.auto_complete_AT_wire)
            val AFWire = dialogView.findViewById<AutoCompleteTextView>(R.id.auto_complete_AF_wire)

            val ATOptions = arrayOf(
                "20", "30", "40", "50", "60", "70", "80", "90", "100", "125", "150", "175",
                "200", "225", "250", "300", "350", "400", "500", "600", "700", "800",
                "1000", "1200", "1600", "2000", "2500", "3000", "4000", "5000", "6000"
            )
            val AFOptions = arrayOf(
                "50", "100", "125", "150", "225", "250", "400", "600", "800",
                "1200", "1600", "2000", "2500", "3000", "4000", "5000", "6000"
            )

            ATWire.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, ATOptions))
            AFWire.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, AFOptions))

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Update AT and AF")
                .setPositiveButton("OK") { _, _ ->
                    val at = ATWire.text.toString()
                    val af = AFWire.text.toString()
                    handleAtAf(at, at, af)
                }
                .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
                .create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false

            val watcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    positiveButton.isEnabled = ATWire.text.toString().trim().isNotEmpty()
                            && AFWire.text.toString().trim().isNotEmpty()
                }
                override fun afterTextChanged(s: Editable?) {}
            }
            ATWire.addTextChangedListener(watcher)
            AFWire.addTextChangedListener(watcher)

            // Auto-map AF from AT
            ATWire.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
                val selectedAT = ATWire.text.toString()
                val autoAF = getAFForAT(selectedAT)
                AFWire.setText(autoAF)
                AFWire.isEnabled = false
            }
        }
    }

    /**
     * Maps AT value to AF value, matching original logic exactly.
     */
    private fun getAFForAT(at: String): String {
        return when (at) {
            "20", "30", "40", "50", "60", "70", "80", "90" -> "100"
            "100" -> "125"
            "125" -> "150"
            "150", "175", "200" -> "225"
            "225", "250" -> "250"
            "300", "350", "400" -> "400"
            "500", "600" -> "600"
            "700", "800" -> "800"
            "1000", "1200" -> "1200"
            "1600" -> "1600"
            "2000" -> "2000"
            "2500" -> "2500"
            "3000" -> "3000"
            "4000" -> "4000"
            "5000" -> "5000"
            else -> "6000"
        }
    }

    // =====================================================================
    //  HANDLE FEEDER WIRE SELECTION
    // =====================================================================

    private fun handleFeederWireSelection(selectedFeederWire: String) {
        val feed = FeederWire.text.toString()
        if (!feed.startsWith("2 -")) return

        try {
            val feedWireSizeStr = feed.substring(4, feed.indexOf("mm\u00B2"))
            val feedWireSize = feedWireSizeStr.toDouble()
            val selectedWireSize = selectedFeederWire.toDouble()

            val doUpdate = {
                FeederWire.text = "2 - ${selectedFeederWire}mm\u00B2 THHN/THWN-2 Cu. Wire "
                gec(FeederWire.text.toString())
                updateTopViews()
            }

            if (selectedWireSize < feedWireSize) {
                showPECWarningDialog { doUpdate() }
            } else {
                doUpdate()
            }
        } catch (_: Exception) {
        }
    }

    // =====================================================================
    //  HANDLE FEEDER WIRE SECOND SELECTION (pipe wire)
    // =====================================================================

    private fun handleFeederWireSecondSelection(selectedFeederWiresec: String) {
        val feed2 = FeederWireSecond.text.toString()
        if (!feed2.startsWith(" + 1 - ")) return

        try {
            val startIndex = " + 1 - ".length
            val endIndex = feed2.indexOf("mm\u00B2")
            val feedWireSizeStr = feed2.substring(startIndex, endIndex)
            val feedWireSize2 = feedWireSizeStr.toDouble()
            val selectedWireSize2 = selectedFeederWiresec.toDouble()

            if (selectedWireSize2 < feedWireSize2) {
                showPECWarningDialog { updateFeederWireSecond(selectedFeederWiresec) }
            } else {
                updateFeederWireSecond(selectedFeederWiresec)
            }
        } catch (_: Exception) {
        }
    }

    private fun updateFeederWireSecond(selectedFeederWiresec: String) {
        val newText = " + 1 - ${selectedFeederWiresec}mm\u00B2 THHN/THWN-2 Cu. Wire"
        FeederWireSecond.text = newText
        updateTopViews()
    }

    // =====================================================================
    //  HANDLE FEEDER WIRE THIRD SELECTION (ground electrode)
    // =====================================================================

    private fun handleFeederWireThirdSelection(selectedFeederWire: String) {
        val feed2 = FeederWireFourth.text.toString()
        if (!feed2.startsWith("(G)In ")) return

        try {
            val startIndex = "(G)In ".length
            val endIndex = feed2.indexOf(" mm\u00F8")
            val feedWireSizeStr = feed2.substring(startIndex, endIndex)
            val feedWireSize2 = feedWireSizeStr.toDouble()
            val selectedWireSize2 = selectedFeederWire.toDouble()

            val doUpdate = {
                FeederWireFourth.text = "(G)In $selectedFeederWire mm\u00F8"
                updateTopViews()
            }

            if (selectedWireSize2 < feedWireSize2) {
                showPECWarningDialog { doUpdate() }
            } else {
                doUpdate()
            }
        } catch (_: Exception) {
        }
    }

    // =====================================================================
    //  HANDLE AT / AF
    // =====================================================================

    private fun handleAtAf(selectedAT: String, AT: String, AF: String) {
        val feed2 = MainWire.text.toString()

        try {
            val endIndex = feed2.indexOf(" AT,")
            if (endIndex < 0) return
            val feedWireSizeStr = feed2.substring(0, endIndex)
            val feedWireSize2 = feedWireSizeStr.toDouble()
            val selectedWireSize2 = selectedAT.toDouble()

            val doUpdate = {
                val newAfAt = "$AT AT, $AF AF, 2P, 230V, 60 HZ"
                MainWire.text = newAfAt
                numAViews.forEach { it.text = "$AT AT" }
            }

            if (selectedWireSize2 < feedWireSize2) {
                showPECWarningDialog { doUpdate() }
            } else {
                doUpdate()
            }
        } catch (_: Exception) {
        }
    }

    // =====================================================================
    //  PEC WARNING DIALOG (reusable)
    // =====================================================================

    private fun showPECWarningDialog(onProceed: () -> Unit) {
        AlertDialog.Builder(this)
            .setMessage(
                "Please be informed that reducing the wire size significantly may violate " +
                        "the Philippine Electrical Code (PEC) standards.\n\n" +
                        "Click Proceed to confirm if you wish to continue editing."
            )
            .setPositiveButton("Proceed") { _, _ -> onProceed() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // =====================================================================
    //  UPDATE TOP VIEWS (skeleton "USE ..." text)
    // =====================================================================

    private fun updateTopViews() {
        val topText = "USE ${FeederWire.text}\n${FeederWireSecond.text}\n${FeederWireFourth.text} ${Pipetype.text}"
        numTopViews.forEach { it.text = topText }
    }

    // =====================================================================
    //  SUM OF LEFT AND RIGHT TOP (core calculation)
    // =====================================================================

    private fun sumOfLeftAndRightTop() {
        val value1 = topOneAndTwoValue
        val value2 = topThreeAndFourValue
        val value3 = underThreeAndFourValue

        val sum = value1 + value2
        TotalTop.text = decimalFormat.format(sum)

        val sum2 = value1 + value3
        TotalUnder.text = decimalFormat.format(sum2)

        // Determine MainWire AT/AF based on sum2
        val mainWireText = getMainWireForSum(sum2)
        if (mainWireText != null) {
            MainWire.text = mainWireText
        }

        val passMainWire = MainWire.text.toString()

        // Set FeederWire and FeederWireSecond based on mainwire
        setFeederWiresFromMainWire(passMainWire)

        val sharedPreferences = getSharedPreferences("SharePref", MODE_PRIVATE)
        val FDW = sharedPreferences.getString("UFWT", "")
        if (FDW != null) {
            MainWire.text = passMainWire
        }

        val feederW2 = FeederWire.text.toString().trim()

        // Set FeederWireFourth (pipe diameter) and bot text (GEC) based on feeder wire size
        setGecAndPipeDiameter(feederW2, trimTrailingSpace = false)

        // Update top views on skeleton
        val feeder2 = FeederWireSecond.text.toString().trim()
        val feeder3 = FeederWireFourth.text.toString().trim()
        val topText = "USE $feederW2\n$feeder2\n$feeder3 ${Pipetype.text}"
        numTopViews.forEach { it.text = topText }

        // Extract AT portion from MainWire for skeleton display
        val fullText = MainWire.text.toString()
        val commaIndex = fullText.indexOf(',')
        val desiredSubstring = if (commaIndex != -1) fullText.substring(0, commaIndex) else fullText
        numAViews.forEach { it.text = desiredSubstring }
    }

    /**
     * Returns the MainWire string for a given sum2 value, matching original logic.
     */
    private fun getMainWireForSum(sum2: Double): String? {
        return when {
            sum2 in 1.0..20.0 -> "20 AT, 100 AF, 2P, 230V, 60 HZ"
            sum2 <= 30 -> "30 AT, 100 AF, 2P, 230V, 60 HZ"
            sum2 <= 40 -> "40 AT, 100 AF, 2P, 230V, 60 HZ"
            sum2 <= 50 -> "50 AT, 100 AF, 2P, 230V, 60 HZ"
            sum2 <= 60 -> "60 AT, 100 AF, 2P, 230V, 60 HZ"
            sum2 <= 70 -> "70 AT, 100 AF, 2P, 230V, 60 HZ"
            sum2 <= 100 -> "100 AT, 125 AF, 2P, 230V, 60 HZ"
            sum2 <= 125 -> "125 AT, 150 AF, 2P, 230V, 60 HZ"
            sum2 <= 150 -> "150 AT, 225 AF, 2P, 230V, 60 HZ"
            sum2 <= 175 -> "175 AT, 225 AF, 2P, 230V, 60 HZ"
            sum2 <= 200 -> "200 AT, 225 AF, 2P, 230V, 60 HZ"
            sum2 <= 225 -> "225 AT, 250 AF, 2P, 230V, 60 HZ"
            sum2 <= 250 -> "250 AT, 250 AF, 2P, 230V, 60 HZ"
            sum2 <= 300 -> "300 AT, 400 AF, 2P, 230V, 60 HZ"
            sum2 <= 400 -> "400 AT, 400 AF, 2P, 230V, 60 HZ"
            sum2 <= 500 -> "500 AT, 600 AF, 2P, 230V, 60 HZ"
            sum2 <= 600 -> "600 AT, 600 AF, 2P, 230V, 60 HZ"
            sum2 <= 700 -> "700 AT, 800 AF, 2P, 230V, 60 HZ"
            sum2 <= 800 -> "800 AT, 800 AF, 2P, 230V, 60 HZ"
            sum2 <= 1000 -> "1000 AT, 1200 AF, 2P, 230V, 60 HZ"
            sum2 <= 1200 -> "1200 AT, 1200 AF, 2P, 230V, 60 HZ"
            sum2 <= 1600 -> "1600 AT, 1600 AF, 2P, 230V, 60 HZ"
            sum2 <= 2000 -> "2000 AT, 2000 AF, 2P, 230V, 60 HZ"
            sum2 <= 2500 -> "2500 AT, 2500 AF, 2P, 230V, 60 HZ"
            sum2 <= 3000 -> "3000 AT, 3000 AF, 2P, 230V, 60 HZ"
            sum2 <= 4000 -> "4000 AT, 4000 AF, 2P, 230V, 60 HZ"
            sum2 <= 5000 -> "5000 AT, 5000 AF, 2P, 230V, 60 HZ"
            sum2 <= 6000 -> "6000 AT, 6000 AF, 2P, 230V, 60 HZ"
            else -> null
        }
    }

    /**
     * Sets FeederWire and FeederWireSecond based on the PassMainWire string.
     */
    private fun setFeederWiresFromMainWire(passMainWire: String) {
        data class FeederSpec(val feeder: String, val feederSecond: String)

        val mapping = mapOf(
            "20 AT, 100 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 3.5mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 3.5mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "30 AT, 100 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 3.5mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 5.5mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "40 AT, 100 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 5.5mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 5.5mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "50 AT, 100 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 5.5mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "60 AT, 100 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 14mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 5.5mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "70 AT, 100 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 22mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "100 AT, 125 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 30mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "125 AT, 150 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 38mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 14mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "150 AT, 225 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 50mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 14mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "175 AT, 225 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 60mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 14mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "200 AT, 225 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 80mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 14mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "225 AT, 250 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 100mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 22mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "250 AT, 250 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 125mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 22mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "300 AT, 400 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 150mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 22mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "350 AT, 400 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 175mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "400 AT, 400 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 200mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "500 AT, 600 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 250mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "600 AT, 600 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 325mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 38mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "700 AT, 800 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 375mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 50mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "800 AT, 800 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 400mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 50mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "1000 AT, 1200 AF, 2P, 230V, 60 HZ" to FeederSpec("2 - 500mm\u00B2 THHN/THWN-2 Cu. Wire", " + 1 - 60mm\u00B2 THHN/THWN-2 Cu. Wire"),
        )

        mapping[passMainWire]?.let { spec ->
            FeederWire.text = spec.feeder
            FeederWireSecond.text = spec.feederSecond
        }
    }

    /**
     * Sets FeederWireFourth (pipe diameter) and bot text (GEC) based on feeder wire size.
     * Used from sumOfLeftAndRightTop.
     */
    private fun setGecAndPipeDiameter(feederW2: String, trimTrailingSpace: Boolean) {
        data class GecSpec(val pipeDiameter: String, val gecWire: String)

        val mapping = mapOf(
            "2 - 3.5mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 20 mm\u00F8", "GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 5.5mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 20 mm\u00F8", "GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 20 mm\u00F8", "GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 14mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 20 mm\u00F8", "GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 22mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 25 mm\u00F8", "GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 30mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 32 mm\u00F8", "GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 38mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 32 mm\u00F8", "GEC: + 1 - 14mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 50mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 40 mm\u00F8", "GEC: + 1 - 14mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 60mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 40 mm\u00F8", "GEC: + 1 - 22mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 80mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 50 mm\u00F8", "GEC: + 1 - 22mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 100mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 50 mm\u00F8", "GEC: + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 125mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 50 mm\u00F8", "GEC: + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 150mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 65 mm\u00F8", "GEC: + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 175mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 65 mm\u00F8", "GEC: + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 200mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 65 mm\u00F8", "GEC: + 1 - 50mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 250mm\u00B2 THHN/THWN-2 Cu. Wire" to GecSpec("(G)In 80 mm\u00F8", "GEC: + 1 - 50mm\u00B2 THHN/THWN-2 Cu. Wire"),
        )

        mapping[feederW2]?.let { spec ->
            FeederWireFourth.text = spec.pipeDiameter
            numBotViews.forEach { it.text = spec.gecWire }
        }
    }

    /**
     * GEC calculation after manually changing feeder wire.
     * Note: original has trailing space in feeder string comparison ("Cu. Wire ").
     */
    private fun gec(feeder2: String) {
        data class GecSpec(val gecWire: String)

        val mapping = mapOf(
            "2 - 3.5mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 5.5mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 14mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 22mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 30mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 8.0mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 38mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 14mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 50mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 14mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 60mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 22mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 80mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 22mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 100mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 125mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 150mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 175mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 30mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 200mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 50mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 250mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 50mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 325mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 60mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 375mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 60mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 400mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 60mm\u00B2 THHN/THWN-2 Cu. Wire"),
            "2 - 500mm\u00B2 THHN/THWN-2 Cu. Wire " to GecSpec("GEC: + 1 - 80mm\u00B2 THHN/THWN-2 Cu. Wire"),
        )

        mapping[feeder2]?.let { spec ->
            numBotViews.forEach { it.text = spec.gecWire }
        }
    }

    // =====================================================================
    //  BACK BUTTON
    // =====================================================================

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (RS30ref.visibility == View.VISIBLE) {
            AlertDialog.Builder(this)
                .setMessage("You cannot go back from this point.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }

    // =====================================================================
    //  RESUME
    // =====================================================================

    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
    }

    // =====================================================================
    //  PAPER SIZE DIALOG -> ADDITIONAL INFO DIALOG -> PDF
    // =====================================================================

    private fun showPaperSizeDialog() {
        val paperSizes = arrayOf<CharSequence>("A1", "A3", "20x30 inches")
        val selectedItem = intArrayOf(-1)

        AlertDialog.Builder(this)
            .setTitle("Choose paper size")
            .setSingleChoiceItems(paperSizes, -1) { _, which ->
                selectedItem[0] = which
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Next") { dialog, _ ->
                if (selectedItem[0] != -1) {
                    val paperSize = paperSizes[selectedItem[0]].toString()
                    dialog.dismiss()
                    showAdditionalInfoDialog(paperSize)
                } else {
                    Toast.makeText(applicationContext, "Please select a paper size", Toast.LENGTH_SHORT).show()
                }
            }
            .create()
            .show()
    }

    private fun showAdditionalInfoDialog(paperSize: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_additional_info, null)

        val etEngineerName = dialogView.findViewById<EditText>(R.id.etEngineerName)
        val etProposedProjName = dialogView.findViewById<EditText>(R.id.etProposedProjName)
        val etLocation = dialogView.findViewById<EditText>(R.id.etLocation)
        val etOwner = dialogView.findViewById<EditText>(R.id.etOwner)
        val etAddress = dialogView.findViewById<EditText>(R.id.etAddress)
        val etRevision1 = dialogView.findViewById<EditText>(R.id.etRevision1)
        val etDesignedBy = dialogView.findViewById<EditText>(R.id.etDesignedBy)
        val etCertifiedBy = dialogView.findViewById<EditText>(R.id.etCertifiedBy)
        val etRevision2 = dialogView.findViewById<EditText>(R.id.etRevision2)
        val etElectrical = dialogView.findViewById<EditText>(R.id.etElectrical)
        val etSheetNo = dialogView.findViewById<EditText>(R.id.etSheetNo)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Save Now") { dialog, _ ->
                // Get TextViews on the layout
                val tvEngineerName: TextView = findViewById(R.id.tvEngineerName)
                val tvProposedProjName: TextView = findViewById(R.id.tvProposedProjName)
                val tvLocation: TextView = findViewById(R.id.tvLocation)
                val tvOwner: TextView = findViewById(R.id.tvOwner)
                val tvAddress: TextView = findViewById(R.id.tvAddress)
                val tvRevision1: TextView = findViewById(R.id.tvRevision1)
                val tvDesignedBy: TextView = findViewById(R.id.tvDesignedBy)
                val tvCertifiedBy: TextView = findViewById(R.id.tvCertifiedBy)
                val tvRevision2: TextView = findViewById(R.id.tvRevision2)
                val tvElectrical: TextView = findViewById(R.id.tvElectrical)
                val tvSheetNo: TextView = findViewById(R.id.tvSheetNo)

                val tvEngineerNameA3: TextView = findViewById(R.id.tvEngineerNameA3)
                val tvProposedProjNameA3: TextView = findViewById(R.id.tvProposedProjNameA3)
                val tvLocationA3: TextView = findViewById(R.id.tvLocationA3)
                val tvOwnerA3: TextView = findViewById(R.id.tvOwnerA3)
                val tvAddressA3: TextView = findViewById(R.id.tvAddressA3)
                val tvRevision1A3: TextView = findViewById(R.id.tvRevision1A3)
                val tvDesignedByA3: TextView = findViewById(R.id.tvDesignedByA3)
                val tvCertifiedByA3: TextView = findViewById(R.id.tvCertifiedByA3)
                val tvRevision2A3: TextView = findViewById(R.id.tvRevision2A3)
                val tvElectricalA3: TextView = findViewById(R.id.tvElectricalA3)
                val tvSheetNoA3: TextView = findViewById(R.id.tvSheetNoA3)

                // Get values, default to space if empty
                fun getOrSpace(et: EditText): String {
                    val s = et.text.toString()
                    return if (s.isEmpty()) " " else s
                }

                val engineerName = getOrSpace(etEngineerName)
                val proposedProjName = getOrSpace(etProposedProjName)
                val location = getOrSpace(etLocation)
                val owner = getOrSpace(etOwner)
                val address = getOrSpace(etAddress)
                val revision1 = getOrSpace(etRevision1)
                val designedBy = getOrSpace(etDesignedBy)
                val certifiedBy = getOrSpace(etCertifiedBy)
                val revision2 = getOrSpace(etRevision2)
                val electrical = getOrSpace(etElectrical)
                val sheetNo = getOrSpace(etSheetNo)

                // Set text to TextViews (A1/20x30)
                tvEngineerName.text = engineerName.uppercase()
                tvProposedProjName.text = proposedProjName.uppercase()
                tvLocation.text = location.uppercase()
                tvOwner.text = owner.uppercase()
                tvAddress.text = address.uppercase()
                tvRevision1.text = revision1.uppercase()
                tvDesignedBy.text = designedBy.uppercase()
                tvCertifiedBy.text = certifiedBy.uppercase()
                tvRevision2.text = revision2.uppercase()
                tvElectrical.text = electrical.uppercase()
                tvSheetNo.text = sheetNo.uppercase()

                // Set text to A3 TextViews
                tvEngineerNameA3.text = engineerName.uppercase()
                tvProposedProjNameA3.text = proposedProjName.uppercase()
                tvLocationA3.text = location.uppercase()
                tvOwnerA3.text = owner.uppercase()
                tvAddressA3.text = address.uppercase()
                tvRevision1A3.text = revision1.uppercase()
                tvDesignedByA3.text = designedBy.uppercase()
                tvCertifiedByA3.text = certifiedBy.uppercase()
                tvRevision2A3.text = revision2.uppercase()
                tvElectricalA3.text = electrical.uppercase()
                tvSheetNoA3.text = sheetNo.uppercase()

                // Capture layout as image
                if (currentTableCount < 3) {
                    currentTableCount++
                    captureRelativeLayoutAsImage()
                }

                dialog.dismiss()

                // Show progress dialog and generate PDF
                val progressDialog = ProgressDialog(this).apply {
                    setMessage("Saving PDF...")
                    setCancelable(false)
                    show()
                }

                Thread {
                    try {
                        val currentDateAndTime = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                        val filename = "HELP_${paperSize}_$currentDateAndTime.pdf"
                        val pdf = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename)
                        val outputStream = FileOutputStream(pdf)

                        val pageSize: Rectangle? = when (paperSize.lowercase()) {
                            "a1" -> PageSize.A1
                            "a3" -> Rectangle(841.68f, 1190.5f)
                            "20x30 inches" -> Rectangle(1441f, 2163f)
                            else -> null
                        }

                        if (pageSize != null) {
                            val document = Document(pageSize.rotate())
                            PdfWriter.getInstance(document, outputStream)
                            document.open()
                            document.add(Paragraph(paperSize))

                            val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            for (i in 1..3) {
                                val keyRela = "relativeLayout$i"
                                val relaImageBase64 = prefs.getString(keyRela, null)

                                if (relaImageBase64 != null) {
                                    try {
                                        // Add border image
                                        val borderId = if ("20x30 inches" == paperSize) {
                                            resources.getIdentifier("a20x30border", "drawable", packageName)
                                        } else {
                                            resources.getIdentifier("${paperSize.lowercase()}border", "drawable", packageName)
                                        }

                                        if (borderId != 0) {
                                            val options = BitmapFactory.Options()
                                            options.inJustDecodeBounds = true
                                            BitmapFactory.decodeResource(resources, borderId, options)
                                            options.inSampleSize = calculateInSampleSize(options, 1000, 1000)
                                            options.inJustDecodeBounds = false
                                            val bmp = BitmapFactory.decodeResource(resources, borderId, options)

                                            val stream = ByteArrayOutputStream()
                                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                            val image = Image.getInstance(stream.toByteArray())

                                            if ("20x30 inches" == paperSize) {
                                                val widthPercentage = (pageSize.width / image.width) * 150
                                                val heightPercentage = (pageSize.height / image.height) * 66
                                                image.scalePercent(widthPercentage, heightPercentage)
                                            } else {
                                                val widthPercentage = (pageSize.width / image.width) * 140
                                                val heightPercentage = (pageSize.height / image.height) * 70
                                                image.scalePercent(widthPercentage, heightPercentage)
                                            }
                                            image.setAbsolutePosition(0f, 0f)
                                            document.add(image)
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }

                                    // Add captured layout image
                                    val relaImageBytes = Base64.decode(relaImageBase64, Base64.DEFAULT)
                                    val relaBitmap = BitmapFactory.decodeByteArray(relaImageBytes, 0, relaImageBytes.size)
                                    val relaStream = ByteArrayOutputStream()
                                    relaBitmap.compress(Bitmap.CompressFormat.PNG, 100, relaStream)
                                    val relaImage = Image.getInstance(relaStream.toByteArray())

                                    // Convert info TextViews to bitmaps and add as images
                                    fun addTextViewImage(tv: TextView, x: Float, y: Float, scale: Float = 1f) {
                                        try {
                                            val bm = convertViewToBitmap(tv)
                                            val s = ByteArrayOutputStream()
                                            bm.compress(Bitmap.CompressFormat.PNG, 100, s)
                                            val img = Image.getInstance(s.toByteArray())
                                            if (scale != 1f) {
                                                img.scaleAbsolute(img.width / scale, img.height / scale)
                                            }
                                            img.setAbsolutePosition(x, y)
                                            document.add(img)
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }

                                    var desiredWidth = 0
                                    var desiredHeight = 0
                                    var xPosition = 0f
                                    var yPosition = 0f

                                    when (paperSize.lowercase()) {
                                        "a1" -> {
                                            desiredWidth = 2000
                                            desiredHeight = (pageSize.height / 2).toInt()
                                            xPosition = (pageSize.height - desiredWidth) + 1200
                                            yPosition = 350f

                                            addTextViewImage(tvEngineerName, 500f, 150f)
                                            addTextViewImage(tvProposedProjName, 775f, 140f)
                                            addTextViewImage(tvOwner, 1180f, 140f)
                                            addTextViewImage(tvElectrical, 1965f, 130f)
                                            addTextViewImage(tvSheetNo, 2270f, 100f)
                                            addTextViewImage(tvDesignedBy, 1800f, 170f)
                                            addTextViewImage(tvCertifiedBy, 1800f, 120f)
                                            addTextViewImage(tvRevision2, 1800f, 85f)
                                            addTextViewImage(tvAddress, 1200f, 85f)
                                            addTextViewImage(tvLocation, 850f, 85f)
                                        }
                                        "a3" -> {
                                            desiredWidth = 1100
                                            desiredHeight = (pageSize.height / 1.8).toInt()
                                            xPosition = pageSize.height - desiredWidth
                                            yPosition = 150f

                                            addTextViewImage(tvEngineerNameA3, 250f, 75f, 2f)
                                            addTextViewImage(tvProposedProjNameA3, 400f, 65f, 2f)
                                            addTextViewImage(tvOwnerA3, 600f, 65f, 2f)
                                            addTextViewImage(tvElectricalA3, 980f, 65f, 2f)
                                            addTextViewImage(tvSheetNoA3, 1120f, 50f, 2f)
                                            addTextViewImage(tvDesignedByA3, 890f, 85f, 2f)
                                            addTextViewImage(tvCertifiedByA3, 890f, 60f, 2f)
                                            addTextViewImage(tvRevision2A3, 890f, 45f, 2f)
                                            addTextViewImage(tvAddressA3, 600f, 44f, 2f)
                                            addTextViewImage(tvLocationA3, 415f, 44f, 1.8f)
                                        }
                                        "20x30 inches" -> {
                                            desiredWidth = 2000
                                            desiredHeight = (pageSize.height / 1.8).toInt()
                                            xPosition = (pageSize.height - desiredWidth) + 880
                                            yPosition = 300f

                                            addTextViewImage(tvEngineerName, 460f, 100f)
                                            addTextViewImage(tvProposedProjName, 710f, 80f)
                                            addTextViewImage(tvOwner, 1070f, 80f)
                                            addTextViewImage(tvElectrical, 1800f, 80f)
                                            addTextViewImage(tvSheetNo, 2060f, 50f)
                                            addTextViewImage(tvDesignedBy, 1650f, 100f)
                                            addTextViewImage(tvCertifiedBy, 1650f, 60f)
                                            addTextViewImage(tvRevision2, 1650f, 35f)
                                            addTextViewImage(tvAddress, 1100f, 37f)
                                            addTextViewImage(tvLocation, 750f, 37f)
                                        }
                                        else -> {
                                            desiredWidth = (pageSize.width - 30).toInt()
                                            desiredHeight = (pageSize.height / 4).toInt()
                                            xPosition = 30f
                                            yPosition = 30f + (i - 1) * desiredHeight
                                        }
                                    }

                                    relaImage.scaleAbsolute(desiredWidth.toFloat(), desiredHeight.toFloat())
                                    relaImage.setAbsolutePosition(xPosition, yPosition)
                                    document.add(relaImage)

                                    if (currentTableCount > 0) {
                                        document.newPage()
                                    }
                                }
                            }

                            document.close()
                            outputStream.close()

                            runOnUiThread {
                                AlertDialog.Builder(this)
                                    .setTitle("PDF Saved")
                                    .setMessage("Your PDF file has been saved. Please check your Downloads folder.")
                                    .setPositiveButton(android.R.string.ok) { d, _ -> d.dismiss() }
                                    .show()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(applicationContext, "Please select a paper size", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Error occurred while saving PDF", Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        runOnUiThread {
                            progressDialog.dismiss()
                        }
                    }
                }.start()
            }
            .create()
            .show()
    }

    // =====================================================================
    //  CAPTURE RELATIVE LAYOUT AS IMAGE
    // =====================================================================

    private fun captureRelativeLayoutAsImage() {
        val relativeLayout: RelativeLayout = findViewById(R.id.aaaa)

        // Store original top margin
        val originalLayoutParams = relativeLayout.layoutParams as RelativeLayout.LayoutParams
        val originalTopMargin = originalLayoutParams.topMargin

        // Temporarily set top margin to 0
        originalLayoutParams.setMargins(
            originalLayoutParams.leftMargin, 0,
            originalLayoutParams.rightMargin, originalLayoutParams.bottomMargin
        )
        relativeLayout.layoutParams = originalLayoutParams

        // Convert to bitmap
        val bitmap = Bitmap.createBitmap(
            relativeLayout.width, relativeLayout.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        relativeLayout.draw(canvas)

        // Restore original margins
        originalLayoutParams.setMargins(
            originalLayoutParams.leftMargin, originalTopMargin,
            originalLayoutParams.rightMargin, originalLayoutParams.bottomMargin
        )
        relativeLayout.layoutParams = originalLayoutParams

        // Save to SharedPreferences as Base64
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val keyRela = "relativeLayout$currentTableCount"
        prefs.edit().putString(keyRela, Base64.encodeToString(byteArray, Base64.DEFAULT)).apply()
    }

    private fun convertViewToBitmap(textView: TextView): Bitmap {
        val bitmap = Bitmap.createBitmap(textView.width, textView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        textView.layout(0, 0, textView.width, textView.height)
        textView.draw(canvas)
        return bitmap
    }

    companion object {
        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }
    }
}

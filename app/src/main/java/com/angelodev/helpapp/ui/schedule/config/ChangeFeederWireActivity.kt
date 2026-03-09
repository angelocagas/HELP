package com.angelodev.helpapp.ui.schedule.config

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angelodev.helpapp.R
import com.angelodev.helpapp.databinding.ActivityChangeFeederWireBinding
import com.angelodev.helpapp.ui.schedule.LoadScheduleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeFeederWireActivity : AppCompatActivity() {

    private val viewModel: ConfigViewModel by viewModels()
    private lateinit var binding: ActivityChangeFeederWireBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_feeder_wire)
        binding.lifecycleOwner = this

        binding.next.setOnClickListener {
            when {
                binding.one.isChecked -> {
                    binding.WFGT.text = "TW"
                    viewModel.prefsManager.feederWireType = "TW"
                }
                binding.two.isChecked -> {
                    binding.WFGT.text = "THHN"
                    viewModel.prefsManager.feederWireType = "THHN"
                }
                else -> {
                    Toast.makeText(this, "Choose wire for Ground", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            startActivity(Intent(this, LoadScheduleActivity::class.java))
            finish()
        }
    }
}

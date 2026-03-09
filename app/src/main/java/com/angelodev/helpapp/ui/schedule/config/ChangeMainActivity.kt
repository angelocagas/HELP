package com.angelodev.helpapp.ui.schedule.config

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angelodev.helpapp.R
import com.angelodev.helpapp.databinding.ActivityChangeMainBinding
import com.angelodev.helpapp.ui.schedule.LoadScheduleActivity
import com.angelodev.helpapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeMainActivity : AppCompatActivity() {

    private val viewModel: ConfigViewModel by viewModels()
    private lateinit var binding: ActivityChangeMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_main)
        binding.lifecycleOwner = this

        binding.next.setOnClickListener {
            val at = binding.AT.text.toString().trim()
            val af = binding.AF.text.toString().trim()

            if (at.isEmpty()) {
                binding.AT.error = "Put Some Value For AMPERE TRIP"
                return@setOnClickListener
            }
            if (af.isEmpty()) {
                binding.AF.error = "Put Some Value For AMPERE FRAME"
                return@setOnClickListener
            }

            val mainText = "$at AT, $af AF, 2P, 230V, 60 HZ"
            binding.UpdatedMain.text = mainText
            viewModel.prefsManager.updatedMain = mainText

            val intent = Intent(this, LoadScheduleActivity::class.java).apply {
                putExtra(Constants.EXTRA_EXECUTE_CODE_3, true)
            }
            startActivity(intent)
            finish()
        }
    }
}

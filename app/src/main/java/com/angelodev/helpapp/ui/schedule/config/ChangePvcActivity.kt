package com.angelodev.helpapp.ui.schedule.config

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angelodev.helpapp.R
import com.angelodev.helpapp.databinding.ActivityChangePvcBinding
import com.angelodev.helpapp.ui.schedule.LoadScheduleActivity
import com.angelodev.helpapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePvcActivity : AppCompatActivity() {

    private val viewModel: ConfigViewModel by viewModels()
    private lateinit var binding: ActivityChangePvcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_pvc)
        binding.lifecycleOwner = this

        binding.next.setOnClickListener {
            val value = binding.NumberPVC.text.toString().trim()
            if (value.isEmpty()) {
                binding.NumberPVC.error = "Put Some Value"
                return@setOnClickListener
            }

            val text = " (G)In $value mmø IMC PIPE"
            binding.NumPVC.text = text

            val intent = Intent(this, LoadScheduleActivity::class.java).apply {
                putExtra(Constants.EXTRA_NUM_PVC, text)
            }
            startActivity(intent)
            finish()
        }
    }
}

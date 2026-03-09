package com.angelodev.helpapp.ui.schedule.config

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angelodev.helpapp.R
import com.angelodev.helpapp.databinding.ActivityPipeBinding
import com.angelodev.helpapp.ui.schedule.LoadScheduleActivity
import com.angelodev.helpapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePipeSizeActivity : AppCompatActivity() {

    private val viewModel: ConfigViewModel by viewModels()
    private lateinit var binding: ActivityPipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pipe)
        binding.lifecycleOwner = this

        binding.next.setOnClickListener {
            val wireSection = binding.TT.text.toString().trim()
            if (wireSection.isEmpty()) {
                binding.TT.error = "Put Some Value"
                return@setOnClickListener
            }

            val text = "+ 1 - $wireSection mm.sq. THHN Cu. Wire"
            binding.Updatedtt.text = text
            viewModel.prefsManager.pipeWire = text

            val intent = Intent(this, LoadScheduleActivity::class.java).apply {
                putExtra(Constants.EXTRA_EXECUTE_CODE_2, true)
            }
            startActivity(intent)
            finish()
        }
    }
}

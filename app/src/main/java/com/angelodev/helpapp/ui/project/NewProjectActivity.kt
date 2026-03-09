package com.angelodev.helpapp.ui.project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angelodev.helpapp.R
import com.angelodev.helpapp.databinding.ActivityNewProjectBinding
import com.angelodev.helpapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewProjectActivity : AppCompatActivity() {

    private val viewModel: NewProjectViewModel by viewModels()
    private lateinit var binding: ActivityNewProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_project)
        binding.lifecycleOwner = this

        binding.proceed.setOnClickListener {
            viewModel.onProceed(
                projectName = binding.name.text.toString().trim(),
                quantity = binding.circuitNum.text.toString().trim(),
                isTW = binding.one.isChecked,
                isTHHN = binding.two.isChecked
            )
        }

        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.navigateToInput.observe(this) { config ->
            config?.let {
                val intent = Intent(this, CircuitInputActivity::class.java).apply {
                    putExtra(Constants.EXTRA_PROJECT_CONFIG, it)
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

package hu.bme.aut.mobweb.wf72qq.globetrotter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import hu.bme.aut.mobweb.wf72qq.globetrotter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object  {
        const val KEY_NAME = "KEY_NAME"
        const val KEY_PASSWORD = "KEY_PASSWORD"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelButton.setOnClickListener {
            binding.usernameEditText.setText("")
            binding.passwordEditText.setText("")
        }

        binding.loginButton.setOnClickListener {
            if (TextUtils. isEmpty(binding.usernameEditText.text))
            {
                binding.usernameEditText.error = getString(R.string.emptyName)
            }
            else
            {
                val intentCountryList = Intent(this@MainActivity,CountryListActivity::class.java)
                intentCountryList.putExtra(KEY_NAME, binding.usernameEditText.text.toString())
                intentCountryList.putExtra(KEY_PASSWORD, binding.passwordEditText.text.toString())
                startActivity(intentCountryList)
            }
        }
    }
}

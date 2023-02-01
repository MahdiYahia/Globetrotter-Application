package hu.bme.aut.mobweb.wf72qq.globetrotter.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.mobweb.wf72qq.globetrotter.R
import hu.bme.aut.mobweb.wf72qq.globetrotter.data.Name
import hu.bme.aut.mobweb.wf72qq.globetrotter.databinding.DialogCountryNameBinding


class CountryDialog: DialogFragment() {

    interface CountryHandler {
        fun countryAdded(country: String)
    }

    lateinit var countryHandler: CountryHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CountryHandler)
        countryHandler = context
    }

    private lateinit var etCountryName: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogCountryNameBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        etCountryName = binding.etCountryName

        builder.setTitle("Country dialog")
        builder.setPositiveButton("Ok") { dialog, which ->
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
        }
        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etCountryName.text.isEmpty()) {
                etCountryName.error = getString(R.string.emptyName)
            } else {
                countryHandler.countryAdded(etCountryName.text.toString())
            }
        }
    }
}
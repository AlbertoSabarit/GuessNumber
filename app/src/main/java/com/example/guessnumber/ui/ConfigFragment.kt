package com.example.guessnumber.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.guessnumber.R
import com.example.guessnumber.data.Juego
import com.example.guessnumber.databinding.FragmentConfigBinding
import com.example.guessnumber.usecase.ConfigState
import com.example.guessnumber.usecase.ConfigViewModel
import com.google.android.material.textfield.TextInputLayout

/**
 * En este fragment se indicará el nombre del jugar y el número de intentos de los que dispondrá
 */
class ConfigFragment : Fragment() {

    private var _binding: FragmentConfigBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ConfigViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentConfigBinding.inflate(inflater, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tieNombre.addTextChangedListener(ErrorsWatchers(binding.tilNombre))
        binding.tieIntentos.addTextChangedListener(ErrorsWatchers(binding.tilIntentos))

        binding.btnJugar.setOnClickListener {

            viewModel.validarNombreYNumero()
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                ConfigState.EmptyName -> setEmptyName()
                ConfigState.EmptyNumber -> setEmptyNumber()
                ConfigState.NumberFormatError -> setNumberFormatError()
                ConfigState.Success -> onSuccess()
            }
        })
    }

    private fun onSuccess() {

        val juego =
            Juego(binding.tieNombre.text.toString(), binding.tieIntentos.text.toString().toInt())

        val bundle = Bundle()
        bundle.putParcelable(Juego.TAG, juego)

        findNavController().navigate(R.id.action_configFragment_to_playFragment, bundle)
        Toast.makeText(context, "Exito al crear el juego", Toast.LENGTH_SHORT).show()
    }

    private fun setNumberFormatError() {
        binding.tilIntentos.error = "Número erróneo"
        binding.tilIntentos.requestFocus()
    }

    private fun setEmptyNumber() {
        binding.tilIntentos.error = "Numero vacío"
        binding.tilIntentos.requestFocus()
    }

    private fun setEmptyName() {
        binding.tilNombre.error = "Nombre vacío"
        binding.tilNombre.requestFocus()
    }

    inner class ErrorsWatchers(val til: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }

    }

}
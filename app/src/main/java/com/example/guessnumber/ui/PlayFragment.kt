package com.example.guessnumber.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.guessnumber.R
import com.example.guessnumber.data.Juego
import com.example.guessnumber.databinding.FragmentPlayBinding
import com.example.guessnumber.usecase.PlayState
import com.example.guessnumber.usecase.PlayViewModel
import com.google.android.material.textfield.TextInputLayout

/**
 * En este fragment se irán introduciendo números y la app irá diciendo si el número
 * a adivinar es mayor o menor que el introducido
 */
class PlayFragment : Fragment() {

    private var _binding: FragmentPlayBinding? = null

    private val binding get() = _binding!!

    private val viewModel: PlayViewModel by viewModels()

    private var juego: Juego? = null
    private var intentosRestantes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlayBinding.inflate(inflater, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tieNumero.addTextChangedListener(ErrorsWatchers(binding.tilNumero))

        juego = requireArguments().getParcelable(Juego.TAG)
        intentosRestantes = juego?.nIntentos ?: 0

        binding.btnJugar.setOnClickListener {

            validarNumeroIngresado()
            binding.tieNumero.isEnabled = false
        }

        binding.btnNuevoIntento.setOnClickListener {

            binding.tieNumero.isEnabled = true
            binding.tieNumero.text = null
            binding.tvNumeroEsMayor.visibility = View.GONE
            binding.tvNumeroEsMenor.visibility = View.GONE
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                PlayState.EmptyNumber -> setEmptyNumber()
                PlayState.NumberFormatError -> setNumberFormatError()
                PlayState.Success -> onSuccess()
            }
        })
    }

    private fun validarNumeroIngresado() {
        val numeroIngresado = binding.tieNumero.text.toString()

        if (numeroIngresado.isEmpty()) {
            setEmptyNumber()
            return
        }

        if (numeroIngresado.toIntOrNull() == null) {
            setNumberFormatError()
            return
        }

        val numeroCorrecto = juego?.numeroAleatorio ?: 0

        when {
            numeroIngresado.toInt() == numeroCorrecto -> {
                juego!!.contador++
                viewModel.validarNumeroAcertado()
            }

            numeroIngresado.toInt() < numeroCorrecto -> {
                juego!!.contador++
                binding.tvNumeroEsMayor.visibility = View.VISIBLE
            }

            else -> {
                juego!!.contador++
                binding.tvNumeroEsMenor.visibility = View.VISIBLE
            }
        }

        intentosRestantes--

        if (intentosRestantes == 0) {
            juego!!.acertado = false
            val bundle = bundleOf(Juego.TAG to juego)
            findNavController().navigate(R.id.action_playFragment_to_endPlayFragment, bundle)
            Toast.makeText(context, "Has fallado", Toast.LENGTH_LONG).show()
        }
    }

    private fun onSuccess() {
        juego!!.acertado = true
        val bundle = bundleOf(Juego.TAG to juego)
        findNavController().navigate(R.id.action_playFragment_to_endPlayFragment, bundle)
        Toast.makeText(context, "¡Numero acertado!", Toast.LENGTH_LONG).show()
    }

    private fun setEmptyNumber() {
        binding.tilNumero.error = "Numero vacío"
        binding.tilNumero.requestFocus()
    }

    private fun setNumberFormatError() {
        binding.tilNumero.error = "Numero erróneo"
        binding.tilNumero.requestFocus()
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
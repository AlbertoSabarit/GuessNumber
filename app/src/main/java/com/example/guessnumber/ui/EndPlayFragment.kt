package com.example.guessnumber.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guessnumber.data.Juego
import com.example.guessnumber.databinding.FragmentEndPlayBinding

/**
 * En este fragment sale el nombre del jugar y si ha ganado o perdido.
 * Así como en cuantos intentos lo hizo.
 */

class EndPlayFragment : Fragment() {

    private var _binding: FragmentEndPlayBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEndPlayBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.juego = requireArguments().getParcelable(Juego.TAG)

        with(binding) {
            tvNombre.text = juego!!.nombre
            tvNIntentos.text = juego!!.contador.toString()
            if (juego!!.acertado) {
                tvFraseGanadora.visibility = View.VISIBLE
                tvFrasePerdedora.visibility = View.GONE
            } else {
                tvFraseGanadora.visibility = View.GONE
                tvFrasePerdedora.visibility = View.VISIBLE
            }
        }
    }


}
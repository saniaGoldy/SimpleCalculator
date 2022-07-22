package com.example.simplecaclulator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.simplecaclulator.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            clearButton.setOnClickListener { clearAction() }
            backspaceButton.setOnClickListener { backspaceAction() }
            addDecimalButton.setOnClickListener { addDecimal(it) }
            equalsButton.setOnClickListener { equalsAction() }

            //number buttons
            Button1.setOnClickListener { numberAction(it) }
            Button2.setOnClickListener { numberAction(it) }
            Button3.setOnClickListener { numberAction(it) }
            Button4.setOnClickListener { numberAction(it) }
            Button5.setOnClickListener { numberAction(it) }
            Button6.setOnClickListener { numberAction(it) }
            Button7.setOnClickListener { numberAction(it) }
            Button8.setOnClickListener { numberAction(it) }
            Button9.setOnClickListener { numberAction(it) }
            ZeroButton.setOnClickListener { numberAction(it) }
            DoubleZeroButton.setOnClickListener {
                if (workingsTV.length() > 0 && lastCharIsNumber())
                    numberAction(it)
                else
                    numberAction(ZeroButton)
            }

            divideButton.setOnClickListener { operationAction(it) }
            multiplyButton.setOnClickListener { operationAction(it) }
            minusButton.setOnClickListener { operationAction(it) }
            plusButton.setOnClickListener { operationAction(it) }
            percentButton.setOnClickListener { percentAction(it) }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun numberAction(view: View) {
        if (view is Button) {
            with(binding) {
                workingsTV.append(view.text)
                Log.d(TAG, "numberAction: ${workingsTV.text}")
                updateResultsTV()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun operationAction(view: View) {
        if (view is Button) {
            with(binding) {
                val operation = view.text
                val length = workingsTV.length()
                if (length > 0) {
                    if (!lastCharIsNumber() && !lastCharIsPercent()) {
                        if (length > 1) {
                            workingsTV.text =
                                workingsTV.text.subSequence(0, length - 1).toString() + operation
                        }
                    } else
                        workingsTV.append(operation)
                } else if (operation == getString(R.string.minus)) {
                    workingsTV.append(operation)
                }
                updateResultsTV()
            }
        }
    }

    private fun clearAction() {
        with(binding) {
            workingsTV.text = ""
            resultsTV.text = ""
        }
    }

    private fun percentAction(view: View) {
        if (view is Button) {
            with(binding) {
                if (workingsTV.length() > 0 && (lastCharIsNumber() || lastCharIsPercent()))
                    workingsTV.append(view.text)
                updateResultsTV()
            }
        }
    }

    private fun backspaceAction() {
        with(binding) {
            val length = workingsTV.length()
            if (length > 0)
                workingsTV.text = workingsTV.text.subSequence(0, length - 1)
            updateResultsTV()
        }
    }

    private fun equalsAction() {
        with(binding) {
            try {
                workingsTV.text = parseCalculatorString(workingsTV.text.toString()).toString()
                resultsTV.text = ""
            } catch (ex: IllegalArgumentException) {
                resultsTV.text = getString(R.string.errorCalculatorMessage)
            }
        }
    }

    private fun updateResultsTV() {
        with(binding) {
            resultsTV.text = try {
                parseCalculatorString(workingsTV.text.toString()).toString()
            } catch (ex: IllegalArgumentException) {
                getString(R.string.errorCalculatorMessage)
            }
        }
    }

    private fun addDecimal(view: View) {
        with(binding) {
            if (view is Button) {
                if (lastCharIsNumber()) {
                    workingsTV.append(view.text)
                } else if (!lastCharIsPercent()) {
                    if (lastChar() == getString(R.string.minus)[0] || lastChar() == null) {
                        workingsTV.append("0.")
                    }
                }
            }
        }
    }

    private fun lastCharIsNumber() = lastChar() in '0'..'9'

    private fun lastCharIsPercent() = lastChar() == getString(R.string.percent)[0]

    private fun lastChar(): Char? {
        with(binding) {
            return if (workingsTV.length() == 0) {
                null
            } else
                workingsTV.text[workingsTV.length() - 1]
        }
    }

    companion object {
        fun newInstance() = CalculatorFragment()
    }
}
package com.example.simplecaclulator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.simplecaclulator.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val model by viewModels<CalculatorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            val workingsObserver = Observer<String> {
                workingsTV.text = it
            }
            val resultsObserver = Observer<String> {
                resultsTV.text = it
            }

            model.workings.observe(viewLifecycleOwner, workingsObserver)
            model.results.observe(viewLifecycleOwner, resultsObserver)

            clearButton.setOnClickListener { clearAction() }
            backspaceButton.setOnClickListener { backspaceAction() }
            addDecimalButton.setOnClickListener { model.parseDecimalAction() }
            equalsButton.setOnClickListener { equalsAction() }

            //number buttons
            button1.setOnClickListener { parseNumberAction("1") }
            button2.setOnClickListener { parseNumberAction("2") }
            button3.setOnClickListener { parseNumberAction("3") }
            button4.setOnClickListener { parseNumberAction("4") }
            button5.setOnClickListener { parseNumberAction("5") }
            button6.setOnClickListener { parseNumberAction("6") }
            button7.setOnClickListener { parseNumberAction("7") }
            button8.setOnClickListener { parseNumberAction("8") }
            button9.setOnClickListener { parseNumberAction("9") }
            zeroButton.setOnClickListener { parseNumberAction("0") }
            doubleZeroButton.setOnClickListener { doubleZeroAction() }

            divideButton.setOnClickListener { operationAction(Operands.DIVISION) }
            multiplyButton.setOnClickListener { operationAction(Operands.MULTIPLY) }
            minusButton.setOnClickListener { operationAction(Operands.MINUS) }
            plusButton.setOnClickListener { operationAction(Operands.PlUS) }
            percentButton.setOnClickListener { percentAction() }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun parseNumberAction(number: String) {
        model.numberAction(number)
        updateResultsTV()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun doubleZeroAction() {
        model.doubleZeroValidation()
        updateResultsTV()
    }


    private fun operationAction(operand: Operands) {
        model.operationValidation(operand.sign)
        updateResultsTV()
    }

    private fun clearAction() {
        model.workings.value = ""
        model.results.value = ""
    }

    private fun percentAction() {
        model.percentValidation(Symbols.PERCENT.value)
        updateResultsTV()
    }

    private fun backspaceAction() {
        model.backspaceValidation()
        updateResultsTV()
    }

    private fun equalsAction() {
        try {
            model.workings.value =
                model.workings.value?.let { parseCalculatorString(it).toString() }
            model.results.value = ""
        } catch (ex: IllegalArgumentException) {
            model.results.value = getString(R.string.errorCalculatorMessage)
        }
    }

    private fun updateResultsTV() {
        model.results.value = try {
            model.workings.value?.let { parseCalculatorString(it).toString() }
        } catch (ex: IllegalArgumentException) {
            getString(R.string.errorCalculatorMessage)
        }
    }

    companion object {
        fun newInstance() = CalculatorFragment()
    }
}
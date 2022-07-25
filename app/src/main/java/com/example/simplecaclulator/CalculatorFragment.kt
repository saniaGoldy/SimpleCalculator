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
            backspaceButton.setOnClickListener {
                model.parseBackspace()
                model.updateResults(getString(R.string.errorCalculatorMessage))
            }
            addDecimalButton.setOnClickListener { model.parseDecimalAction() }
            equalsButton.setOnClickListener { model.equalsAction(getString(R.string.errorCalculatorMessage)) }

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
            doubleZeroButton.setOnClickListener {
                model.parseDoubleZeroAction()
                model.updateResults(getString(R.string.errorCalculatorMessage))
            }

            divideButton.setOnClickListener { operationAction(Operands.DIVISION) }
            multiplyButton.setOnClickListener { operationAction(Operands.MULTIPLY) }
            minusButton.setOnClickListener { operationAction(Operands.MINUS) }
            plusButton.setOnClickListener { operationAction(Operands.PlUS) }
            percentButton.setOnClickListener {
                model.parsePercent(Symbols.PERCENT.value)
                model.updateResults(getString(R.string.errorCalculatorMessage))
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseNumberAction(number: String) {
        with(model) {
            numberAction(number)
            updateResults(getString(R.string.errorCalculatorMessage))
        }
    }

    private fun operationAction(operand: Operands) {
        model.parseOperation(operand.sign)
        model.updateResults(getString(R.string.errorCalculatorMessage))
    }

    private fun clearAction() {
        model.workings.value = ""
        model.results.value = ""
    }

    companion object {
        fun newInstance() = CalculatorFragment()
    }
}
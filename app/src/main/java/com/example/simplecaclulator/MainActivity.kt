package com.example.simplecaclulator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.simplecaclulator.databinding.ActivityMainBinding

const val TAG = "MyApp"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val workingsTV: TextView
        get() = findViewById(R.id.workingsTV)
    private val resultsTV: TextView
        get() = findViewById(R.id.resultsTV)

    private var operationApplicable = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            clearButton.setOnClickListener { clearAction() }
            backspaceButton.setOnClickListener { backspaceAction() }
            addDecimalButton.setOnClickListener { addDecimal(it) }

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
            percentButton.setOnClickListener {percentAction(it) }
        }

    }

    private fun numberAction(view: View) {
        if (view is Button) {
            workingsTV.append(view.text)
            Log.d(TAG, "numberAction: ${workingsTV.text}")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun operationAction(view: View) {
        if (view is Button) {
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
        }
    }

    private fun lastCharIsPercent() = lastChar() == getString(R.string.percent)[0]

    private fun clearAction() {
        workingsTV.text = ""
        resultsTV.text = ""
    }

    fun percentAction(view: View) {
        if (view is Button) {
            if (workingsTV.length() > 0 && (lastCharIsNumber() || lastCharIsPercent()))
                workingsTV.append(view.text)
        }
    }

    private fun backspaceAction() {
        val length = workingsTV.length()
        if (length > 0)
            workingsTV.text = workingsTV.text.subSequence(0, length - 1)
    }

    fun equalsAction(view: View) {}

    private fun addDecimal(view: View) {
        if (view is Button) {
            if (workingsTV.length() > 0 && lastCharIsNumber()) {
                workingsTV.append(view.text)
            } else if (!lastCharIsPercent()) {
                workingsTV.text = "0,"
            }
        }
    }

    private fun lastCharIsNumber() = workingsTV.text[workingsTV.length() - 1] in '0'..'9'

    private fun lastChar() = workingsTV.text[workingsTV.length() - 1]
}
package com.example.simplecaclulator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    val workings = MutableLiveData("")
    val results = MutableLiveData("")

    private val workingsLength: Int
        get() {
            return workings.value?.length ?: 0
        }

    fun parseDoubleZeroAction() {
        if (workingsLength > 0 && lastCharIsNumber())
            numberAction("00")
        else
            numberAction("0")
    }

    fun numberAction(number: String) {
        workings.value = workings.value + number
        Log.d(TAG, "numberAction: ${workings.value}")
    }

    fun parseOperation(operation: Char) {
        if (workingsLength > 0) {
            if (!lastCharIsNumber() && !lastCharIsPercent()) {
                if (workingsLength > 1) {
                    workings.value = workings.value?.substring(0, workingsLength - 1) + operation
                }
            } else
                workings.value = workings.value + operation
        } else if (operation == Operands.MINUS.sign) {
            workings.value = workings.value + operation
        }
    }

    fun parsePercent(operation: Char) {
        if (workingsLength > 0 && (lastCharIsNumber() || lastCharIsPercent()))
            workings.value = workings.value + operation
    }

    fun parseBackspace() {
        if (workingsLength > 0)
            workings.value = workings.value?.substring(0, workingsLength - 1)
    }

    fun parseDecimalAction() {
        if (lastCharIsNumber()) {
            workings.value = workings.value + Symbols.DOT.value
        } else if (!lastCharIsPercent()) {
            if (lastChar() == Operands.MINUS.sign || lastChar() == null) {
                workings.value = workings.value + "0" + Symbols.DOT.value
            }
        }
    }

    fun updateResults(errorMessage: String) {
        results.value =try {
            workings.value?.let { parseCalculatorString(it).toString() }
        } catch (ex: IllegalArgumentException){
            errorMessage
        }
    }

    fun equalsAction(errorMessage: String) {
        try {
            workings.value =
                workings.value?.let { parseCalculatorString(it).toString() }
            results.value = ""
        } catch (ex: IllegalArgumentException) {
            results.value = errorMessage
        }
    }

    private fun lastCharIsPercent() = lastChar() == Symbols.PERCENT.value

    private fun lastCharIsNumber() = lastChar() in '0'..'9'

    private fun lastChar(): Char? {
        return if (workingsLength == 0) {
            null
        } else
            workings.value?.get(workingsLength - 1)

    }
}
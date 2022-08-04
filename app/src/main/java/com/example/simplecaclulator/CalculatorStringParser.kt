package com.example.simplecaclulator

import android.util.Log
import kotlin.math.pow

fun parseCalculatorString(input: String): Double {
    var workingString = ""
    //Add multiply signs after percent signs if needed
    for (id in input.indices) {
        workingString += (input[id])
        if (input[id] == Symbols.PERCENT.value && isNextNumberOrMinus(input, id))
            workingString += (Operands.MULTIPLY.sign)
    }
    //split string by operators and parse percent signs
    val splitString: MutableList<String> = workingString.parsePercents()
    Log.d(TAG, "parseCalculatorString: $splitString")

    val result = evaluateRPN(splitString.convertToRPN().also { Log.d(TAG, "RPNForm: $it") })
    Log.d(TAG, "result: $result")
    return result
}



private fun isNextNumberOrMinus(input: String, id: Int): Boolean {
    return if (id < input.lastIndex) {
        input[id + 1] in '0'..'9' || input[id + 1] == Operands.MINUS.sign
    } else false
}

private fun String.parsePercents(): MutableList<String> {
    val numbers = mutableListOf<String>()
    this.splitByOperators().forEach {
        val firstPercentId = it.indexOf(Symbols.PERCENT.value)
        numbers.add(
            if (firstPercentId > 0) {
                (it
                    .substring(
                        0, firstPercentId
                    ).toDouble() / (100.0.pow((it.length - firstPercentId).toDouble())
                        )).toString()
            } else
                it
        )
    }
    Log.d(TAG, "parsePercents: $numbers")
    return numbers
}

fun isOperand(char: Char): Boolean {
    Operands.values().forEach { if (it.sign == char) return true }
    return false
}

private fun String.splitByOperators(): MutableList<String> {
    val splitString = mutableListOf<String>()
    var buffer: String? = null
    var operand = false
    for (char in this) {
        if (isOperand(char)) {
            if (!buffer.isNullOrEmpty()) {
                splitString.add(buffer)
            }
            splitString.add(char.toString())
            buffer = ""
            operand = true
        }
        if (!operand) {
            if (buffer != null)
                buffer += char
            else
                buffer = char.toString()
        }
        operand = false
    }

    if (buffer != null) {
        if (buffer.isNotEmpty()) {
            splitString.add(buffer)
        } else {
            when (splitString[splitString.lastIndex].getOrNull(0)) {
                Operands.PlUS.sign, Operands.MINUS.sign -> splitString.add("0")
                Operands.MULTIPLY.sign, Operands.DIVISION.sign -> splitString.add("1")
            }
        }
    }

    Log.d(TAG, "splitByOperators: $splitString")
    return splitString
}
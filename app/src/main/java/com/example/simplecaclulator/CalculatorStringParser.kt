package com.example.simplecaclulator

import android.util.Log
import java.lang.IllegalArgumentException
import kotlin.math.pow

fun parseCalculatorString(input: String): Double {
    var workingString = ""
    //Add multiply signs after percent signs if needed
    for (id in input.indices) {
        workingString += (input[id])
        if (input[id] == Operands.PERCENT.sign && checkNext(input, id))
            workingString += (Operands.MULTIPLY.sign)
    }
    //split string by operators and parse percent signs
    val splitString: MutableList<String> = workingString.parsePercents()
    Log.d(TAG, "parseCalculatorString: $splitString")


    val result = evaluate(splitString)
    Log.d(TAG, "result: $result")
    return result
}

fun evaluate(operandsAndOperators: MutableList<String>): Double {
    /*operandsAndOperators.forEachIndexed { index, s ->
        when (s) {

            Operands.MINUS.sign.toString() -> {
                if (isOperand(operandsAndOperators[index - 1][0])) {
                    operandsAndOperators[index + 1] = s + operandsAndOperators[index + 1]
                }
            }

        }
    }*/

    operandsAndOperators.apply {
        forEachIndexed { index, s ->
            if (s == Operands.MINUS.sign.toString()) {
                if (operandsAndOperators.getOrNull(index - 1)?.get(0) in '0'..'9') {
                    return@evaluate evaluate(
                        operandsAndOperators.subList(
                            0,
                            index
                        )
                    ) - evaluate(operandsAndOperators.subList(index + 1, operandsAndOperators.size))
                } else {
                    operandsAndOperators[index + 1] =
                        operandsAndOperators[index] + operandsAndOperators[index + 1]
                    return@evaluate evaluate(
                        (operandsAndOperators.subList(
                            0,
                            index
                        ) + operandsAndOperators.subList(
                            index + 1,
                            operandsAndOperators.size
                        )) as MutableList<String>
                    )
                }
            }
        }
        forEachIndexed { index, s ->
            if (s == Operands.PlUS.sign.toString()) {
                return@evaluate evaluate(
                    operandsAndOperators.subList(
                        0,
                        index
                    )
                ) + evaluate(operandsAndOperators.subList(index + 1, operandsAndOperators.size))
            }
        }
        forEachIndexed { index, s ->
            if (s == Operands.MULTIPLY.sign.toString()) {
                return@evaluate evaluate(
                    operandsAndOperators.subList(
                        0,
                        index
                    )
                ) * evaluate(operandsAndOperators.subList(index + 1, operandsAndOperators.size))
            }
        }
        forEachIndexed { index, s ->
            if (s == Operands.DIVISION.sign.toString()) {
                return@evaluate evaluate(
                    operandsAndOperators.subList(
                        0,
                        index
                    )
                ) / evaluate(operandsAndOperators.subList(index + 1, operandsAndOperators.size))
            }
        }
    }
    if (operandsAndOperators.size == 1) {
        return operandsAndOperators[0].toDouble()
    }

    throw IllegalArgumentException()
}

private fun checkNext(input: String, id: Int): Boolean {
    return if (id < input.lastIndex) {
        input[id + 1] in '0'..'9' || input[id + 1] == Operands.MINUS.sign
    } else false
}

private fun String.parsePercents(): MutableList<String> {
    val numbers = mutableListOf<String>()
    this.splitByOperators().forEach {
        val firstPercentId = it.indexOf(Operands.PERCENT.sign)
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
    Operands.values()
        .forEach { if (it.sign == char && it.sign != Operands.PERCENT.sign) return true }
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
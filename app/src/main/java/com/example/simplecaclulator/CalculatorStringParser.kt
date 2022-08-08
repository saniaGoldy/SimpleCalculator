package com.example.simplecaclulator

import android.util.Log
import kotlin.math.pow

class CalculatorStringParser {
    fun parseCalculatorString(input: String): Double {
        var workingString = ""
        //Add multiply signs after percent signs if needed
        for (id in input.indices) {
            workingString += (input[id])
            if (input[id] == Symbols.PERCENT.value && isNextNumberOrMinus(input, id))
                workingString += (Operands.MULTIPLY.sign)
        }

        if (workingString[0] == Operands.MINUS.sign) {
            workingString = "0$workingString"
        }

        return RPNProcessor().evaluate(workingString)
    }


    private fun isNextNumberOrMinus(input: String, id: Int): Boolean {
        return if (id < input.lastIndex) {
            input[id + 1] in '0'..'9' || input[id + 1] == Operands.MINUS.sign
        } else false
    }
}
package com.example.simplecaclulator

import android.util.Log
import java.util.*
import kotlin.math.pow

const val openingBracket = "("
const val closingBracket = ")"

class RPNProcessor {
    fun evaluate(workingString: String): Double {
        //split string by operators and parse percent signs
        val splitString: MutableList<String> = workingString.parsePercents()
        Log.d(TAG, "parseCalculatorString: $splitString")
        val result = evaluateRPN(splitString.convertToRPN().also { Log.d(TAG, "RPNForm: $it") })
        Log.d(TAG, "result: $result")
        return result
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

    private fun String.splitByOperators(): MutableList<String> {
        val splitString = mutableListOf<String>()
        var buffer: String? = ""
        var operand = false
        this.forEachIndexed { index, char ->
            if (isOperand(char) && index > 0) {
                if (!buffer.isNullOrEmpty()) {
                    splitString.add(buffer!!)
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
            if (buffer!!.isNotEmpty()) {
                splitString.add(buffer!!)
            }
        }

        when (splitString[splitString.lastIndex].getOrNull(0)) {
            Operands.PlUS.sign, Operands.MINUS.sign -> splitString.add("0")
            Operands.MULTIPLY.sign, Operands.DIVISION.sign -> splitString.add("1")
        }

        Log.d(TAG, "splitByOperators: $splitString")
        return splitString
    }


    private fun evaluateRPN(tokens: MutableList<String>): Double {

        // Initialize the stack and the variable
        val stack = Stack<String>()
        var x: Double
        var y: Double
        var result: String
        var choice: String
        var value: Double
        val p = ""

        // Iterating to the each character
        // in the array of the string
        tokens.forEach {
            // If the character is not the special character
            // ('+', '-' ,'*' , '/')
            // then push the character to the stack
            choice =
                if (!isOperand(it[0])) {
                    stack.push(it)
                    return@forEach
                } else {
                    // else if the character is the special
                    // character then use the switch method to
                    // perform the action
                    it
                }
            when (choice[0]) {
                Operands.PlUS.sign -> {

                    // Performing the "+" operation by poping
                    // put the first two character
                    // and then again store back to the stack
                    x = stack.pop().toDouble()
                    y = stack.pop().toDouble()
                    value = x + y
                    result = p + value
                    stack.push(result)
                }
                Operands.MINUS.sign -> {

                    // Performing the "-" operation by poping
                    // put the first two character
                    // and then again store back to the stack
                    x = stack.pop().toDouble()
                    y = stack.pop().toDouble()
                    value = y - x
                    result = p + value
                    stack.push(result)
                }
                Operands.MULTIPLY.sign -> {

                    // Performing the "*" operation
                    // by poping put the first two character
                    // and then again store back to the stack
                    x = stack.pop().toDouble()
                    y = stack.pop().toDouble()
                    value = x * y
                    result = p + value
                    stack.push(result)
                }
                Operands.DIVISION.sign -> {

                    // Performing the "/" operation by poping
                    // put the first two character
                    // and then again store back to the stack
                    x = stack.pop().toDouble()
                    y = stack.pop().toDouble()
                    value = y / x
                    result = p + value
                    stack.push(result)
                }
                else -> return@forEach
            }
        }

        // Method to convert the String to integer
        return stack.pop().toDouble()
    }

    fun MutableList<String>.convertToRPN(): MutableList<String> {
        val result = ArrayList<String>()
        if (this.isEmpty()) {
            return result
        }
        val opStack = Stack<String>()
        this.forEach { token ->
            if (isNumber(token)) {
                result.add(token)
            } else if (token == openingBracket) {
                opStack.push(token)
            } else if (token == closingBracket) {
                while (opStack.peek() != openingBracket) {
                    result.add(opStack.pop())
                }
                opStack.pop()
            } else {
                while (opStack.isNotEmpty() && getPriority(opStack.peek()) >= getPriority(token)) {
                    result.add(opStack.pop())
                }
                opStack.push(token)
            }
        }
        while (!opStack.isEmpty()) {
            result.add(opStack.pop())
        }
        return result
    }

    private fun isNumber(token: String): Boolean {
        return Character.isDigit(token[0])
    }

    fun isOperand(char: Char): Boolean {
        Operands.values().forEach { if (it.sign == char) return true }
        return false
    }

    private fun getPriority(op: String): Int {
        return when (op) {
            openingBracket -> {
                0
            }
            Operands.PlUS.sign.toString(), Operands.MINUS.sign.toString() -> {
                1
            }
            else -> {
                2
            }
        }
    }
}
package com.example.simplecaclulator

import java.util.*

fun evaluateRPN(tokens: MutableList<String>): Double {

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
    for (i in tokens.indices) {

        // If the character is not the special character
        // ('+', '-' ,'*' , '/')
        // then push the character to the stack
        choice =
            if (!isOperand(tokens[i][0])) {
                stack.push(tokens[i])
                continue
            } else {
                // else if the character is the special
                // character then use the switch method to
                // perform the action
                tokens[i]
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
            else -> continue
        }
    }

    // Method to convert the String to integer
    return stack.pop().toDouble()
}
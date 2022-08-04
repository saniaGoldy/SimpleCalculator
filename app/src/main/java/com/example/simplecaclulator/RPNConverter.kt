package com.example.simplecaclulator

import java.util.*

const val openingBracket = "("
const val closingBracket = ")"

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

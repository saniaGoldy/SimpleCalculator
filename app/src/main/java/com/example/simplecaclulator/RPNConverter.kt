package com.example.simplecaclulator

import java.util.*

fun convertToRPN(expression: MutableList<String>): ArrayList<String> {
    val result = ArrayList<String>()
    if (expression.isEmpty()) {
        return result
    }
    val opStack = Stack<String>()
    for (token in expression) {
        if (isNumber(token)) {
            result.add(token)
        } else if (token == "(") {
            opStack.push(token)
        } else if (token == ")") {
            while (opStack.peek() != "(") {
                result.add(opStack.pop())
            }
            opStack.pop()
        } else {
            while (!opStack.isEmpty() && getPriority(opStack.peek()) >= getPriority(token)) {
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
        "(" -> {
            0
        }
        "+", "-" -> {
            1
        }
        else -> {
            2
        }
    }
}

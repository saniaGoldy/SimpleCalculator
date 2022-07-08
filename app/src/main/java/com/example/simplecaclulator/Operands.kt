package com.example.simplecaclulator

enum class Operands(val sign: Char) {
    MINUS('-'),
    PlUS('+'),
    MULTIPLY('×'),
    DIVISION('÷'),
    POWER('^'),
    PERCENT('%')
}
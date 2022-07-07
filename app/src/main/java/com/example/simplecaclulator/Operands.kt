package com.example.simplecaclulator

import android.content.res.Resources

enum class Operands(val sign: Char) {
    MINUS('-'),
    PlUS('+'),
    MULTIPLY('×'),
    DIVISION('÷'),
    POWER('^'),
    PERCENT('%')
}
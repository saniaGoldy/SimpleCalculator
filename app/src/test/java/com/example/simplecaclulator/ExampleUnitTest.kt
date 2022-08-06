package com.example.simplecaclulator

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var engine: CalculatorStringParser
    private lateinit var rpnProcessor: RPNProcessor

    @Before
    fun setUp() {
        engine = mockk()
        rpnProcessor = mockk()
    }

    @Test
    fun minusAtStartOfExpression() {
        every { engine.parseCalculatorString(Operands.MINUS.sign.toString()) } returns 0.0
    }

    @Test
    fun division() {
        every { engine.parseCalculatorString("25${Operands.DIVISION.sign}5") } returns 5.0
    }

    @Test
    fun divisionByZero() {
        every { engine.parseCalculatorString("25${Operands.DIVISION.sign}0") } returns Double.POSITIVE_INFINITY
        every { engine.parseCalculatorString("-25${Operands.DIVISION.sign}0") } returns Double.NEGATIVE_INFINITY
    }

    @Test
    fun subtraction() {
        every { engine.parseCalculatorString("-25${Operands.MINUS.sign}40") } returns -65.0
        every { engine.parseCalculatorString("25${Operands.MINUS.sign}40") } returns -15.0
    }

    @Test
    fun addition() {
        every { engine.parseCalculatorString("-25${Operands.PlUS.sign}30") } returns 5.0
    }

    @Test
    fun multiplication() {
        every { engine.parseCalculatorString("-25${Operands.MULTIPLY.sign}0") } returns 0.0
        every { engine.parseCalculatorString("-25${Operands.MULTIPLY.sign}5") } returns -125.0
    }

    @Test
    fun tracingSign() {
        coEvery {
            engine.parseCalculatorString("-25${Operands.DIVISION.sign}5${Operands.PlUS.sign}")
            engine.parseCalculatorString("-25${Operands.DIVISION.sign}5${Operands.MINUS.sign}")
            engine.parseCalculatorString("-25${Operands.DIVISION.sign}5${Operands.MULTIPLY.sign}")
            engine.parseCalculatorString("-25${Operands.DIVISION.sign}5${Operands.DIVISION.sign}")
        } returns -5.0
    }

}
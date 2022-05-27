package com.mathalgo.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.nio.file.Files.delete
import java.nio.file.Files.setPosixFilePermissions

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())
    private set

    fun onAction(action: CalculatorAction) {
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDeletion()
        }
    }

    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(operation = null)
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
        return
    }
    // We're doing copies here because the state is not mutable
    // and replacing the state with another one means that we trigger a recomposition

    private fun performCalculation() {
        var number1 = 0.0
        var number2 = 0.0
        if (state.number1.isNotBlank()) number1 = state.number1.toDouble()
        if (state.number2.isNotBlank()) number2 = state.number2.toDouble()
        val result = when(state.operation) {
            is CalculatorOperation.Add -> number1 + number2
            is CalculatorOperation.Subtract -> number1 - number2
            is CalculatorOperation.Multiply -> number1 * number2
            is CalculatorOperation.Divide -> number1 / number2
            else -> number1
        }
        state = state.copy(
            number1 = result.toString().take(15),
            number2 = "",
            operation = null
        )
        return
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }
    private fun CharSequence.doesNotContainDecimal(): Boolean {
        return !isBlank() && !this.contains('.')
    }
    private fun enterDecimal() {
        when {
            state.number2.isNotBlank() -> {
                if (state.number2.doesNotContainDecimal()) {
                    state = state.copy(
                        number2 = state.number2 + "."
                    )
                }
            }
            state.operation != null -> { // do nothing
            }
            state.number1.isNotBlank() -> {
                if (state.number1.doesNotContainDecimal()) {
                    state = state.copy(
                        number1 = state.number1 + "."
                    )
                }
            }
        }
        return
    }

    private fun enterNumber(number: Int) {
        if (state.number1.length + state.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        state = if (state.operation == null) {
            state.copy(
                number1 = state.number1 + number
            )
        } else {
            state.copy(
                number2 = state.number2 + number
            )
        }
        return
    }

    companion object {
        private const val MAX_NUM_LENGTH = 7
    }
}
package com.mathalgo.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.nio.file.Files.delete

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
        TODO("Not yet implemented")
        return
    }

    private fun performCalculation() {
        TODO("Not yet implemented")
        return
    }

    private fun enterOperation(operation: CalculatorOperation) {
        return
    }

    private fun enterDecimal() {
        TODO("Not yet implemented")
        return
    }

    private fun enterNumber(number: Int) {
        return
    }
}
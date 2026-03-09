package com.angelodev.helpapp.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Locale

object ElectricalCalculator {
    private const val VOLTAGE = 230.0

    fun computeVA(quantity: Int, watts: Double): Double = quantity * watts

    fun computeAmpere(va: Double): Double = va / VOLTAGE

    fun computeTotalVA(vaValues: List<Double>): Double = vaValues.sum()

    fun computeTotalAmpere(ampereValues: List<Double>): Double = ampereValues.sum()

    fun computeDemandFactor(percent: Int): Double = percent / 100.0

    fun findHighestAmpere(ampereValues: List<Double>): Double {
        val highest = ampereValues.maxOrNull() ?: 0.0
        val df = DecimalFormat("#.##", java.text.DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(highest).toDouble()
    }

    /**
     * Distributes circuits into Phase A (odd indices) and Phase B (even indices)
     * Returns Pair(phaseAValues, phaseBValues)
     */
    fun distributePhases(ampereValues: List<Double>): Pair<List<Double>, List<Double>> {
        val phaseA = mutableListOf<Double>()
        val phaseB = mutableListOf<Double>()
        ampereValues.forEachIndexed { index, value ->
            if (index % 2 == 0) phaseA.add(value) else phaseB.add(value)
        }
        return Pair(phaseA, phaseB)
    }

    fun computePhaseTotal(values: List<Double>): Double = values.sum()

    /**
     * Computes top values for the schedule
     * topOneAndTwo = highestA * demandFactor
     */
    fun computeTopOneAndTwo(highestA: Double, demandFactor: Double): Double =
        highestA * demandFactor

    fun computeTopThreeAndFour(totalA: Double, demandFactor: Double): Double =
        totalA * demandFactor

    fun computeUnderOneAndTwo(topOneAndTwo: Double): Double = topOneAndTwo

    fun computeUnderThreeAndFour(topThreeAndFour: Double): Double = topThreeAndFour

    fun formatValue(value: Double): String {
        val df = DecimalFormat("#.##", java.text.DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(value)
    }

    /**
     * Per PEC Section 4.30.6.1: Motor branch circuit conductors
     * must have ampacity not less than 125% of motor full-load current.
     */
    fun motorConductorMinAmpacity(motorFLC: Double): Double = motorFLC * 1.25

    /**
     * Per PEC Section 4.30.7.2: Motor branch circuit overcurrent protection
     * for inverse time breakers shall not exceed 250% of motor FLC.
     */
    fun motorMaxBreakerSize(motorFLC: Double): Double = motorFLC * 2.5
}

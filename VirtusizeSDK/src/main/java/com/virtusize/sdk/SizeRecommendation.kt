package com.virtusize.sdk

import java.math.RoundingMode

class SizeRecommendation {

    interface SizeRecommendationCallback {
        fun onSuccess(result: SizeRecommendationData)
        fun onError(errorMessage: String)
    }

    fun get(heightInCM: Double = 0.0, weightInKg: Double = 0.0, callback: SizeRecommendationCallback) {
        if (inputAreValid(height = heightInCM, weight = weightInKg)) {
            val heightInMeters = heightInCM / 100
            val bmi = calculateBMI(height = heightInMeters, weight = weightInKg)
            val roundedBMI = bmi.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

            val recommendedSize: RecommendedSize = if (roundedBMI < 18.5) {
                RecommendedSize.Small
            } else if (roundedBMI in 18.5..24.9) {
                RecommendedSize.Medium
            } else if (roundedBMI in 25.0..29.9) {
                RecommendedSize.Large
            } else if (roundedBMI >= 30) {
                RecommendedSize.ExtraLarge
            } else {
                RecommendedSize.Unknown
            }

            val sizeRecommendationData = SizeRecommendationData(bmi = roundedBMI, recommendedSize = recommendedSize)
            VirtusizeSDK.instance.lastBMIResult = sizeRecommendationData.bmi
            VirtusizeSDK.instance.lastRecommendedSizeResult = sizeRecommendationData.recommendedSize
            callback.onSuccess(result = sizeRecommendationData)
        } else {
            //TODO: improve error handling
            callback.onError(errorMessage = "Input are invalid")
        }
    }

    private fun inputAreValid(height: Double = 0.0, weight: Double = 0.0): Boolean {
        return height > 0.0 && weight > 0.0
    }

    private fun calculateBMI(height: Double = 0.0, weight: Double = 0.0): Double {
        require(height > 0.0) { "Height must be greater than 0." }
        require(weight > 0.0) { "Weight must be greater than 0." }

        val bmi = weight / (height * height)
        return bmi
    }

}

data class SizeRecommendationData(val bmi: Double, val recommendedSize: RecommendedSize)
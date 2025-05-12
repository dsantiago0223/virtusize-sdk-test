package com.virtusize.sdk

class VirtusizeSDK private constructor() {

    internal var lastBMIResult: Double = 0.0
    internal var lastRecommendedSizeResult: RecommendedSize = RecommendedSize.Unknown

    fun getLastBMIResult(): Double {
        return lastBMIResult
    }

    fun getLastRecommendationResult(): RecommendedSize {
        return lastRecommendedSizeResult
    }

    companion object {
        val instance = VirtusizeSDK()
    }
}
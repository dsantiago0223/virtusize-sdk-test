package com.virtusize.app

import com.virtusize.sdk.SizeRecommendation
import com.virtusize.sdk.SizeRecommendation.SizeRecommendationCallback
import com.virtusize.sdk.SizeRecommendationData
import org.junit.Test
import org.junit.Assert.*

class SizeRecommendationTest {

    @Test
    fun getSizeRecommendation_Success() {
        SizeRecommendation().get(heightInCM = 180.0, weightInKg = 90.0, object:
            SizeRecommendationCallback {
            override fun onSuccess(result: SizeRecommendationData) {
                assertEquals("L", result.recommendedSize.toString())
            }

            override fun onError(errorMessage: String) {}
        })
    }

    @Test
    fun getSizeRecommendation_Error() {
        SizeRecommendation().get(heightInCM = 0.0, weightInKg = 0.0, object:
            SizeRecommendationCallback {
            override fun onSuccess(result: SizeRecommendationData) {}

            override fun onError(errorMessage: String) {
                assertEquals("Input are invalid", errorMessage)
            }
        })
    }

}
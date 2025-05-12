# virtusize-sdk-test

- Test SDK that gives size recommendation by providing height (cm) and weight (kg)
- The size recommendation will be calculated based on BMI (kg / m2), with the following BMI ranges corresponding to the size:

...
S = <18.5
M = 18.5-24.9
L = 25-29.9
XL = >=30
...

## Usage 

...
private fun getSizeRecommendation(height: Double, weight: Double) {
Log.d(TAG, "height: $height")
Log.d(TAG, "weight: $weight")
SizeRecommendation().get(heightInCM = height, weightInKg = weight, object:  SizeRecommendationCallback {
override fun onSuccess(result: SizeRecommendationData) {
Log.d(TAG, "bmi: ${result.bmi}")
Log.d(TAG, "recommended size: ${result.recommendedSize}")
Log.d(TAG, "last bmi result: ${VirtusizeSDK.instance.getLastBMIResult()}")
Log.d(TAG, "last recommended size result: ${VirtusizeSDK.instance.getLastRecommendationResult()}")
showResult(result = result)
}

            override fun onError(errorMessage: String) {
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
...
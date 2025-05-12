# Virtusize SDK Test

- Test SDK that gives size recommendation by providing height (cm) and weight (kg).
- The size recommendation will be calculated based on BMI (kg / m2), with the following BMI ranges corresponding to the size:

```
S = <18.5
M = 18.5-24.9
L = 25-29.9
XL = >=30
```

## How to use

```
import com.virtusize.sdk. SizeRecommendation
import com.virtusize.sdk.SizeRecommendation.SizeRecommendationCallback
import com.virtusize.sdk.VirtusizeSDK
import com.virtusize.sdk.SizeRecommendationData

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

Output:
025-05-13 05:35:08.435 22775-22775 Virtusize               com.virtusize.app                    D  height: 180.0
2025-05-13 05:35:08.435 22775-22775 Virtusize               com.virtusize.app                    D  weight: 90.0
2025-05-13 05:35:08.438 22775-22775 Virtusize               com.virtusize.app                    D  bmi: 27.8
2025-05-13 05:35:08.438 22775-22775 Virtusize               com.virtusize.app                    D  recommended size: L
2025-05-13 05:35:08.438 22775-22775 Virtusize               com.virtusize.app                    D  last bmi result: 27.8
2025-05-13 05:35:08.438 22775-22775 Virtusize               com.virtusize.app                    D  last recommended size result: L
```

## How to add the library in your project

You can download the AAR File then add to your app>libs folder or download the module VirtusizeSDK and import directly in your project. If you use AAR file, just add this to app build.gradle file:

```gradle
dependencies {
    implementation fileTree(dir: "libs", include: ["*.aar"])
}
```
or grab from Maven

```gradle
dependencies {
    implementation("com.virtusize.sdk:virtusize-sdk:1.0.3")
}
```


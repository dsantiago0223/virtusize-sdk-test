package com.virtusize.app

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.virtusize.sdk.SizeRecommendation
import com.virtusize.sdk.SizeRecommendation.SizeRecommendationCallback
import com.virtusize.sdk.VirtusizeSDK
import com.virtusize.app.databinding.ActivityMainBinding
import com.virtusize.sdk.SizeRecommendationData
import java.util.Objects
import androidx.core.graphics.drawable.toDrawable

class MainActivity : AppCompatActivity() {

    private val TAG = "Virtusize"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recommendationButton.setOnClickListener {
            if (binding.heightEditText.text.toString().isNotEmpty() && binding.heightEditText.text.toString().isNotEmpty()) {
                getSizeRecommendation(height = binding.heightEditText.text.toString().toDouble(),
                    weight = binding.weightEditText.text.toString().toDouble())
            } else {
                Toast.makeText(this, "Please enter height or weight", Toast.LENGTH_SHORT).show()
            }
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTaskToBack(false)
            }
        })
    }

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

    private fun showResult(result: SizeRecommendationData) {
        val builder = MaterialDialog.Builder(this).customView(R.layout.dialog_result, false).cancelable(false)
        val dialog = builder.build()
        Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        val view = dialog.customView!!

        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val messageTextView = view.findViewById<TextView>(R.id.messageTextView)
        val title = "Your Recommended Size: ${result.recommendedSize}"
        val message = "Based on your info, size ${result.recommendedSize} is recommended."
        titleTextView.text = title
        messageTextView.text = message

        val okButton = view.findViewById<Button>(R.id.okButton)
        okButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

}
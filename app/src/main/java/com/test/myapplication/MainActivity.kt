package com.test.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.test.myapplication.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedId: Int? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{
            if(selectedId==null){
                Toast.makeText(this,"Please Select An Option",Toast.LENGTH_SHORT).show()
            }
            else{calculateTip()}
        }
        binding.costOfService.setOnKeyListener { v, keyCode, _ -> handleKeyEvent(v,keyCode) }
        binding.tipOptions.setOnCheckedChangeListener { _, checkedId -> selectedId=checkedId }
    }

    private fun calculateTip() {
        val cost=binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if(cost==null){
            Toast.makeText(this,"Please Enter a Valid Number",Toast.LENGTH_SHORT).show()
            return
        }

        val tipPercentage=when(selectedId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip=tipPercentage*cost
        val roundUp=binding.roundUpSwitch.isChecked
        if(roundUp){
            tip=kotlin.math.ceil(tip)
        }
        val formattedTip=NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text=getString(R.string.tip_amount,formattedTip)

    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}
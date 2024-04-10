package com.example.paymentgatewaysample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() ,PaymentResultWithDataListener {

    private var amountEdt : EditText ?= null
    private var paybutton : Button ?= null
    private var payStatus : TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         amountEdt = findViewById(R.id.editText)
         paybutton = findViewById(R.id.button)
         payStatus = findViewById(R.id.textView)


        paybutton?.setOnClickListener {
             if (amountEdt?.text?.isNotEmpty() == true && amountEdt?.text != null){
                 val amount  : Int =amountEdt?.text.toString().toInt()
                 payAmount(amount)
             }else{
                 Toast.makeText(this,"Please Enter the amount" ,Toast.LENGTH_SHORT).show()

             }

        }
    }

    private fun payAmount(amount: Int) {

        val co = Checkout()
        co.setKeyID("rzp_test_ovsesivy4zNpo6")
        co.setImage(R.drawable.baseline_align_vertical_bottom_24)


        try {
            val orderRequest = JSONObject()
            orderRequest.put("name", "Vasantha Manikandan")
            orderRequest.put("Description", "Please pay the total amount for delivery")
            orderRequest.put("theme.color", R.color.black)
            orderRequest.put("currency", "INR")
            orderRequest.put("amount", amount * 100)

            val retryRequest = JSONObject()
            retryRequest.put("enabled", true)
            retryRequest.put("max", 4)
            retryRequest.put("retry", retryRequest)

            co.open(this@MainActivity, orderRequest)

        }catch (e :Exception){
            Toast.makeText(this,"${e.message}" ,Toast.LENGTH_SHORT).show()
            e.printStackTrace()


        }


    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this,"Payment Error : $p1" ,Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        payStatus?.text = p0
        Toast.makeText(this,"Payment Success" ,Toast.LENGTH_SHORT).show()

    }

}
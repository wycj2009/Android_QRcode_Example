package com.example.android_qrcode_example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainTextviewName: TextView
    private lateinit var activityMainTextviewAddress: TextView
    private lateinit var activityMainTextviewResult: TextView
    private lateinit var activityMainButtonScan: Button

    private lateinit var qrScan: IntentIntegrator //QRcode scanner object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainTextviewName = findViewById(R.id.activity_main_textview_name)
        activityMainTextviewAddress = findViewById(R.id.activity_main_textview_address)
        activityMainTextviewResult = findViewById(R.id.activity_main_textview_result)
        activityMainButtonScan = findViewById(R.id.activity_main_button_scan)

        qrScan = IntentIntegrator(this) //intializing scan object

        activityMainButtonScan.setOnClickListener {
            //scan option
            qrScan.setPrompt("Scanning...")
            //qrScan.setOrientationLocked(false)
            qrScan.captureActivity = (CaptureActivity::class.java)
            qrScan.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) { //QRcode 가 없는 경우
            if (result.contents == null) {
                Toast.makeText(this@MainActivity, "취소", Toast.LENGTH_SHORT).show()
            } else { //QRcode 결과가 있는 경우
                Toast.makeText(this@MainActivity, "스캔완료", Toast.LENGTH_SHORT).show()
                try {
                    //data를 json으로 변환
                    val obj = JSONObject(result.contents)
                    activityMainTextviewName.text = obj.getString("name")
                    activityMainTextviewAddress.text = obj.getString("address")
                } catch (e: JSONException) {
                    e.printStackTrace()
                    //Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show()
                    activityMainTextviewResult.text = result.contents
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
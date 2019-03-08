package com.example.routerdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gecng.routeannotation.Route

@Route(path = "app/mainAct")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

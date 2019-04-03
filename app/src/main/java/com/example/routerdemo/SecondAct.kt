package com.example.routerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gecng.routeannotation.Route


@Route(path = "app/second")
class SecondAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


    }

}
package com.gmbuyi.temno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.account_layout)
       // SingUpDialog(this, R.style.TransparentDialog).show()

    }



}
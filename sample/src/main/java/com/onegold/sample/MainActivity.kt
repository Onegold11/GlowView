package com.onegold.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onegold.glbutton.ui.view.base.GlButton
import com.onegold.glbutton.ui.view.base.GlLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<GlButton>(R.id.btn_round_button)
        val button2 = findViewById<GlButton>(R.id.btn_cut_button)
        val button3 = findViewById<GlLayout>(R.id.btn_round_layout)
        val button4 = findViewById<GlLayout>(R.id.btn_cut_layout)

        button1.setOnClickListener {
            startActivity(Intent(this, RoundButtonActivity::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this, CutButtonActivity::class.java))
        }
        button3.setOnClickListener {
            startActivity(Intent(this, RoundLayoutActivity::class.java))
        }
        button4.setOnClickListener {
            startActivity(Intent(this, CutLayoutActivity::class.java))
        }
    }
}
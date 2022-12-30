package com.lucas.loteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.lucas.loteria.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var randomList = emptyList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonGenerate.setOnClickListener(this)
        binding.buttonShare.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_generate) {
            generate()
        } else if (view.id == R.id.button_share) {
            share()
        }
    }

    private fun generate() {
        val n: MutableSet<Int> = mutableSetOf()
        while (n.size < 6) {
            n.add((1..60).random())
        }
        randomList = n.toList().sorted()
        binding.textN1.text = randomList.elementAt(0).toString()
        binding.textN2.text = randomList.elementAt(1).toString()
        binding.textN3.text = randomList.elementAt(2).toString()
        binding.textN4.text = randomList.elementAt(3).toString()
        binding.textN5.text = randomList.elementAt(4).toString()
        binding.textN6.text = randomList.elementAt(5).toString()

    }

    private fun share() {
        if (randomList.isEmpty()) {
            Toast.makeText(this, R.string.validate_list, Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                resources.getString(
                    R.string.play_message,
                    randomList.toString().substring(1, 23)
                )
            )
            startActivity(intent)
        }
    }
}
package com.steve.app0000

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val blue = Color.rgb(8, 123, 255)
    private val red = Color.rgb(255, 32, 45)
    private val dark = Color.rgb(5, 7, 13)
    private val panel = Color.rgb(16, 23, 37)
    private var selectedMode = "Romantique ❤️"

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun background(color: Int, radius: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
        }
    }

    private fun text(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
        }
    }

    private fun addSpace(parent: LinearLayout, height: Int) {
        parent.addView(View(this), LinearLayout.LayoutParams(
            1, dp(height)
        ))
    }

    private fun button(
        value: String,
        color: Int,
        action: () -> Unit
    ): TextView {
        return text(value, 15f, Color.WHITE, true).apply {
            gravity = Gravity.CENTER
            setPadding(dp(12), dp(15), dp(12), dp(15))
            background = background(color, 14)
            setOnClickListener { action() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = dark
        window.navigationBarColor = dark

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(dark)
            setPadding(dp(20), dp(20), dp(20), dp(20))
        }

        val scroll = ScrollView(this)
        scroll.addView(root)
        setContentView(scroll)

        val logo = ImageView(this).apply {
            setImageResource(R.drawable.ic_0000)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        root.addView(logo, LinearLayout.LayoutParams(dp(112), dp(112)).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        })

        addSpace(root, 8)

        root.addView(text("0000", 32f, blue, true).apply {
            gravity = Gravity.CENTER
        })

        root.addView(text(
            "TON ASSISTANT INTELLIGENT",
            11f, Color.LTGRAY, true
        ).apply {
            gravity = Gravity.CENTER
        })

        addSpace(root, 25)

        root.addView(text("CONNEXION DES APPLICATIONS", 13f, blue, true))

        addSpace(root, 12)

        root.addView(button("●  WhatsApp", Color.rgb(25, 110, 65)) {
            Toast.makeText(
                this, "Connexion WhatsApp : étape suivante",
                Toast.LENGTH_SHORT
            ).show()
        })

        addSpace(root, 10)

        root.addView(button("●  WhatsApp Business", Color.rgb(25, 110, 65)) {
            Toast.makeText(
                this, "Connexion WhatsApp Business : étape suivante",
                Toast.LENGTH_SHORT
            ).show()
        })

        addSpace(root, 25)

        root.addView(text("PERSONNALITÉ DE GEMINI", 13f, blue, true))

        addSpace(root, 12)

        val modes = arrayOf(
            "Romantique ❤️",
            "Drôle 😂",
            "Automatique 🧠"
        )

        val spinner = Spinner(this)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            modes
        )
        spinner.adapter = adapter

        spinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedMode = modes[position]
                }

                override fun onNothingSelected(
                    parent: android.widget.AdapterView<*>?
                ) {}
            }

        root.addView(spinner)

        addSpace(root, 22)

        root.addView(text("CONFIGURATION DU SERVEUR IA", 13f, blue, true))

        addSpace(root, 10)

        val server = EditText(this).apply {
            hint = "https://ton-serveur.onrender.com"
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)
            textSize = 14f
            setSingleLine(true)
            setPadding(dp(14), dp(14), dp(14), dp(14))
            background = background(panel, 12)
        }

        root.addView(server)

        addSpace(root, 10)

        root.addView(button("ENREGISTRER LE SERVEUR", blue) {
            getSharedPreferences("0000_CONFIG", MODE_PRIVATE)
                .edit()
                .putString("server_url", server.text.toString().trim())
                .apply()

            Toast.makeText(
                this, "Adresse enregistrée",
                Toast.LENGTH_SHORT
            ).show()
        })

        addSpace(root, 22)

        root.addView(button("PARAMÈTRES DE LA BULLE FLOTTANTE", red) {
            if (android.provider.Settings.canDrawOverlays(this)) {
                Toast.makeText(
                    this, "Autorisation de superposition active",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivity(
                    android.content.Intent(
                        android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                    ).apply {
                        data = android.net.Uri.parse("package:$packageName")
                    }
                )
            }
        })

        addSpace(root, 20)

        root.addView(text(
            "0000 V1.0.0  •  GEMINI AI",
            11f, Color.GRAY
        ).apply {
            gravity = Gravity.CENTER
        })
    }
}

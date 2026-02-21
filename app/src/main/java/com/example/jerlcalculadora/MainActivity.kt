package com.example.jerlcalculadora
import android.widget.Button
import android.widget.TextView
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var pantalla: TextView
    private var textoPantalla = ""
    private var numeroAnterior = 0
    private var operador = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        pantalla = findViewById(R.id.Pantalla)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonesNumeros = listOf(
            R.id.btn0 to "0",
            R.id.btn1 to "1",
            R.id.btn2 to "2",
            R.id.btn3 to "3",
            R.id.btn4 to "4",
            R.id.btn5 to "5",
            R.id.btn6 to "6",
            R.id.btn7 to "7",
            R.id.btn8 to "8",
            R.id.btn9 to "9"
        )

        val btnPlus = findViewById<Button>(R.id.btnMas)
        val btnMinus = findViewById<Button>(R.id.btnLess)
        val btnMul = findViewById<Button>(R.id.btnX)
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnDot = findViewById<Button>(R.id.Dot)
        val btnPop = findViewById<Button>(R.id.Pop)

        btnPlus.setOnClickListener { selectOperator("+") }
        btnMinus.setOnClickListener { selectOperator("-") }
        btnMul.setOnClickListener { selectOperator("*") }
        btnDivide.setOnClickListener { selectOperator("÷") }
        btnEqual.setOnClickListener { calcularResultado() }
        btnClear.setOnClickListener { clearScreen() }
        btnDot.setOnClickListener { addDecimal() }
        btnPop.setOnClickListener { deleteLast() }

        for ((id, valor) in botonesNumeros){
            findViewById<Button>(id).setOnClickListener {
                addNumber(valor)
            }
        }
    }
    private fun addNumber(numero: String) {
        if (textoPantalla == "0") {
            textoPantalla = numero
        } else {
            textoPantalla += numero
        }
        pantalla.text = textoPantalla
    }

    private fun selectOperator(op: String){
        if(textoPantalla.isNotEmpty()){
            numeroAnterior = textoPantalla.toDouble().toInt()
            operador = op
            textoPantalla = ""
                pantalla.text = "0"
        }
    }
    private fun calcularResultado() {
        if (textoPantalla.isEmpty()) return

        val numeroActual = textoPantalla.toFloat()
        val anterior = numeroAnterior.toFloat()
        var resultado = 0.0F

        when (operador) {
            "+" -> resultado = anterior + numeroActual
            "-" -> resultado = anterior - numeroActual
            "*" -> resultado = anterior * numeroActual
            "÷" -> resultado = anterior / numeroActual
        }

        pantalla.text = resultado.toString()
        textoPantalla = resultado.toString()
        operador = ""
    }

    private fun clearScreen(){
        textoPantalla = ""
        numeroAnterior = 0
        operador = ""
        pantalla.text = "0"
    }
    private fun addDecimal() {
        if (!textoPantalla.contains(".")) {
            textoPantalla = if (textoPantalla.isEmpty()) {
                "0."
            } else {
                textoPantalla + "."
            }
            pantalla.text = textoPantalla
        }
    }


    private fun deleteLast() {
        if (textoPantalla.isNotEmpty()) {
            textoPantalla = textoPantalla.dropLast(1)
            pantalla.text = if (textoPantalla.isEmpty()) "0" else textoPantalla
        }
    }


}
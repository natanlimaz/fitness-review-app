package co.natanlima.fitnessreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ImcActivity : AppCompatActivity() {

    lateinit var editWeight: EditText
    lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_imc_send)
        btnSend.setOnClickListener {
            if(!validate()){
                Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight: Int = editWeight.text.toString().toInt()
            val height: Int = editHeight.text.toString().toInt()

            val imcResult = calculateImc(weight, height)
            Log.d("Teste", "Resultado: $imcResult")

        }
    }

    private fun calculateImc(weight: Int, height: Int): Double{
        return weight / ((height / 100.0) * (height / 100.0))
    }

    private fun validate(): Boolean {
        // nao pode inserir valores nulos / vazio
        // nao pode inserir / comecar com 0
        return editWeight.text.toString().isNotEmpty() && editHeight.text.toString().isNotEmpty() && !editWeight.text.toString().startsWith("0") && !editHeight.text.toString().startsWith("0")
    }
}
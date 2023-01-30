package co.natanlima.fitnessreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.natanlima.fitnessreview.model.Calc

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

            val imcResponseId = imcResponse(imcResult)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, imcResult))
                .setMessage(imcResponseId)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    //aqui vai rodar depois do click
                }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()

                        dao.insert(Calc(type = "imc", res = imcResult))

                        runOnUiThread {
                            val intent = Intent(this@ImcActivity, ListCalcActivity::class.java)
                            intent.putExtra("type", "imc")
                            startActivity(intent)
                        }

                    }.start()

                }
                .create()
                .show()

            // ocultar janela de entrada do software -> ocultar o teclado
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    @StringRes
    private fun imcResponse(imc: Double): Int{
        return when {
            imc < 15.0 -> R.string.imc_severy_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
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
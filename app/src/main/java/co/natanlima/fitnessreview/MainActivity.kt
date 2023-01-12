package co.natanlima.fitnessreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var btnImc: LinearLayout // variavel de campo - escopo;campo da classe (que irei inicializar depos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnImc = findViewById(R.id.btn_imc)

        /*
            Intent -> intencao de voce abrir algum recurso do sistema operacional android (pode ser varias coisas: link/atividade/telefonema (serviços do sistema))
        * */
        btnImc.setOnClickListener {
            val i = Intent(this, ImcActivity::class.java) //ctx de execucao atividade corrente, classe q a gente quer inicializar
            startActivity(i)
        }
    }
}
package co.natanlima.fitnessreview

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //private lateinit var btnImc: LinearLayout // variavel de campo - escopo;campo da classe (que irei inicializar depos)
    private lateinit var recyclerViewMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.label_imc,
                color = Color.GREEN
            )
        )

        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.baseline_visibility_24,
                textStringId = R.string.label_tmb,
                color = Color.YELLOW
            )
        )

        // 1) o layout xml
        // 2) a onde a recyclerView vai aparecer (tela principal, tela cheia)
        // 3) logica - conectar o xml da celula DENTRO do recyclerView + a sua quantidade de elementos dinâmicos
//        val adapter = MainAdapter(mainItems, object : OnItemClickListener {
//            // METODO 2, IMPL VIA OBJETO ANÔNIMO
//            override fun onClick(id: Int) {
//                when(id) {
//                    1 -> {
//                        val intent = Intent(this@MainActivity, ImcActivity::class.java)
//                        startActivity(intent)
//                    }
//                    2 -> {
//                        // Abrir uma outra activity
//                    }
//                }
//            }
//
//        })

        val adapter = MainAdapter(mainItems) { id ->
            when(id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this@MainActivity, TmbActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        recyclerViewMain = findViewById(R.id.rv_main) // referencia da RecyclerView
        recyclerViewMain.adapter = adapter

        recyclerViewMain.layoutManager = GridLayoutManager(this, 2) // COMPORTAMENTO DA NOSSA LISTAGEM

//        btnImc = findViewById(R.id.btn_imc)
//
//        /*
//            Intent -> intencao de voce abrir algum recurso do sistema operacional android (pode ser varias coisas: link/atividade/telefonema (serviços do sistema))
//        * */
//        btnImc.setOnClickListener {
//            val i = Intent(this, ImcActivity::class.java) //ctx de execucao atividade corrente, classe q a gente quer inicializar
//            startActivity(i)
//        }
    }

    // METODO 1, USANDO IMPL INTERFACE VIA ACTIVITY
//    override fun onClick(id: Int) {
//        when(id) {
//            1 -> {
//                val intent = Intent(this, ImcActivity::class.java)
//                startActivity(intent)
//            }
//            2 -> {
//                // Abrir uma outra activity
//            }
//        }
//    }

    private inner class MainAdapter(private val mainItems: List<MainItem>, private val onItemClickListener: (Int) -> Unit) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        // 1 - Qual é o layout XML da célula específica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

            val view = layoutInflater.inflate(R.layout.main_item, parent, false)

            return MainViewHolder(view)

        }

        // 2 - Disparado toda vez que houver uma rolagem na tela e for necessário trocar o conteúdo da célula
        override fun getItemCount(): Int {
            return mainItems.size
        }

        // 3 - Informar quantas células essa listagem terá
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        // é a classe da célula em si!!!
        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }

    }

}
package co.natanlima.fitnessreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.natanlima.fitnessreview.model.Calc
import co.natanlima.fitnessreview.model.DateConverter
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Locale

class ListCalcActivity : AppCompatActivity(), OnListClickListener {

    private lateinit var adapter: ListCalcAdapter
    private lateinit var listCalc: MutableList<Calc>

    private lateinit var recyclerViewListCalc: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        listCalc = mutableListOf<Calc>()

        adapter = ListCalcAdapter(listCalc, this)
        recyclerViewListCalc = findViewById(R.id.rv_list_calc)
        recyclerViewListCalc.layoutManager = LinearLayoutManager(this)
        recyclerViewListCalc.adapter = adapter

        val type = intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        // Buscar no banco esse tipo
        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                listCalc.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()

    }

    override fun onClick(id: Int, type: String) {
        when(type) {
            "imc" -> {
                val intent = Intent(this, ImcActivity::class.java)
                intent.putExtra("updateId", id)
                startActivity(intent)
            }
            "tmb" -> {
                val intent = Intent(this, TmbActivity::class.java)
                intent.putExtra("updateId", id)
                startActivity(intent)
            }
        }
        finish()
    }

    override fun onLongClick(position: Int, calc: Calc) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton(android.R.string.cancel) { dialog, which ->
            }
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()

                    val response = dao.delete(calc)

                    if(response > 0) {
                        runOnUiThread {
                            listCalc.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        }
                    }
                }.start()
            }
            .create()
            .show()
    }

    private inner class ListCalcAdapter(private val calcItems: List<Calc>, private val listener: OnListClickListener) : RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ListCalcViewHolder(view)
        }

        override fun getItemCount(): Int {
            return calcItems.size
        }

        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val itemCurrent = calcItems[position]
            holder.bind(itemCurrent)
        }

        private inner class ListCalcViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bind(item: Calc) {
                val tv = itemView as TextView

                val response = item.res

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")) // conversor de data para padrao ptbr
                val date = sdf.format(item.createdDate)

                tv.text = getString(R.string.list_response, response, date)

                tv.setOnLongClickListener {
                    listener.onLongClick(adapterPosition, item)
                    true
                }

                tv.setOnClickListener {
                    listener.onClick(item.id, item.type)
                }
            }

        }
    }

}
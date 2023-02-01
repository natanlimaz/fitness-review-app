package co.natanlima.fitnessreview

import co.natanlima.fitnessreview.model.Calc

interface OnListClickListener {
    fun onClick(id: Int, type: String)
    fun onLongClick(position: Int, calc: Calc)
}
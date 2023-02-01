package co.natanlima.fitnessreview.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query as Query

@Dao
interface CalcDao {

    //@Query -> buscar
    //@Update -> atualizar
    //@Delete -> deletar

    @Insert
    fun insert(calc: Calc)

    @Query("SELECT * FROM Calc WHERE type = :type")
    fun getRegisterByType(type: String): List<Calc>

    @Delete
    fun delete(calc: Calc): Int

    @Update
    fun update(calc: Calc)

}
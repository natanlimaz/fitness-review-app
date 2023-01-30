package co.natanlima.fitnessreview

import android.app.Application
import co.natanlima.fitnessreview.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }

}
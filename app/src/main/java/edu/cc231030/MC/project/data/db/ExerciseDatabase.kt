package edu.cc231030.MC.project.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.cc231030.MC.project.data.db.Entities.ExerciseEntity
import edu.cc231030.MC.project.data.db.Entities.ExerciseSetEntity
import edu.cc231030.MC.project.data.db.Entities.SessionEntity

// Database
@Database(entities = [ExerciseEntity::class, ExerciseSetEntity::class, SessionEntity::class], version = 7, exportSchema = false)
@TypeConverters(Converters::class) // update the version after changing something within the database
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseDatabase? = null

        fun getDatabase(context: Context): ExerciseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ExerciseDatabase::class.java,
                    "exercise_database"
                )
                    .fallbackToDestructiveMigration() // Automatically handles migrations
                    .build()
                INSTANCE = instance
                println("Database success")
                return instance
            }
        }
    }
}
package bruno.fernandez.dogs.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*** We will implement this class as a Singleton in Kotlin.
 * So, when we call this class.invoke with a context, it returns an instance wheter or not is already created.
 * This is part of Section 9 in the kotlin Jetpack course in udemy***/
//We could add more classes if we had them in the arrayOf
@Database(entities = arrayOf(DogBreed::class), version = 1)
abstract class DogDatabase : RoomDatabase() {

    abstract fun dogDao(): DogDao

    //This creates static functions and classes to be accesed from outside the scope of the class
    companion object {
        //this is the unique instance of the DB
        @Volatile
        private var instance: DogDatabase? = null
        private val LOCK = Any()

        //We return the instance to the invoker. Synchronized doesn´t allow to acces 2 threads to the database
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            //buildDatabase creates the instance
            instance ?: buildDatabase(context).also {
                //we attach it to the instance variable
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            //We pass the applicationContext because the regular context is volatile and can be null
            context.applicationContext,
            DogDatabase::class.java,
            //This is the name of the database
            "dogdatabase"
        ).build()
    }

}

/*invoke blocks the bloc of code, then it returns an instance if it was already created. If not,
it builds the database (with a context, a class and a name), and then assign that database to the instance,
and returns it to the one who invoked the method.*/
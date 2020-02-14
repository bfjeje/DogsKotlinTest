package bruno.fernandez.dogs.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/*** Here we define what kind of functions we can perform in our DB***/
@Dao
interface DogDao {

    @Insert
    //vararg means there could be multiple arguments. The list of Long will be the list of primary keys.
    //suspend is because this function must be on a separate thread
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dogbreed WHERE uuid= :dogId")
    suspend fun getDog(dogId: Int): DogBreed

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()
}
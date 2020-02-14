package bruno.fernandez.dogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//Entity is from Room. We ccould add a specific tablename, like: @Entity(tableName = "dogBreed")
@Entity
data class DogBreed(

    //This is from Room. The name of the column on the db
    @ColumnInfo(name = "breed_id")
    //SerializedName I thing is from Retrofit
    //The id from the JSON links to breedId in my model
    // el ? significa que puede tener un valor nulo
    @SerializedName("id")
    val breedId: String?,

    @ColumnInfo(name = "dog_name")
    //if the name of the json matchs exactly with the name of the attribute, we dont need to put the tag SerializedName
    @SerializedName("name")
    val dogBreed: String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifespan: String?,

    @ColumnInfo(name = "beed_group")
    @SerializedName("breed_group")
    val breedGroup: String?,
    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val bredFor: String?,

    //We don´t need to specify the columnName if we are happy with the name of the variables as a column in the DB
    @SerializedName("temperament")
    val temperament: String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    val imageURL: String?
) {
    //Here i will put the primary key
    @PrimaryKey(autoGenerate = true)
    //By default the value is 0
    var uuid: Int = 0
}
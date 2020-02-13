package bruno.fernandez.dogs.model

import com.google.gson.annotations.SerializedName

//el ? significa que puede tener un valor nulo
data class DogBreed(
    //The id from the JSON links to breedId in my model
    @SerializedName("id")
    val breedId: String?,
    //if the name of the json matchs exactly with the name of the attribute, we dont need to put the tag SerializedName
    @SerializedName("name")
    val dogBreed: String?,
    @SerializedName("life_span")
    val lifespan: String?,
    @SerializedName("breed_group")
    val breedGroup: String?,
    @SerializedName("bred_for")
    val bredFor: String?,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("url")
    val imageURL: String?
)
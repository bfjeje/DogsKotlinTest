package bruno.fernandez.dogs.model

//el ? significa que puede tener un valor nulo
data class DogBreed(
    val breedId: String?,
    val dogBreed: String?,
    val lifespan: String?,
    val breedGroup: String?,
    val bredFor: String?,
    val temperament: String?,
    val imageURL: String?
)
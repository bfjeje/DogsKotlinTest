package bruno.fernandez.dogs.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {
    //When I call this function, Retrofit will download the file, and return a Single List of DogBreed
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>
}
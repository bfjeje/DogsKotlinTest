package bruno.fernandez.dogs.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {
    private val BASE_URL = "https://raw.githubusercontent.com"

    //Lesson 7
    //This is a Retrofit class that allows us to call the api and get the information
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        //Gson converts json into objects from our model
        .addConverterFactory(GsonConverterFactory.create())
        //RxJava2 converts the object we created into a Single type, which is an Observable
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        //we build based on the Interface DogsApi, so we can call the method getDogs
        .create(DogsApi::class.java)

    fun getDogs(): Single<List<DogBreed>> {
        return api.getDogs()
    }
}
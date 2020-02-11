package bruno.fernandez.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bruno.fernandez.dogs.model.DogBreed

/*
* The VidewModel Shouldn´t know ANYTHING about the View*/
class ListViewModel : ViewModel() {

    //dogs provides the real list of dogs into the List<DogBreed>, and it gets it from the data source
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        //data for example
        val dog1 = DogBreed("1", "Corgi", "15 years", "breedGroup", "bredFor", "temperament", "")
        val dog2 = DogBreed("2", "Labrador", "10 years", "breedGroup", "bredFor", "temperament", "")
        val dog3 = DogBreed("3", "Pug", "99 years", "breedGroup", "bredFor", "temperament", "")

        val dogList = arrayListOf<DogBreed>(dog1, dog2, dog3)

        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }
}
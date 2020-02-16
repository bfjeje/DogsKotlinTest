package bruno.fernandez.dogs.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bruno.fernandez.dogs.model.DogBreed
import bruno.fernandez.dogs.model.DogDatabase
import bruno.fernandez.dogs.model.DogsApiService
import bruno.fernandez.dogs.util.SharedPreferencesHelper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

/*
* The VidewModel Shouldn´t know ANYTHING about the View.
* Here we just call the api, and store the data.
* The viewmodel knows nothing about the view.*/
class ListViewModel(application: Application) : BaseViewModel(application) {

    //SharedPreferences chapter
    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L //5 minutes

    //Lecture 8
    private val dogService = DogsApiService()
    //allows us to observe the observable without worring about get rid of it. Avoids memory leaks
    private val disposable = CompositeDisposable()

    //dogs provides the real list of dogs into the List<DogBreed>, and it gets it from the data source
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    //Modified for the sharedpreference chapter
    fun refresh() {
        val updateTime = prefHelper.getUpdateTime()
        //if the current time - refresh time is lower than 5 minutes, i refresh from db
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        //This is because we do it with coroutines
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(), "Dogs retrieved from database", Toast.LENGTH_SHORT)
                .show()
        }
    }

    //this retrieves data from remote. We put this into a new function, because in the future we
    // will retrieve data from remote or local db based on some conditions
    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            dogService.getDogs()
                //Use another thread to this
                .subscribeOn(Schedulers.newThread())
                //We push the information into our main thread after we get it from our alternate thread
                .observeOn(AndroidSchedulers.mainThread())
                //Says who will see this
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>() {
                    override fun onSuccess(dogList: List<DogBreed>) {
                        storeDogsLocally(dogList)
                        Toast.makeText(
                            getApplication(),
                            "Dogs retrieved from endpoint",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun dogsRetrieved(dogList: List<DogBreed>) {
        //here we update our MutableLiveData
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list: List<DogBreed>) {
        //Launch will run this in a separate thread, thanks to coroutines
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            //Something from kotlin, doing that * thing will get a list, and separate the elements into
            //individual DogBreed, so we can pass every DogBreed idividually as the Query requires
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(list)
        }
        //This stores the information of the moment when we have updated our database with the dog
        // information retrieved. Allows us to decide if we want to refresh from database or server.
        // It`s part of sharedPreferences
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
package bruno.fernandez.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/*
* We use AndroidViewModel because we receive an application context, and we need an application context (not a regular activity context)
* because the activity context is volatile, and to access the database, we need a reliable context. That´s why we don´t extend
* from a regular ViewModel, but from an AndroidViewModel instead.
* CoroutineScope allows us to have coroutines in our class*/
abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    private val job = Job()

    //We will have a job that is running, and when it returns, we go back to our main thread
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
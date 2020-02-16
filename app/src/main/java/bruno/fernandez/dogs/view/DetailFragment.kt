package bruno.fernandez.dogs.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

import bruno.fernandez.dogs.R
import bruno.fernandez.dogs.util.getProgressDrawable
import bruno.fernandez.dogs.util.loadImage
import bruno.fernandez.dogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var dogUuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //The ? means that arguments can be null
        arguments?.let {
            //Here we retireve the Uuid
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        //Here we get the viewModel and put it into our view
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        //Challenge Database
        //Second: pass uuid
        viewModel.fetch(dogUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let {
                dogName.text = dog.dogBreed
                dogPurpose.text = dog.bredFor
                dogTemperament.text = dog.temperament
                dogLifepan.text = dog.lifespan
                //Challenge Database
                //Third: put the image in the right place
                context?.let {
                    //it makes reference to context in this case
                    dogImage.loadImage(dog.imageURL, getProgressDrawable(it))
                }
            }
        })
    }

}

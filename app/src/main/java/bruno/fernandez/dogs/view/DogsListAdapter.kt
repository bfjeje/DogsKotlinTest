package bruno.fernandez.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import bruno.fernandez.dogs.R
import bruno.fernandez.dogs.model.DogBreed
import bruno.fernandez.dogs.util.getProgressDrawable
import bruno.fernandez.dogs.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

    //This is the Holder of all elements
    class DogViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        //The elements of the dogList will be transformed into views
        val view = inflater.inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    //This links the element in the array with the Holder
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        //I get the attributes of the holder from the layout
        holder.view.name.text = dogsList[position].dogBreed
        holder.view.lifespan.text = dogsList[position].lifespan
        holder.view.setOnClickListener {
            //Here we pass the uuid of the dog to the details fragment
            val action = ListFragmentDirections.actionDetailFragment()
            action.dogUuid = dogsList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }
        //loadImage was created by us in the Util.kt file.
        holder.view.imageView.loadImage(
            dogsList[position].imageURL,
            getProgressDrawable(holder.view.imageView.context)
        )
    }

    fun updateDogList(newDogsList: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }
}
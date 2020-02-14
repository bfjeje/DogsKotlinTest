package bruno.fernandez.dogs.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import bruno.fernandez.dogs.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//This adds a spinner (circular progress image) that represents the loading of an image
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

//The question marks means that can´t be null. When we call loadImage, we automatically place it here.
//We extended the functionality of ImageView, to be used in the Adapter.
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_dog_icon)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}
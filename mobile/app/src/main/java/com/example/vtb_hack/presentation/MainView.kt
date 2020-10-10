package com.example.vtb_hack.presentation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.CarDB
import com.example.vtb_hack.data.Content
import com.example.vtb_hack.data.DataBase
import com.example.vtb_hack.data.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_view.*
import java.io.ByteArrayOutputStream
import java.util.*


class MainView : Fragment(R.layout.main_view) {

    companion object {
        private const val CREATE_PHOTO = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        DataBase.getDataBase().carDAO()
            .getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                carRecycler.adapter = CarAdapter(it)
            }.subscribe()

        camera.setOnClickListener {
            val photoDialogFragment = PhotoDialogFragment()
            photoDialogFragment.setTargetFragment(this, PhotoDialogFragment.PICK_PHOTO)
            photoDialogFragment.show(fragmentManager!!, "photoPicker")
        }

        val filters = listOf("Марка", "Тип кузова", "Цвет", "5000")
        filterRecycler.adapter = FilterAdapter(filters)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        lateinit var encodedImage: String
        lateinit var bm: Bitmap

        when (resultCode) {
            PhotoDialogFragment.PICK_PHOTO -> {
                val mCurrentPhotoPath = data?.extras?.get("photo") as Uri
                val inputStream = context?.contentResolver?.openInputStream(mCurrentPhotoPath)
                bm = BitmapFactory.decodeStream(inputStream)


            }
            PhotoDialogFragment.CREATE_PHOTO -> {
                val currentPhotoPath = data?.getStringExtra("path")

                BitmapFactory.decodeFile(currentPhotoPath).also {
                    val matrix = Matrix()
                    val orientation = ExifInterface(currentPhotoPath!!).getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL
                    )
                    matrix.postRotate(
                        when (orientation) {
                            ExifInterface.ORIENTATION_ROTATE_90 -> {
                                90f
                            }
                            ExifInterface.ORIENTATION_ROTATE_180 -> {
                                180f
                            }
                            ExifInterface.ORIENTATION_ROTATE_270 -> {
                                270f
                            }
                            else -> {
                                0f
                            }
                        }
                    )
                    bm = it
                }
            }
        }
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos) // bm is the bitmap object
        val byteArrayImage: ByteArray = baos.toByteArray()
        encodedImage = Base64.getEncoder().encodeToString(byteArrayImage)
        encodedImage.replace("\n", "")

        val res = RetrofitInstance.instance.postCar(Content(encodedImage))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val fragment = CarPageFragment()
                    fragment.arguments = Bundle().apply {
                        putString("model", it.model)
                        putString("country", it.country)
                        putString("bodyType", it.bodyType)
                        putString("brand", it.brand)
                        putInt("colorsCount", it.colorsCount)
                        putInt("price", it.price)
                        putInt("doorsCount", it.doorsCount)
                        putString("bigPhoto", it.bigPhoto.toString())
                    }

                    fragmentManager!!.beginTransaction().addToBackStack("mainView")
                        .replace(R.id.mainFragment, fragment).commit()
                },
                {
                    Toast.makeText(context, "Автомобиль не распознан", Toast.LENGTH_SHORT)
                        .show()
                }
            )
    }
}
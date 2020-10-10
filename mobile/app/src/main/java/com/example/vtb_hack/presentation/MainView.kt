package com.example.vtb_hack.presentation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Car
import com.example.vtb_hack.data.CarDB
import com.example.vtb_hack.data.DataBase
import com.example.vtb_hack.data.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
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
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(context?.packageManager!!)?.also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                        null
                    }
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            context!!,
                            "com.vtb_hack.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, CREATE_PHOTO)
                    }
                }
            }
        }

        val filters = listOf("Марка", "Тип кузова", "Цвет", "5000")
        filterRecycler.adapter = FilterAdapter(filters)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bm = BitmapFactory.decodeFile(mCurrentPhotoPath)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos) // bm is the bitmap object
        val byteArrayImage: ByteArray = baos.toByteArray()
        val encodedImage: String = Base64.getEncoder().encodeToString(byteArrayImage)


    }

    lateinit var mCurrentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            mCurrentPhotoPath = absolutePath
        }
    }
}
package com.example.vtb_hack.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Car
import kotlinx.android.synthetic.main.car_item.*
import kotlinx.android.synthetic.main.main_view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainView : Fragment(R.layout.main_view) {

    companion object {
        private const val CREATE_PHOTO = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cars = listOf(
            Car(
                1,
                "LADA",
                "Granta",
                "Россия",
                273310,
                "Седан",
                3,
                3,
                Uri.parse("https://tradeins.space/uploads/photo/511795/hetch.png")
            ),

            Car(
                2,
                "Toyota",
                "Camry",
                "Россия",
                273310,
                "Хетчбэк",
                3,
                3,
                Uri.parse("https://tradeins.space/uploads/photo/511795/hetch.png")
            ),

            Car(
                3,
                "BMW",
                "X5",
                "Россия",
                273310,
                "Универсал",
                3,
                3,
                Uri.parse("https://tradeins.space/uploads/photo/511795/hetch.png")
            )
        )

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

        carRecycler.adapter = CarAdapter(cars)
        val filters = listOf("Марка", "Тип кузова", "Цвет", "5000")
        filterRecycler.adapter = FilterAdapter(filters)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(context, "Фото сделано", Toast.LENGTH_LONG).show()
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
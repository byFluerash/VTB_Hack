package com.example.vtb_hack.presentation

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.example.vtb_hack.R
import kotlinx.android.synthetic.main.photo_dialog.view.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PhotoDialogFragment : DialogFragment(), View.OnClickListener {

    companion object {
        const val PICK_PHOTO = 1
        const val CREATE_PHOTO = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.photo_dialog, null)

        view.choose_photo.setOnClickListener(this)
        view.create_photo.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.choose_photo -> {
                val openGallery = Intent().setType("image/*").setAction(Intent.ACTION_PICK)
                startActivityForResult(
                    Intent.createChooser(openGallery, "Выберите фото"),
                    PICK_PHOTO
                )
            }
            R.id.create_photo -> {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(context?.packageManager!!)?.also {
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
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
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PICK_PHOTO -> {
                if (resultCode == RESULT_OK && data != null) {
                    try {
                        val intent = Intent().putExtra("photo", data.data!!)
                        targetFragment?.onActivityResult(targetRequestCode, PICK_PHOTO, intent)
                        dismiss()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
            CREATE_PHOTO -> {
                if (resultCode == RESULT_OK) {
                    try {
                        val intent = Intent().putExtra("path", mCurrentPhotoPath)
                        targetFragment?.onActivityResult(targetRequestCode, CREATE_PHOTO, intent)
                        dismiss()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
        }
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
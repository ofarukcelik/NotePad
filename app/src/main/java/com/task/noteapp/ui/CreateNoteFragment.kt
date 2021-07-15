package com.task.noteapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.task.noteapp.databinding.FragmentCreateNotesBinding
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CreateNoteFragment : Fragment() {
  private val PICK_IMAGES_CODE = 0
  private val PERMISSION_CODE = 1000
  private val IMAGE_CAPTURE_CODE = 1001
  private lateinit var binding: FragmentCreateNotesBinding
  private var selectedBitmap: Bitmap? = null
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCreateNotesBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.lnrGallery.setOnClickListener { intentGallery() }
    binding.lnrCamera.setOnClickListener { captureImage() }
    binding.btnSave.setOnClickListener { btnSaveOnClick() }
  }

  private fun intentGallery() {
    val intent = Intent()
    intent.type = "image/*"
    intent.putExtra(Intent.ACTION_PICK, true)
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_CODE)
  }

  private fun getDate(): String {
    return SimpleDateFormat("dd.MM.yyyy").format(Date())
  }

  private fun captureImage() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (requireActivity().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
        || requireActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
      ) {
        val permission = arrayOf(
          android.Manifest.permission.CAMERA,
          android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        requestPermissions(permission, PERMISSION_CODE)
      } else {
        openCamera()
      }
    } else {
      openCamera()
    }
  }

  private fun openCamera() {
    val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intentCamera, IMAGE_CAPTURE_CODE)
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      PICK_IMAGES_CODE -> {
        if (resultCode == Activity.RESULT_OK) {
          val uri = data!!.data
          if (uri != null) {
            try {
              selectedBitmap = if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
              } else {
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
              }
            } catch (e: Exception) {
              e.printStackTrace()
            }
          }
        }
      }
      IMAGE_CAPTURE_CODE -> {
        if (resultCode == Activity.RESULT_OK) {
          val imageData = data?.extras?.get("data") as Bitmap
          selectedBitmap = imageData
        }
      }
    }
    binding.imgSelected.isVisible = true
    binding.imgSelected.setImageBitmap(makeSmallerBitmap(400))
  }

  private fun makeSmallerBitmap(maxSize: Int): Bitmap {
    var width = selectedBitmap!!.width
    var height = selectedBitmap!!.height
    val bitmapRatio: Double = width.toDouble() / height.toDouble()
    if (bitmapRatio > 1) {
      width = maxSize
      val scaledHight = width / bitmapRatio
      height = scaledHight.toInt()

    } else {
      height = maxSize
      val scaledWidth = height * bitmapRatio
      width = scaledWidth.toInt()
    }


    return Bitmap.createScaledBitmap(selectedBitmap!!, width, height, true)
  }

  private fun btnSaveOnClick() {
    var byteArray: ByteArray? = null
    if (selectedBitmap != null) {
      var smalledBitmap = makeSmallerBitmap(300)

      val outputStream = ByteArrayOutputStream()
      smalledBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
      byteArray = outputStream.toByteArray()
    }
    try {
      val database = requireActivity().openOrCreateDatabase("Notes", Context.MODE_PRIVATE, null)
      database.execSQL("CREATE TABLE IF NOT EXISTS notes(id INTEGER PRIMARY KEY, title VARCHAR, description VARCHAR, createdDate VARCHAR, updatedDate VARCHAR, image BLOB)")
      val sqlString =
        "INSERT INTO notes (title, description, createdDate, updatedDate, image) VALUES (?,?,?,?,?)"
      val statement = database.compileStatement(sqlString)
      statement.bindString(1, binding.etTitle.text.toString())
      statement.bindString(2, binding.etDescription.text.toString())
      statement.bindString(3, getDate())
      statement.bindString(4, "")
      statement.bindBlob(5, byteArray)
      statement.execute()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
      PERMISSION_CODE -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          openCamera()
        } else {
          Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
        }
      }
    }
  }
}
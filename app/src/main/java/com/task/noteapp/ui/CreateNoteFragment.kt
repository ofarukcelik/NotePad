package com.task.noteapp.ui

import android.app.Activity
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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.task.noteapp.adapter.SelectedImageAdapter
import com.task.noteapp.databinding.FragmentCreateNotesBinding
import java.lang.Exception

class CreateNoteFragment : Fragment() {
  private val PICK_IMAGES_CODE = 0
  private val PERMISSION_CODE = 1000
  private val IMAGE_CAPTURE_CODE = 1001
  private lateinit var binding: FragmentCreateNotesBinding
  private lateinit var selectedBitmaps: ArrayList<Bitmap>

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCreateNotesBinding.inflate(inflater, container, false)
    selectedBitmaps = arrayListOf()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.lnrGallery.setOnClickListener { intentGallery() }
    binding.lnrCamera.setOnClickListener {
      captureImage()
    }
  }

  private fun intentGallery() {
    val intent = Intent()
    intent.type = "image/*"
    intent.putExtra(Intent.ACTION_PICK, true)
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_CODE)
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
              if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                selectedBitmaps.add(ImageDecoder.decodeBitmap(source))
              } else {
                selectedBitmaps.add(MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri))
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
          selectedBitmaps.add(imageData)
        }
      }
    }
    binding.imgRecylerView.layoutManager = GridLayoutManager(requireContext(), 3)
    binding.imgRecylerView.adapter =
      SelectedImageAdapter(selectedBitmaps, requireContext())
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
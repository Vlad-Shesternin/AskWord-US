package com.veldan.askword_us.global.general_classes

import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.RequestCode
import kotlinx.coroutines.*
import java.io.File
import java.lang.Runnable
import java.text.SimpleDateFormat
import java.util.*


class Camera(private val fragment: Fragment) {

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private val TAG = this::class.simpleName
    private val context = fragment.requireContext()
    val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
    var imageCapture: ImageCapture? = null

    fun cameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, PERMISSION_CAMERA) ==
                PackageManager.PERMISSION_GRANTED
    }

    fun startCamera(preview: PreviewView) {
        if (cameraPermissionGranted()) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener(Runnable {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(preview.surfaceProvider)
                    }

                imageCapture = ImageCapture.Builder().build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(fragment, cameraSelector, preview, imageCapture)
                } catch (e: Exception) {
                    Log.i(TAG, "Use case binding failed", e)
                }
            }, ContextCompat.getMainExecutor(context))
        } else {
            fragment.requestPermissions(
                arrayOf(PERMISSION_CAMERA),
                RequestCode.RC_CAMERA
            )
        }
    }


    fun takePhoto(): MutableLiveData<Uri> {
        var savedUri = MutableLiveData<Uri>()

        val photoFile = File(
            getOutputDirectory(),
            SimpleDateFormat(FILENAME_FORMAT, Locale.UK)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture!!.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    savedUri.postValue(Uri.fromFile(photoFile))
                    Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
                }
            }
        )

        return savedUri
    }


    private fun getOutputDirectory(): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, fragment.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }

    fun deleteImage(path: String) {
        val file = File(path)
        if (file.exists()) {
            if (file.delete()) {
                MediaScannerConnection.scanFile(context,
                    arrayOf(Environment.getExternalStorageDirectory().toString()),
                    null) { _, _ ->
                    Toast.makeText(context, "DELETE DONE", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}



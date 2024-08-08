package com.dcfanbase.storyapp.view.posting

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dcfanbase.storyapp.databinding.ActivityPostBinding
import com.dcfanbase.storyapp.view.ViewModelFactory
import com.dcfanbase.storyapp.view.app.MainActivityGo
import com.dcfanbase.storyapp.view.getImageUri
import com.dcfanbase.storyapp.view.reduceFileImage
import com.dcfanbase.storyapp.view.showLoadingCostum
import com.dcfanbase.storyapp.view.showToast
import com.dcfanbase.storyapp.view.uriToFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class PostActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPostBinding
    private val postViewModel : PostViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private var currentUriImg : Uri? = null

    private val launchGalery = this.registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){
        if(it != null){
            currentUriImg = it
            showImage()
        }else{
            Toast.makeText(this,"No Image Selected",Toast.LENGTH_SHORT).show()
        }
    }

    private val  launchCamera = this.registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){
        if(it){
            showImage()
        }
    }

    private var latUser : Double? = null
    private var lngUser : Double? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                }
                permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                }
                else -> {
                    // No location access granted.
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ){
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latUser = location.latitude
                    lngUser = location.longitude
                } else {
                    Toast.makeText(
                        this@PostActivity,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.postGallery.setOnClickListener {
            startGallery()
        }
        binding.postCamera.setOnClickListener {
            startCamera()
        }
        postViewModel.getEnablePostLocation().observe(this){isEnable ->
            if(isEnable){
                getMyLastLocation()
            }
            binding.switchAccessLocation.isChecked = isEnable


        }

        binding.switchAccessLocation.setOnCheckedChangeListener { _, isChecked ->
            postViewModel.saveEnablePostLocation(isChecked)
        }

        binding.postFileImage.setOnClickListener {
            if(currentUriImg == null || (binding.postDescription.text.toString().isEmpty())){
                showToast("Isian tidak boleh kosong",this)
            }else{
                uploadImageKu()
            }

        }
    }

    private fun startGallery(){
        launchGalery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera(){
        currentUriImg = getImageUri(this)
        launchCamera.launch(currentUriImg)
    }

    private fun showImage(){
        currentUriImg?.let {
            binding.postImg.setImageURI(it)
        }
    }

    private fun uploadImageKu(){
        val imageFile = uriToFile(currentUriImg as Uri,this).reduceFileImage()
        val description  = binding.postDescription.text.toString()
        postViewModel.getSession().observe(this){
            postViewModel.uploadImageKu(description,imageFile,"Bearer "+it.token,latUser ,lngUser)
        }
        showLoadingCostum(this,this,postViewModel.isLoading)
        postViewModel.succesResponse.observe(this){
            if(!it){
                val intent = Intent(this, MainActivityGo::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

    }

}
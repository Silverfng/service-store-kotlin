package com.ilsongiuliano.servicestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ilsongiuliano.servicestore.databinding.ActivityServicoBinding
import com.ilsongiuliano.servicestore.databinding.ActivityMobileBinding

class MobileActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityMobileBinding.inflate( layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )
        inicializarToolbar()
    }


    private fun inicializarToolbar() {
        val toolbar = binding.includeMobileToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Store Service"
            setDisplayHomeAsUpEnabled(true)
        }
    }


}
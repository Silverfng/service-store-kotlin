package com.ilsongiuliano.servicestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ilsongiuliano.servicestore.databinding.ActivityPerfilBinding
import com.ilsongiuliano.servicestore.databinding.ActivityServicoBinding
class ServicoActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityServicoBinding.inflate( layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root)
        inicializarToolbar()

    }


    private fun inicializarToolbar() {
        val toolbar = binding.includeServicoToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Store Service"
            setDisplayHomeAsUpEnabled(true)
        }
    }


}
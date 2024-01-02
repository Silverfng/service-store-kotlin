package com.ilsongiuliano.servicestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.ilsongiuliano.servicestore.databinding.ActivityServicoBinding
import com.ilsongiuliano.servicestore.databinding.ActivityWebBinding
class WebActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityWebBinding.inflate( layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root)
        inicializarToolbar()
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeWebToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Store Service"
            setDisplayHomeAsUpEnabled(true)
        }
    }


}
package com.ilsongiuliano.servicestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ilsongiuliano.servicestore.databinding.ActivityMainBinding
import com.ilsongiuliano.servicestore.databinding.ActivityPerfilBinding

class PerfilActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityPerfilBinding.inflate( layoutInflater)
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
        listarPerfil()
        deletarContaUsuario()

    }

    private fun listarPerfil() {
        val idUsuarioLogado = firebaseAuth.currentUser?.uid

        if( idUsuarioLogado != null) {
            val referenciaUsuario = firestore
                .collection("usuarios")
                .document( idUsuarioLogado)

            referenciaUsuario
                .get()
                .addOnSuccessListener { documenteSnapshot ->
                    val dados = documenteSnapshot.data
                    if( dados != null) {
                        val nome = dados["nome"]
                        val email = dados["email"]
                        val textNome = "Nome: $nome"
                        val textEmail = "E-mail: $email"
                        binding.textNomePerfil.text = textNome
                        binding .textEmailPerfil.text = textEmail
                    }
                }
                .addOnFailureListener {  }
        }
    }

    private fun deletarContaUsuario() {
        binding.btnExcluirConta.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Apagar Conta")
                .setMessage("Deseja realmente apagar a conta?")
                .setNegativeButton("Cancelar"){dialog, posicao -> }
                .setPositiveButton("Sim"){dialog, posicao ->
                    val usuarioAuten = firebaseAuth
                    val idUsuarioLogado = firebaseAuth.currentUser?.uid
                    if( idUsuarioLogado != null) {
                        firestore
                            .collection("usuarios")
                            .document(idUsuarioLogado)
                            .delete()
                        usuarioAuten.currentUser?.delete()
                            startActivity(
                                Intent(this,LoginActivity::class.java)
                            )

                    }

                }
                .create()
                .show()
        }


    }


    private fun inicializarToolbar() {
        val toolbar = binding.includePerfilToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Store Service"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}
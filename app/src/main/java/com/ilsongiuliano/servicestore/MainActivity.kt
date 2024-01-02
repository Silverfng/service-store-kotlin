package com.ilsongiuliano.servicestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ilsongiuliano.servicestore.databinding.ActivityJogosBinding
import com.ilsongiuliano.servicestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate( layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root)
        inicializarToolbar()
        inicializarEventoClique()

        }

    private fun inicializarEventoClique() {
        binding.imgDesk.setOnClickListener{
            startActivity(
                Intent(this, ServicoActivity::class.java)
            )
        }
        binding.imgWeb.setOnClickListener{
            startActivity(
                Intent(this, WebActivity::class.java)
            )
        }
        binding.imgJogos.setOnClickListener {
            startActivity(
                Intent(this, JogosActivity::class.java)
            )
        }
        binding.imgMobile.setOnClickListener {
            startActivity(
                Intent(this, MobileActivity::class.java)
            )
        }
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeMainToolbar.tbPrincipal
        setSupportActionBar( toolbar )
        supportActionBar?.apply {
            title = "Store Service"
        }

        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_principal, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when( menuItem.itemId ) {
                        R.id.item_perfil -> {
                            startActivity(
                                Intent(applicationContext, PerfilActivity::class.java)
                            )
                        }
                        R.id.item_sair -> {
                                deslogarUsuario()
                        }
                    }
                    return true
                }

            }
        )
    }

    private fun deslogarUsuario() {

        AlertDialog.Builder(this)
            .setTitle("Deslogar")
            .setMessage("Deseja realmente sair?")
            .setNegativeButton("Cancelar"){dialog, posicao -> }
            .setPositiveButton("Sim"){dialog, posicao ->
                firebaseAuth.signOut()
                startActivity(
                    Intent(applicationContext, LoginActivity::class.java)
                )
            }
            .create()
            .show()

    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }




    }

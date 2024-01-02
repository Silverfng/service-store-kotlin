package com.ilsongiuliano.servicestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.ilsongiuliano.servicestore.databinding.ActivityCadastroBinding
import com.ilsongiuliano.servicestore.databinding.ActivityLoginBinding
import com.ilsongiuliano.servicestore.model.Usuario
import com.ilsongiuliano.servicestore.utils.exibirMensagem
import kotlinx.coroutines.handleCoroutineException

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate( layoutInflater)
    }

    private lateinit var nome: String
    private lateinit var email: String
    private lateinit var senha: String

    //Firebase
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
        inicializarEventosClique()
    }


    private fun inicializarEventosClique() {
        binding.btnCadastrar.setOnClickListener {
            if( validarCampos() ) {
                cadastrarUsuario(nome, email, senha)
            }
        }
    }

    private fun cadastrarUsuario(nome: String, email: String, senha: String) {

        firebaseAuth.createUserWithEmailAndPassword(
            email, senha
        ).addOnCompleteListener { resultado ->
            if ( resultado.isSuccessful){

                val idUsuario = resultado.result.user?.uid
                if( idUsuario != null){
                    val usuario = Usuario(
                        idUsuario, nome, email
                    )
                    salvarUsuarioFirestore( usuario)
                }




            }
        }.addOnFailureListener { erro ->
            try {
                throw erro
            }catch ( erroSenhaFraca: FirebaseAuthWeakPasswordException){
                erroSenhaFraca.printStackTrace()
                exibirMensagem("Senha fraca, digite outra letras, numeros e caracteres especiais!")
            }catch ( erroUsuarioExistente: FirebaseAuthUserCollisionException) {
                erroUsuarioExistente.printStackTrace()
                exibirMensagem("Esse email já possui uma conta!")
            }catch ( erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException){
                erroCredenciaisInvalidas.printStackTrace()
                exibirMensagem("Email inválido, digite outro email!")
            }
        }
    }

    private fun salvarUsuarioFirestore(usuario: Usuario) {

        firestore
            .collection("usuarios")
            .document( usuario.id)
            .set( usuario)
            .addOnSuccessListener {
                exibirMensagem("Sucesso ao fazer seu cadastro!")
                startActivity(
                    Intent(applicationContext,MainActivity::class.java)
                )

            }.addOnFailureListener {
                exibirMensagem("Erro ao fazer cadastro!")
            }


    }

    private fun validarCampos(): Boolean {

        nome = binding.editNome.text.toString()
        email = binding.editEmail.text.toString()
        senha = binding.editSenha.text.toString()

        if( nome.isNotEmpty() ) {

            binding.textInputLayoutNome.error = null
            if ( email.isNotEmpty() ) {

                binding.textInputLayoutEmail.error = null
                if( senha.isNotEmpty() ) {

                    binding.textInputLayoutSenha.error = null
                    return true
                }else{
                    binding.textInputLayoutSenha.error = "Preencha a sua senha"
                    return false
                }

            }else {
                binding.textInputLayoutEmail.error = "Preencha o seu email"
                return false
            }
        }else{
            binding.textInputLayoutNome.error = "Preencha o seu nome"
            return false
        }
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbar.tbPrincipal
        setSupportActionBar( toolbar )
        supportActionBar?.apply {
            title = "Faça o seu cadastro"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}
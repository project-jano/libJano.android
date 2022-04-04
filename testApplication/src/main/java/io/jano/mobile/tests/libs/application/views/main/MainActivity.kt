package io.jano.mobile.tests.libs.application.views.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.jano.mobile.tests.libs.application.R
import io.jano.mobile.tests.libs.application.views.main.viewmodel.MainViewModel
import io.jano.mobile.tests.libs.application.views.securedpayload.ViewSecuredPayloadActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.generate_certificate).setOnClickListener {
            viewModel.generateCertificate()
        }

        findViewById<Button>(R.id.sign_certificate).setOnClickListener {
            viewModel.signCertificateSigningRequest()
        }

        findViewById<Button>(R.id.local_echo).setOnClickListener {
            viewModel.localEcho()
        }

        findViewById<Button>(R.id.decode_payload).setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.enter_ciphered_payload)

            val inputText = EditText(this)
            inputText.hint = "Ciphered text"
            inputText.setLines(3)

            val inputSignature = EditText(this)
            inputSignature.hint = "Signature"
            inputSignature.setLines(3)

            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.addView(inputText)
            linearLayout.addView(inputSignature)
            builder.setView(linearLayout)


            builder.setCancelable(true)
            builder.setPositiveButton(R.string.decode) { s, _ ->
                val cipheredText: String = inputText.text.toString()
                val signatureText: String = inputSignature.text.toString()

                val viewIntent = Intent(this, ViewSecuredPayloadActivity::class.java)
                viewIntent.putExtra(ViewSecuredPayloadActivity.CIPHERED_TEXT_PARAM, cipheredText)
                viewIntent.putExtra(ViewSecuredPayloadActivity.SIGNATURE_PARAM, signatureText)

                startActivity(viewIntent)

                s.cancel()
            }

            builder.show()
        }

        viewModel.loading.observe(this) {
            updateButtons()
        }

        viewModel.currentError.observe(this) {
            it?.let {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                viewModel.resetErrors()
            }
        }

        viewModel.currentCertificate.observe(this) {
            updateButtons()

            it?.let {
                if (it.isEmpty()) return@let

                Toast.makeText(
                    this,
                    "Certificate generated. Subject: ${it.first().subjectX500Principal.name}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateButtons() {
        val loading = viewModel.loading.value == true
        findViewById<View>(R.id.progressBar).visibility = if (loading) {
            View.VISIBLE
        } else {
            View.GONE
        }
        findViewById<Button>(R.id.generate_certificate).isEnabled = !loading

        findViewById<Button>(R.id.sign_certificate).isEnabled =
            !loading && !viewModel.currentCertificate.value.isNullOrEmpty()

        findViewById<Button>(R.id.local_echo).isEnabled =
            !loading && !viewModel.currentCertificate.value.isNullOrEmpty()

        findViewById<Button>(R.id.decode_payload).isEnabled =
            !loading && !viewModel.currentCertificate.value.isNullOrEmpty()
                && viewModel.currentCertificate.value!!.size > 1
    }
}
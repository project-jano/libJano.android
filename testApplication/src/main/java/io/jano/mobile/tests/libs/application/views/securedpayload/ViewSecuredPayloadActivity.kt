package io.jano.mobile.tests.libs.application.views.securedpayload

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.jano.mobile.tests.libs.application.R
import io.jano.mobile.tests.libs.application.views.securedpayload.viewmodel.ViewSecuredPayloadViewModel

class ViewSecuredPayloadActivity : AppCompatActivity() {

    companion object {
        const val CIPHERED_TEXT_PARAM = "msg"
        const val SIGNATURE_PARAM = "sig"
    }

    private val viewModel: ViewSecuredPayloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_secured_payload)

        val text = intent.getStringExtra(CIPHERED_TEXT_PARAM) ?: savedInstanceState?.getString(
            CIPHERED_TEXT_PARAM
        )
        val signature =
            intent.getStringExtra(SIGNATURE_PARAM) ?: savedInstanceState?.getString(SIGNATURE_PARAM)
            ?: ""


        viewModel.loading.observe(this) {
            updateLoading()
        }

        viewModel.currentError.observe(this) {
            it?.let {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                viewModel.resetErrors()
            }
        }

        viewModel.payload.observe(this) {
            it?.let {
                findViewById<EditText>(R.id.payload).setText(it.message)
            }
        }

        text?.let {
            viewModel.decode(text, signature)
        }
    }

    private fun updateLoading() {
        val loading = viewModel.loading.value == true
        findViewById<View>(R.id.progressBar).visibility = if (loading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
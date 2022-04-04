package io.jano.mobile.tests.libs.application.views.main.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.jano.mobile.libs.android.JanoSDK
import io.jano.mobile.tests.libs.application.BuildConfig
import io.jano.mobile.tests.libs.application.api.UserAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.cert.X509Certificate

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private var _currentError = MutableLiveData<Throwable?>(null)
    val currentError: LiveData<Throwable?> = _currentError

    private var _currentCertificate = MutableLiveData<List<X509Certificate>>(listOf())
    val currentCertificate: LiveData<List<X509Certificate>> = _currentCertificate

    private val subject = "Test User"
    private val userId = "test-user"
    private val deviceId = "test-emulator"

    private val options = JanoSDK.ZeroSecurityOptions

    init {
        loadStoredCertificates()
    }

    fun generateCertificate() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (JanoSDK.hasCertificate(
                    userId = userId,
                    deviceId = deviceId,
                )
            ) {
                loadStoredCertificates()
                return@launch
            }

            JanoSDK.generateCertificate(
                context = getApplication(),
                userId = userId,
                deviceId = deviceId,
                subject = subject,
                options = options
            )
                .onSuccess { certificate ->
                    viewModelScope.launch(Dispatchers.Main) {
                        _currentCertificate.value = listOf(certificate)
                        _currentError.value = null
                        _loading.value = false
                    }
                }
                .onFailure {
                    viewModelScope.launch(Dispatchers.Main) {
                        _currentCertificate.value = listOf()
                        _currentError.value = it
                        _loading.value = false
                    }
                }
        }
    }

    fun signCertificateSigningRequest() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val context = getApplication<Application>().applicationContext
            val csr = JanoSDK.createCertificateSigningRequest(
                context = context,
                userId = userId,
                deviceId = deviceId,
            )

            UserAPI(BuildConfig.JANO_SERVICE_URL)
                .signCertificate(
                    userId = userId,
                    deviceId = deviceId,
                    certificateSigningRequest = csr,
                )
                .onSuccess {
                    JanoSDK.updateWithCertificateChain(
                        userId = userId,
                        deviceId = deviceId,
                        certificatesChain = it
                    )
                        .onSuccess {
                            viewModelScope.launch(Dispatchers.Main) {
                                _currentCertificate.value = it
                                _loading.value = false
                                _currentError.value = null
                            }
                        }
                        .onFailure {
                            viewModelScope.launch(Dispatchers.Main) {
                                _loading.value = false
                                _currentError.value = it
                            }
                        }
                }
                .onFailure {
                    viewModelScope.launch(Dispatchers.Main) {
                        _loading.value = false
                        _currentError.value = it
                    }
                }
        }
    }

    fun localEcho() {
        val context = getApplication<Application>().applicationContext
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            JanoSDK.encrypt(
                context = context,
                userId = userId,
                deviceId = deviceId,
                message = "{\"StringKey\":\"a value\",\"IntKey\":1}",
                forRemoteDecryption = false
            )
                .onSuccess { securedPayload ->

                    viewModelScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Payload secured!: ${securedPayload.toJSONString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    JanoSDK.decrypt(
                        userId = userId,
                        deviceId = deviceId,
                        securedPayload = securedPayload.payload,
                        signature = securedPayload.signature,
                        isRemotePayload = false
                    )
                        .onSuccess { payload ->
                            viewModelScope.launch(Dispatchers.Main) {
                                _loading.value = false
                                Toast.makeText(
                                    context,
                                    "Payload decrypted!: ${payload.toJSONString()}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        .onFailure {
                            viewModelScope.launch(Dispatchers.Main) {
                                _loading.value = false
                                _currentError.value = it
                            }
                        }
                }
                .onFailure {
                    viewModelScope.launch(Dispatchers.Main) {
                        _loading.value = false
                        _currentError.value = it
                    }
                }
        }
    }

    fun resetErrors() {
        _currentError.value = null
    }

    private fun loadStoredCertificates() {
        if (!JanoSDK.hasCertificate(
                userId = userId,
                deviceId = deviceId,
            )
        ) {
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            _loading.value = true
        }

        JanoSDK.getCertificates(
            userId = userId,
            deviceId = deviceId,
        )
            .onSuccess {
                viewModelScope.launch(Dispatchers.Main) {
                    _currentCertificate.value = it
                    _loading.value = false
                }

            }.onFailure {
                viewModelScope.launch(Dispatchers.Main) {
                    _currentCertificate.value = listOf()
                    _loading.value = false
                }
            }
    }
}
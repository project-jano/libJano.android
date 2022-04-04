package io.jano.mobile.tests.libs.application.views.securedpayload.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.jano.mobile.libs.android.JanoSDK
import io.jano.mobile.libs.android.models.Payload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewSecuredPayloadViewModel(application: Application) : AndroidViewModel(application) {

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private var _currentError = MutableLiveData<Throwable?>(null)
    val currentError: LiveData<Throwable?> = _currentError

    private var _payload = MutableLiveData<Payload?>(null)
    val payload: LiveData<Payload?> = _payload

    private val userId = "test-user"
    private val deviceId = "test-emulator"

    fun resetErrors() {
        _currentError.value = null
    }

    fun decode(securedPayload: String, signature: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _loading.value = true
        }

        JanoSDK.decrypt(
            userId = userId,
            deviceId = deviceId,
            securedPayload = securedPayload,
            signature = signature,
        )
            .onSuccess {
                viewModelScope.launch(Dispatchers.Main) {
                    _payload.value = it
                    _currentError.value = null
                    _loading.value = false
                }

            }.onFailure {
                viewModelScope.launch(Dispatchers.Main) {
                    _currentError.value = it
                    _loading.value = false
                }
            }
    }
}
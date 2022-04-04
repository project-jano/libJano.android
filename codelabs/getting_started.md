summary: Getting started with Project Jano on Android. 
id: getting_started 
categories: Samples 
tags: android, jano, mobile, sample 
status: Published 
authors: Ezequiel (Kimi) Aceto 
Feedback Link: https://eaceto.dev

<!-- ------------------ -->
## Adding as a dependency
Duration: 00:00:30

Let's start by adding libJano as a dependency of your Android project

1) Add **jitpack.io** maven repository to your root **build.gradle**

```groovy
    allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2) In your application's **build.gradle** add the dependency

```groovy
    dependencies {
    implementation 'com.github.project-jano:user-service.go:1.0.0'
}
```

3) As your Gradle files have changed, you have to sync your Android project.

<!-- ------------------ -->
## Integrating libJano
Duration: 00:03:00

libJano X uses cryptography in all its operations. This means that in order to use high-level
operations, we need to create a series of keys and certificates and send them to our backend.

Calling **JanoSDK.generateCertificate** is necessary only once per user installation. libJano is
also able to handle multi-user applications, as certificates live acrross apps restarts /
users-logout, unless deleted explicitly.

```kotlin
    val userId = "your-user-id"
val deviceId = "a-device-id" // i.e.: Push Notification id of the device
val subject = "a-name" // a.k.a. Common Name

// use JanoSDK.ZeroSecurityOptions only on tests or during development
val options = JanoSDK.userPresenceRequired

JanoSDK.generateCertificate(
    context = context,
    userId = userId,
    deviceId = deviceId,
    subject = subject,
    options = options
)
    .onSuccess { selfSignedCertificate ->
        // a self-signed certificate
    }
    .onFailure { error ->
        // handle error
    }
```

Once you have a self-signed certificate, it should be sent to the backend so it can be stored and
sign by Jano's user service.

```kotlin
val csr = JanoSDK.createCertificateSigningRequest(
    context = context,
    userId = userId,
    deviceId = deviceId,
)

/// Send the CSR to your backend, who will call Jano.
...

JanoSDK.updateWithCertificateChain(
    userId = userId,
    deviceId = deviceId,
    certificatesChain = it
)
    .onSuccess { certificateChain ->
        // certificateChain can be ignored.
    }
    .onFailure { error ->
        // handle error
    }
```

Now you are ready to use all the features of **Jano** in your Android app!

<!-- ------------------ -->
## Decrypting Secure Push Notifications 
Duration: 00:01:00

When receiving a Push Notification encrypted with Jano, a decryption process should be done before the (Android) OS
shows the notification to the user.

Doing this requires to decrypt the **intent** received from the Messaging Service and returning a modified one to the Operating System. 

```kotlin
class AppMessagingService : FirebaseMessagingService() {

    override fun handleIntent(intent: Intent) {
        val userId = "..."
        val deviceId = "..."

        val decryptedIntent = JanoSDK.decryptNotification(userId, deviceId, intent = intent)
        super.handleIntent(decryptedIntent ?: intent)
    }
}
```
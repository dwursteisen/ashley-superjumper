package com.github.dwursteisen.libgdx

import java.io.File
import java.util.*

object IsAndroidSdk {

    /**
     * <p>
     * Force the detection result.
     *
     * If you want to the detection to be always true no matter what :
     *
     * <pre class="code"><code>
     *      IsAndroidSdk.forceDetectionTo = true
     *      // will always be true
     *      if(IsAndroidSdk.isDetected) {
     *
     *      }
     * </code></pre>
     * </p>
     */
    @JvmStatic
    var forceDetectionTo: Boolean? = null

    /**
     * Try to detect if the Android SDK is installed.
     *
     * Let you add/remove android specific project when
     * you haven't installed the Android SDK
     *
     * <pre class="code"><code>
     *      if(IsAndroidSdk.isDetected) {
     *          // add android plugin...
     *          apply plugins: 'android'
     *      }
     * </code></pre>
     *
     */
    @JvmStatic
    fun isDetected(): Boolean = forceDetectionTo ?: tryToDetectAndroid()


    private fun tryToDetectAndroid(): Boolean {
        val androidHome = System.getenv()["ANDROID_HOME"] != null && System.getenv()["ANDROID_HOME"] != ""
        val localfile = File("local.properties")
        if (localfile.isFile) {
            val androidProperties = Properties()
            localfile.inputStream().use {
                androidProperties.load(it)
            }
            return androidHome || androidProperties.getProperty("sdk.dir") != null

        }
        return androidHome
    }

}
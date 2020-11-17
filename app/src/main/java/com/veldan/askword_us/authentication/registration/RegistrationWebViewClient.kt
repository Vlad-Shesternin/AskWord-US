package com.veldan.askword_us.authentication.registration

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.airbnb.lottie.LottieAnimationView
import com.veldan.askword_us.global.objects.Animator
import com.veldan.askword_us.global.objects.Internet

class RegistrationWebViewClient(
    private val progress: LottieAnimationView,
) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.i("onPageFinished", "onPageFinishedSTART: $url")

        val logIn = url!!.contains("https://accounts.google.com/ServiceLogin?")
        val loggedIn = url.contains("https://mail.google.com/mail/u/0/x")
        if (logIn || loggedIn) {
            progress.also {
                it.visibility = View.VISIBLE
                it.setAnimation(Internet.LOADING)
                it.playAnimation()
            }
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.i("onPageFinished", "onPageFinishedEND: $url")

        val accountSelection = url!!.contains("https://accounts.google.com/ServiceLogin/")
        val accountSelected = url.contains("https://mail.google.com/mail/mu/mp")
        if (accountSelection || accountSelected) {
            cancelLoading()
        }
    }

    private fun cancelLoading() {
        progress.also {
            it.cancelAnimation()
            it.visibility = View.INVISIBLE
        }
    }
}
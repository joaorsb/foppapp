package com.foppal247.foppapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.foppal247.foppapp.R
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.foppal247.foppapp.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_foppal_site.*


class FoppalSiteActivity : BaseActivity() {
    private val URL_FOPPAL = "https://www.foppal247.com"

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foppal_site)
        val actionBar = setupToolbar(R.id.toolbar)
        actionBar.setDisplayHomeAsUpEnabled(true)

        setWebViewClient(webview)
        webview.loadUrl(URL_FOPPAL)
        swipeToRefresh.setOnRefreshListener {
            webview.reload()
        }
        val settings = webview.settings
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        swipeToRefresh.setColorSchemeColors(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3
        )
    }

    private fun setWebViewClient(webview: WebView) {

        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                progress.visibility = View.INVISIBLE
                swipeToRefresh.isRefreshing = false
            }
        }
    }
}

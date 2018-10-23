package com.example.kadohiraharuki.testapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class webview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webview = findViewById(R.id.webview) as WebView

        val intent = getIntent()
        val url = intent.extras.getString("WebRepository_url",null)

        //ブラウザを起動せずにwebview内で検索や表示をすることができる
        webview.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        })
        webview.loadUrl(url)
    }
}

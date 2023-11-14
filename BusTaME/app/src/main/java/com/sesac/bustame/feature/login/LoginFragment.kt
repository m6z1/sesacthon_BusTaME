package com.sesac.bustame.feature.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.sesac.bustame.R
import com.sesac.bustame.feature.StartActivity

class LoginFragment : Fragment() {
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        webView = view.findViewById(R.id.wv_login)

        // WebView 설정
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("url")

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?,
            ): Boolean {
                if (request?.url.toString().startsWith("http://localhost:8080/kakao/callback")) {
                    activity?.let {
                        it.supportFragmentManager.beginTransaction().remove(this@LoginFragment)
                            .commit()
                        val intent = Intent(it, StartActivity::class.java)
                        it.startActivity(intent)
                    }
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        if (url != null) {
            webView.loadUrl(url)
        }
    }
}

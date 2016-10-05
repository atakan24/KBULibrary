package com.example.atakan_pc.kbulibrary.Linkler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.atakan_pc.kbulibrary.R;

/**
 * Created by ATAKAN-PC on 14.03.2016.
 */
public class Duyurular extends Fragment {
    private WebView webSayfasi;
    private WebViewClient webViewClient;
    private String url = "http://kutuphane.karabuk.edu.tr/Duyuru_tum.aspx";


    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.duyurular,container,false);

        webSayfasi = (WebView) view.findViewById(R.id.web_duyuru);
        webSayfasi.getSettings().setJavaScriptEnabled(true);
        webSayfasi.setWebViewClient(new WebViewClient());
        webSayfasi.setWebChromeClient(new WebChromeClient());
        webSayfasi.loadUrl(url);
        return view;
    }

}

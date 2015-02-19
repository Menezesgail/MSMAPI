package com.sony.csx.meta.plugin.sonyliv;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestTrendingVideo {

    public static void main(String[] args) throws ClientProtocolException, IOException {
        // TODO Auto-generated method stub
        HttpGet request = new HttpGet("http://www.sonyliv.com/rest/getTrendingVideos?start=" + 0 + "&limit="
                + 10);
        HttpHost proxy = new HttpHost("43.194.159.150", 10080);
        HttpClient httpClient = new DefaultHttpClient();
        //httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
        HttpResponse response = httpClient.execute(request);
        System.out.println(response);

    }

}

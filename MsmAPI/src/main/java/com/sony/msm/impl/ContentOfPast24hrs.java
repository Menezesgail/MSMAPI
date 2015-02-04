package com.sony.msm.impl;


import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sony.msm.api.MSMClient;
import com.sony.msm.model.Content;
import com.sony.msm.model.Get24HoursVideo;


public class ContentOfPast24hrs implements MSMClient {
	
	public List<Content> getData(int start, int limit) throws Exception,JsonParseException,JsonMappingException{
		
		if (start < 0){
			start=0;
		}
		
		if(limit < 0 || limit > 55){
			limit=5;
		}
        
		HttpGet request = new HttpGet("http://www.sonyliv.com/rest/get24HoursVideos?start="+start+"&limit="+limit);
		HttpHost proxy = new HttpHost("43.194.159.150",10080); 
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
		HttpResponse response = httpClient.execute(request);

		String responseBody = EntityUtils.toString(response.getEntity());
	//	System.out.println(responseBody);
		ObjectMapper mapper = new ObjectMapper();
		Get24HoursVideo getVideoObject = mapper.readValue(responseBody,Get24HoursVideo.class);
		List<Content> contentList = getVideoObject.getGet24HoursVideos();

		return contentList;
		}

 
}


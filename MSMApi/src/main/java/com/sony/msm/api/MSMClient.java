package com.sony.msm.api;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.sony.msm.model.Content;

public interface MSMClient {

	public List<Content> getData(int start,int limit) throws ClientProtocolException, IOException, Exception;
}

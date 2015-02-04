package com.sony.msm.facade;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.sony.msm.api.MSMClient;
import com.sony.msm.impl.ContentOfPast24hrs;
import com.sony.msm.impl.TrendingVideo;
import com.sony.msm.model.Content;


public class MSMFacade {

	private MSMClient trendVideo;
	private MSMClient contentPost;

	public MSMFacade(){
		trendVideo = new TrendingVideo();

		contentPost = new ContentOfPast24hrs();

	}


  public List<Content> getTrendingVideos(int start, int limit) throws ClientProtocolException, IOException,Exception{
	return trendVideo.getData(start,limit);

	}	
	public List<Content> getContentPostedInLast24Hours(int start, int limit ) throws ClientProtocolException, IOException,Exception{
		return contentPost.getData(start,limit);
	}
}

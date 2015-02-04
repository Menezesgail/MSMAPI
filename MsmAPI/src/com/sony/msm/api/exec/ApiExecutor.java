package com.sony.msm.api.exec;
import java.util.List;

import com.sony.msm.facade.MSMFacade;
import com.sony.msm.model.Content;

public class ApiExecutor {


	public static void main(String args[]) throws Exception {

		MSMFacade facade = new MSMFacade();
	List<Content> trendingVidList = facade.getTrendingVideos(0,57);

	for(Content trendingVids : trendingVidList){
			System.out.println(trendingVids.toString("Trending Video"));
		}
		List<Content> contentOfLast24Hours = facade.getContentPostedInLast24Hours(0,7);

		for(Content last24hoursVids : contentOfLast24Hours){
			System.out.println(last24hoursVids.toString("Video in last 24 hours"));
		}
		
	}
}



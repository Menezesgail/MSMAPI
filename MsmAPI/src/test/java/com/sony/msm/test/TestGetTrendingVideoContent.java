package com.sony.msm.test;

import mockit.Expectations;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.sony.msm.facade.MSMFacade;
import com.sony.msm.impl.TrendingVideo;
import com.sony.msm.model.Content;



public class TestGetTrendingVideoContent {	
	//@Mocked 
	EntityUtils entityUtils;

	@Test
	public void testGetTrendingVideo() throws  Exception {
		TrendingVideo trendingVideo = new TrendingVideo();
		new Expectations(EntityUtils.class){{

			entityUtils.toString((HttpEntity)any);
			returns(readFromFile());


		}};
		List<Content> list = trendingVideo.getData(0,5); 
		Content testContent= getTestContent();

		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 5);
		Assert.assertEquals(list.get(0).getId(), testContent.getId());
		Assert.assertEquals(list.get(0).getType(), testContent.getType());
		Assert.assertEquals(list.get(0).getName(), testContent.getName());
		Assert.assertEquals(list.get(0).getVideo_thumb(), testContent.getVideo_thumb());
		Assert.assertEquals(list.get(0).getLikes(), testContent.getLikes());
		Assert.assertEquals(list.get(0).getPub_date(), testContent.getPub_date());
		Assert.assertEquals(list.get(0).getPlayed(), testContent.getPlayed());
		Assert.assertEquals(list.get(0).getVideo_length(), testContent.getVideo_length());
		Assert.assertEquals(list.get(0).getVideo_url(), testContent.getVideo_url());
		Assert.assertEquals(list.get(0).getEpisode_number(), testContent.getEpisode_number());
		Assert.assertEquals(list.get(0).getShow_id(), testContent.getShow_id());
		Assert.assertEquals(list.get(0).getShow_title(), testContent.getShow_title());
		Assert.assertEquals(list.get(0).getShow_url(), testContent.getShow_url());
		Assert.assertEquals(list.get(0).getVideo_social_url(), testContent.getVideo_social_url());
		Assert.assertEquals(list.get(0).getBrightcove_id(), testContent.getBrightcove_id());
		Assert.assertEquals(list.get(0).getPlaylist_status(), testContent.getPlaylist_status());
		Assert.assertEquals(list.get(0).getPlaylist_url(), testContent.getPlaylist_url());
		Assert.assertEquals(list.get(0).getLike_status(), testContent.getLike_status());
		Assert.assertEquals(list.get(0).getLike_url(), testContent.getLike_url());
		Assert.assertEquals(list.get(0).getVideo_views(), testContent.getVideo_views());
	}
	
	@DataProvider(name ="dataProvider" )
	public static Object[][] startAndLimit() {
		return new Object[][] {{0, 5}, {1, 7}, {0, 10},{5,20},{10,15}};
		}
	
	@Test(dataProvider = "dataProvider")
	public void testGetContentOnStartAndLimit(int start, int limit) throws Exception{
		MSMFacade trendingVideoContent = new MSMFacade();
		List<Content> list = trendingVideoContent.getContentPostedInLast24Hours(start, limit);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), limit);
		
	}



	public String readFromFile(){
		BufferedReader br = null;
		String readString="";
		try {

			String sCurrentLine;
			br = new BufferedReader(new FileReader("getTrendingVideos.json"));

			while ((sCurrentLine = br.readLine()) != null) {
				readString+=sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return readString; 
	}

	private Content getTestContent(){
		Content content = new Content();
		content.setId("62563");
		content.setType("videos");
		content.setName("26th January Specials - What will you fix this 26th?");
		content.setVideo_thumb("http://setindiapd.brightcove.com.edgesuite.net/709285226001/2015/01/709285226001_4005177630001_video-still-for-video-4004684811001.jpg?pubId=709285226001");
		content.setLikes("11");
		content.setPub_date("Jan 22 2015");
		content.setPlayed(1248);
		content.setVideo_length("00:03:37");
		content.setVideo_url("http://setindiapd.brightcove.com.edgesuite.net/709285226001/2015/01/709285226001_4004869252001_26th-Final-with-watermark.mp4");
		content.setEpisode_number("");
		content.setShow_id("62562");
		content.setShow_title("26th January Specials");
		content.setShow_url("http://www.sonyliv.com/rest/getShowDetails?show_id=62562");
		content.setVideo_social_url("http://www.sonyliv.com/watch/26th-january-specials-what-will-you-fix-this-26th-1");
		content.setBrightcove_id("4004684811001");
		content.setPlaylist_status("No");
		content.setPlaylist_url("http://www.sonyliv.com/rest/addVideoInPlaylists?video_id=62563");
		content.setLike_status("No");
		content.setLike_url("http://www.sonyliv.com/rest/addLikeVideo?video_id=62563");
		content.setVideo_views("1248");
		return content;


	}

}

package com.sony.msm.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import mockit.Expectations;
import com.sony.msm.facade.MSMFacade;
import com.sony.msm.impl.ContentOfPast24hrs;
import com.sony.msm.model.Content;

public class TestGetContentPostedIn24Hours {

	//@Mocked
	EntityUtils entityUtils;
	@Test
	public void testGetContentPostedIn24Hours() throws Exception{
		ContentOfPast24hrs contentofpast24hours = new ContentOfPast24hrs();

		new Expectations(EntityUtils.class) {{
			entityUtils.toString((HttpEntity)any);
			returns (readFromFile());
		}};
		List<Content> list = contentofpast24hours.getData(0,5);
		Content testContent = testGetContents();
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
	
	@DataProvider(name = "dataProvider")
	public static Object[][] startAndLimit() {
	return new Object[][] {{0, 5}, {1, 7}, {0, 10},{5,20}};
	}
	
	
	@Test(dataProvider = "dataProvider")
	public void testGetContentOnStartAndLimit(int start, int limit) throws Exception{
		MSMFacade contentOfPast24Hours = new MSMFacade();
		List<Content> list = contentOfPast24Hours.getContentPostedInLast24Hours(start,limit);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size() , limit);
	}

	public String readFromFile(){
		BufferedReader br = null;
		String readString="";
		try {

			String sCurrentLine;
			br = new BufferedReader(new FileReader("get24HoursVideos.json"));

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

	private Content testGetContents(){
		Content content= new Content();
		content.setId("62695");
		content.setType("videos");
		content.setName("CID - 1st February, 2015 - White Mask");
		content.setVideo_thumb("http://setindiapd.brightcove.com.edgesuite.net/709285226001/2015/02/709285226001_4026309867001_vs-54ce88dee4b06efa4c20a7f1-782203291001.jpg?pubId=709285226001");
		content.setLikes("0");
		content.setPub_date("Feb 01 2015");
		content.setPlayed(42);
		content.setVideo_length("00:42:41");
		content.setVideo_url("http://setindiahd.pd.ak.o.brightcove.com.edgesuite.net/pd/709285226001/709285226001_2978460479001_Text-2-sd.mp4");
		content.setEpisode_number("Ep #1187");
		content.setShow_id("32163");
		content.setShow_title("CID");
		content.setShow_url("http://www.sonyliv.com/rest/getShowDetails?show_id=32163");
		content.setVideo_social_url("http://www.sonyliv.com/watch/cid-4-1st-february-2015-white-mask");
		content.setBrightcove_id("4026075096001");
		content.setPlaylist_status("No");
		content.setPlaylist_url("http://www.sonyliv.com/rest/addVideoInPlaylists?video_id=62695");
		content.setLike_status("No");
		content.setLike_url("http://www.sonyliv.com/rest/addLikeVideo?video_id=62695");
		content.setVideo_views("42");
		return content;


	}


}

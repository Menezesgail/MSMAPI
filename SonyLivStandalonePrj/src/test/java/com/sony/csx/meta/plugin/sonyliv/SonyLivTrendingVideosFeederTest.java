package com.sony.csx.meta.plugin.sonyliv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InterruptedIOException;

import mockit.*;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.sony.csx.meta.Content;
import com.sony.csx.meta.CountryType;
import com.sony.csx.meta.LanguageType;
import com.sony.csx.meta.ResultArray;
import com.sony.csx.meta.commons.exception.MetaExternalInvalidRequestException;
import com.sony.csx.meta.commons.exception.MetaExternalNetworkException;
import com.sony.csx.meta.commons.exception.MetaExternalObjectNotFoundException;
import com.sony.csx.meta.commons.exception.MetaExternalTimeoutException;
import com.sony.csx.meta.commons.exception.MetaInternalException;
import com.sony.csx.meta.commons.http.HttpDownloader;
import com.sony.csx.meta.commons.http.HttpDownloaderException;
import com.sony.csx.meta.entity.video.Work;
import com.sony.csx.meta.plugin.framework.FeedPluginController;
import com.sony.csx.meta.plugin.framework.PluginControllerConfigContainer;
import com.sony.csx.meta.plugin.framework.parameter.impl.FeedParameterImpl;

public class SonyLivTrendingVideosFeederTest {

	@BeforeClass
	public void testInit() {
		PluginControllerConfigContainer.init();
	}

	@AfterClass
	public void testShutdown() {
		PluginControllerConfigContainer.shutdown();
	}

	@DataProvider (name = "paginationDataProvider")
	public static Object[][] startAndLimit() {
		return new Object[][] {{0, 5}, {1, 7}, {0, 10},{5,20}};
	}

	@Test(dataProvider = "paginationDataProvider")
	public void endToEndPaginationTest(int offset,int limit) { 
		FeedPluginController fp = new FeedPluginController();
		FeedParameterImpl param = new FeedParameterImpl("sonyliv", null, "trending-videos", LanguageType.getLanguage("jpn"),
				CountryType.getCountry("JPN"), offset, limit);

		ResultArray<Content> res = fp.feed(param);

		int validateTitle = param.getOffset();
		for (Content c : res.items) {
			System.out.println("[supplierId]" + c.supplierId);
			System.out.println("[name]" + c.name);
			System.out.println("[episode]" + ((Work)c).episode);

		}
	}

	@Test
	public void testTrendingVideoMocked() throws IOException, HttpDownloaderException{
		SonyLivTrendingVideosFeeder trendingVideoFeeder = new SonyLivTrendingVideosFeeder();
		FeedParameterImpl parameter = new FeedParameterImpl("sonyliv" , "Gail" , "trending-videos" , null, null, 0 , 5);
		Integer length = 217;
		final HttpDownloader downloader=new HttpDownloader();
		new Expectations(HttpDownloader.class) {{
			downloader.httpGet(anyString, anyString);
			returns(readFromFile());
		}};
		ResultArray<Content> contentList = trendingVideoFeeder.feed(parameter);  
		Content content = contentList.get(0);
		Work work = (Work) content;
		Assert.assertNotNull(contentList);
		Assert.assertEquals(contentList.size(), 5);
		Assert.assertEquals(work.supplierId, "sonyliv");
		Assert.assertEquals(work.name, "26th January Specials - What will you fix this 26th?");
		Assert.assertEquals(work.episode,  "Jan 22 2015");
		Assert.assertEquals(work.duration, length);
		Assert.assertEquals(work.images.get(0).url, "http://setindiapd.brightcove.com.edgesuite.net/709285226001/2015/01/709285226001_4005177630001_video-still-for-video-4004684811001.jpg?pubId=709285226001");
		Assert.assertTrue(work.deepLink.id.contains("26th-january-specials-what-will-you-fix-this-26th-1"));
		//Assert.assertEquals(work.deepLink.id, "http://setindiapd.brightcove.com.edgesuite.net/709285226001/2015/01/709285226001_4004869252001_26th-Final-with-watermark.mp4");
		Assert.assertEquals(work.customTag, "Videos");

	}  

	@Test
	public void testNegativeTrendingVideo() throws IOException, HttpDownloaderException{
		SonyLivTrendingVideosFeeder trendingVideoFeeder = new SonyLivTrendingVideosFeeder();
		FeedParameterImpl parameter = new FeedParameterImpl("sonyLiv" , "Gail" , "trending-videos" , null, null, 0 , 5);
		final HttpDownloader downloader=new HttpDownloader();
		new Expectations(HttpDownloader.class) {{
			downloader.httpGet(anyString, anyString);
			returns(readFromFile());
		}};
		ResultArray<Content> contentList = trendingVideoFeeder.feed(parameter);  
		Content content = contentList.get(1);
		Work work = (Work) content;
		Assert.assertNotNull(contentList);
		Assert.assertEquals(contentList.size(), 5);
		Assert.assertTrue(work.supplierId.contains("sonyliv"));
		Assert.assertTrue(work.images.size()==1);
		Assert.assertTrue(work.episode.isEmpty());
		Assert.assertTrue(work.duration == 0);

	}

	@DataProvider (name = "Exception")
	public static Object[][] ExceptionCode() {
		return new Object[][] {{400}, {404}, {500},{502},{504}};
	}

	@Test(dataProvider = "Exception" , expectedExceptions = {MetaExternalInvalidRequestException.class , MetaExternalObjectNotFoundException.class , MetaExternalNetworkException.class , MetaExternalTimeoutException.class})
	public void testForFailedConnections(final int code) throws IOException, HttpDownloaderException{
		SonyLivTrendingVideosFeeder trendingVideoFeeder = new SonyLivTrendingVideosFeeder();
		FeedParameterImpl parameter = new FeedParameterImpl("sonyLiv" , "Gail" , "trending-videos" , null, null, 0 , 5);
		final HttpDownloader downloader=new HttpDownloader();
		new Expectations(HttpDownloader.class) 
		{
			{
				downloader.httpGet(anyString, anyString);
				result =  new HttpDownloaderException(code) ;
			}};

			ResultArray<Content> contentList = trendingVideoFeeder.feed(parameter); 
			Assert.assertNotNull(contentList);
	}

	@Test(expectedExceptions = MetaInternalException.class)
	public void testForException() throws IOException, HttpDownloaderException{
		SonyLivTrendingVideosFeeder trendingVideoFeeder = new SonyLivTrendingVideosFeeder();
		FeedParameterImpl parameter = new FeedParameterImpl("sonyLiv" , "Gail" , "trending-videos" , null, null, 0 , 5);
		final HttpDownloader downloader=new HttpDownloader();
		new Expectations(HttpDownloader.class) 
		{
			{
				downloader.httpGet(anyString, anyString);
				result =  new Exception() ;
			}};

			ResultArray<Content> contentList = trendingVideoFeeder.feed(parameter); 
			Assert.assertNotNull(contentList);
	}

	@Test(expectedExceptions = MetaExternalTimeoutException.class)
	public void testForTimeOut() throws IOException, HttpDownloaderException{
		SonyLivTrendingVideosFeeder trendingVideoFeeder = new SonyLivTrendingVideosFeeder();
		FeedParameterImpl parameter = new FeedParameterImpl("sonyLiv" , "Gail" , "trending-videos" , null, null, 0 , 5);
		final HttpDownloader downloader=new HttpDownloader();
		new Expectations(HttpDownloader.class) 
		{
			{
				downloader.httpGet(anyString, anyString);
				result =  new InterruptedIOException() ;
			}};

			ResultArray<Content> contentList = trendingVideoFeeder.feed(parameter); 
			Assert.assertNotNull(contentList);
	}

	private String readFromFile(){
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


}

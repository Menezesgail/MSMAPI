/*

Copyright 2015 Sony Corporation

 */

package com.sony.csx.meta.plugin.sonyliv;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sony.csx.meta.Content;
import com.sony.csx.meta.ResultArray;
import com.sony.csx.meta.commons.exception.MetaExternalInvalidRequestException;
import com.sony.csx.meta.commons.exception.MetaExternalNetworkException;
import com.sony.csx.meta.commons.exception.MetaExternalObjectNotFoundException;
import com.sony.csx.meta.commons.exception.MetaExternalTimeoutException;
import com.sony.csx.meta.commons.exception.MetaInternalException;
import com.sony.csx.meta.commons.exception.base.MetaException;
import com.sony.csx.meta.commons.http.HttpDownloader;
import com.sony.csx.meta.commons.http.HttpDownloaderException;
import com.sony.csx.meta.commons.log.Logger;
import com.sony.csx.meta.deeplink.crackle.CrackleDeepLinkIdHelper;
import com.sony.csx.meta.entity.ImageSizeType;
import com.sony.csx.meta.entity.LogoImage;
import com.sony.csx.meta.entity.MediaType;
import com.sony.csx.meta.entity.common.ContentId;
import com.sony.csx.meta.entity.util.WorkHelper;
import com.sony.csx.meta.entity.video.Work;
import com.sony.csx.meta.entity.video.WorkImage;
import com.sony.csx.meta.plugin.framework.parameter.BasicFeedParameter;
import com.sony.csx.meta.plugin.framework.plugin.Feeder;
import com.sony.csx.meta.plugin.sonyliv.model.TrendingVideos;

/**
 * SonyLiv Trending Video API that fetches the latest videos in JSON.
 * @author 5013003230
 *
 */
public class SonyLivTrendingVideosFeeder implements Feeder<BasicFeedParameter> {

	private static final String SUPPLIER_NAME = "sonyliv";
	private static Logger logger = Logger.getLogger(SonyLivTrendingVideosFeeder.class);

	@Override
	public boolean isSupportedFeedName(String feedName) {
		if (feedName.equals("trending-videos")) {
			return true;
		}
		return false;
	}

	@Override
	public ResultArray<Content> feed(BasicFeedParameter parameter) throws MetaException {
		ResultArray<Content> ret = new ResultArray<Content>();
		List<com.sony.csx.meta.plugin.sonyliv.model.Content> localContentList;
		
		try {
			int offset = 0, limit = 0;
			offset = parameter.getOffset();
			limit = parameter.getLimit();

			// get data
			if (offset < 0) {
				offset = 0;
			}

			if (limit < 0 || limit > 55) {
				limit = 55;
			}
			
			logger.debug("offset --> " + offset + " limit" + limit);
			
			String json = "{}";
			HttpDownloader downloader = new HttpDownloader("http://www.sonyliv.com/rest/getTrendingVideos?start=" + offset + "&limit="
					+ limit);
			json = downloader.httpGet(null, null);
			String responseBody = json ;

			ObjectMapper mapper = new ObjectMapper();
			TrendingVideos getVideoObject = mapper.readValue(responseBody, TrendingVideos.class);

			localContentList = getVideoObject.getGetTrendingVideos();

			return getWorkList(localContentList, ret);
			
		} catch (HttpDownloaderException e) {
			logger.error(e.getMessage(), e);
			throw SonyLivTrendingVideosFeeder.exceptionMapper(e);
		}catch (InterruptedIOException e) {
			logger.error(e.getMessage(), e);
			throw new MetaExternalTimeoutException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new MetaInternalException(e);
		}

	}
	
	//Getting Work list
	private ResultArray<Content> getWorkList(List<com.sony.csx.meta.plugin.sonyliv.model.Content> localContentList, ResultArray<Content> ret){
		for (com.sony.csx.meta.plugin.sonyliv.model.Content content : localContentList) {
			String supplierId = SUPPLIER_NAME;
			final String contentId = content.getId();		
			Work w = WorkHelper.createWorkByListingLevel(null, // SupplierType
					supplierId, // Supplier ID
					new ContentId(SUPPLIER_NAME, new HashMap<String, String>() {{ put("content_id",contentId ); }}), // Content ID = MetaFrontã?®contents
					MediaType.VIDEO, // Media Type
					null, // Language
					null, // Country
					content.getName(), // name  ex:vieo_title
					null, // subtitle
					null, // Summary
					null, // Description
					content.getPub_date(), // Episode
					getDuration(content), // Duration
					null, // Release Date
					null, // Create Year
					getWorkImage(content), // Images
					getLogoImage(content), // Logo Images
					null, // Attribution
					null, // Season
					null, // Series
					null, // Scores
					null, // Genres
					CrackleDeepLinkIdHelper.getTvUrl(content.getVideo_social_url()).toString(), // DeepLinkId
					"Videos"); // Custom Tag
			ret.items.add(w);
		}
		
		return ret;

	}
	
	//Setting WorkImage
	private List <WorkImage> getWorkImage(com.sony.csx.meta.plugin.sonyliv.model.Content content){
		WorkImage workImage =  new WorkImage();
		List<WorkImage> imageList = new ArrayList<WorkImage>();
		workImage.url = content.getVideo_thumb();
		imageList.add(workImage);
		return imageList;

	}
	
	// setting duration 
	private int getDuration(com.sony.csx.meta.plugin.sonyliv.model.Content content) {
		int	TotalElapsedTime=0;
		if(!content.getVideo_length().isEmpty()){
			String videoLength = content.getVideo_length();
			String length[] =videoLength.split(":");
			int hours = Integer.valueOf(length[0]);
			int minutes = Integer.valueOf(length[1]);
			int seconds = Integer.valueOf(length[2]); 
			TotalElapsedTime = (hours * 3600) + (minutes * 60) + seconds; 
		}
		return TotalElapsedTime;
		
	}
	
	//Setting LogoImage
	private List<LogoImage> getLogoImage(com.sony.csx.meta.plugin.sonyliv.model.Content content) {
		List<LogoImage> logoImageList = new ArrayList<LogoImage>();
		LogoImage logoImage = new LogoImage();
		logoImage.url = "http://cdn-as.sonyliv.com/static/9ewwNhZsGgvv8d15aSXjqp4pwkvGXFeN8prAxbCzauF.png";
		logoImage.size = ImageSizeType.THUMBNAIL;
		logoImage.height = 32;
		logoImage.width = 32;
		logoImageList.add(logoImage);
		return logoImageList;
		
	}

	@SuppressWarnings("unchecked")
	private static <T extends MetaException> T exceptionMapper(HttpDownloaderException e) {
		T me = (T) new MetaInternalException(e);
		switch (e.getCode()) {
		case 400:
			me = (T) new MetaExternalInvalidRequestException("", e);
			break;
		case 404:
			me = (T) new MetaExternalObjectNotFoundException(e);
			break;
		case 500:
		case 502:
			me = (T) new MetaExternalNetworkException(e);
			break;
		case 504:
			me = (T) new MetaExternalTimeoutException(e);
			break;
		default:
			break;
		}

		return me;
	}
}

//package com.sony.csx.meta.plugin.sonyliv;
//
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//
//import com.sony.csx.meta.commons.util.ParallelableTask;
//import com.sony.csx.meta.entity.video.WorkImage;
//import com.sony.csx.meta.plugin.sonyliv.model.Content;
//
//
//public class ImageFetcherThread extends ParallelableTask<List<WorkImage>> {
//
//	@Override
//	public List<WorkImage> execute() throws Exception {
//		final List<Content> contentList;
//		List<WorkImage> imageList = new ArrayList<WorkImage>();
//		contentList = SonyLivTrendingVideosFeeder.getContentList();
//
//		WorkImage image;
//
//
//		for(com.sony.csx.meta.plugin.sonyliv.model.Content content :contentList){
//			
// 		image = new WorkImage();
//			image.url = content.getVideo_thumb();
//			BufferedImage bi = ImageIO.read(image.url);
//			image.width = bi.getWidth();
//		    image.height = bi.getHeight();
//			imageList.add(image);
//		}
//		return imageList;
//
//	}
//}

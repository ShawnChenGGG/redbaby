package zz.itcast.redbody7.dao;

import java.util.ArrayList;

/**
 * 主页轮播数据bean
 * @author Administrator
 *
 */
public class HomeTopic {

	
	
	
		
		public String response;
		
		public ArrayList<HomeBanner> home_banner;
		
		
		public class HomeBanner{
			
			public int id;
			public String pic;
			public String title;
			@Override
			public String toString() {
				return "HomeBanner [id=" + id + ", pic=" + pic + ", title="
						+ title + "]";
			}
			
			
			
		}
	}
	
	
	


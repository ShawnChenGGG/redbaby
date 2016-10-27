package zz.itcast.redbody7.bean;

import java.util.ArrayList;
import java.util.List;



public class RecommendBean {
	
	public List<Recommend> brand;
	
	public String response;
	
	
	
	public class Recommend{
		public String key;
		
		public ArrayList<Values> value;
		
		public class Values{
			
			//商品ID
			public int id;
			//商品名称
			public String name;
			//商品图片Url
			public String pic;
		}
		
	}
}

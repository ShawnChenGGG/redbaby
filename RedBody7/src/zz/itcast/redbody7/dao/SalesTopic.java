package zz.itcast.redbody7.dao;

import java.util.ArrayList;

/**
 * 促销快报bean
 * @author Administrator
 *
 */
public class SalesTopic {

	public String response;
	
	public ArrayList<Topic> topic;
	
	public class Topic{
		public int id;
		
		public String name;
		
		public String pic;
	}
}

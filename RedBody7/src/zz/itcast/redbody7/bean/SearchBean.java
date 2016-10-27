package zz.itcast.redbody7.bean;

import java.util.ArrayList;

/**
 *搜索bean
 * @author LENOVO
 *
 */
public class SearchBean {

	public int list_coun;
	
	public String response;
	
	public ArrayList<ProductList> productlist;
	
	public class ProductList{
		public int id;
		public int marketprice;
		public String name;
		public String pic;
		public int price;
	}
}

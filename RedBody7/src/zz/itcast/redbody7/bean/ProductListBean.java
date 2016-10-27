package zz.itcast.redbody7.bean;

import java.util.ArrayList;

/**
 * 商品详情页面的json Bean】
 * 
 * @author zh
 * 
 */
public class ProductListBean {

	public String response;
	public ArrayList<ProductDetail> product;

	public static class ProductDetail {
		

		// "available": false,
		public boolean available;
		// "brandid": 0,
		public int brandid;
		// "buylimit": "20",
		public String buylimit;
		// "categoryid": 1,
		public int categoryid;
		// "color": "",
		public String color;
		// "commentcount": 23,
		public int commentcount;
		// "id": 2,
		public int id;
		// "inventoryarea": "",
		public String inventoryarea;
		// "isgift": true,
		public boolean isgift;
		// "ishotproduct": false,
		public boolean ishotproduct;
		// "islimitbuy": true,
		public boolean islimitbuy;
		// "isnewproduct": true,
		public boolean isnewproduct;
		// "lefttime": "3600",
		public String lefttime;
		// "limitprice": 20,
		public int limitprice;
		// "listfilter": "",
		public String listfilter;
		// "marketprice": 79,
		public int marketprice;
		// "name": "三鹿    ",
		public String name;
		// "number": 998,
		public int number;
		// "pic": "/images/11.jpg",
		public String pic;
		// "price": 76,
		public int price;
		// "productdesc": "此商品为测试商品,暂无商品描述信息",
		public String productdesc;
		// "productprom": "",
		public String productprom;
		// "productproperty": "",
		public String productproperty;
		// "sales": 99,
		public int sales;
		// "score": 111,
		public int score;
		// "size": "",
		public String size;
		// "topicid": 1
		public int topicid;

		

		public ArrayList<String> bigpic;
		public ArrayList<String> pics;
		public ArrayList<String> productProm;
		public ArrayList<ProductProperty> product_property;
		
		public class ProductProperty{
			
			public int id;
			
			public String prodKey;
			public String prodValue;

		}
	}

}

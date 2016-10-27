package zz.itcast.redbody7.bean;

import java.util.ArrayList;

public class CommodityListBean {
	/**
	 * 返回的数据类型	
	 */
	public String response;
	/**
	 * 商品列表数
	 */
	public int list_count;
	/**
	 * 商品列表集合,数据源
	 */
	public ArrayList<Commodity> productlist;
	
	public class Commodity{
		/**
		 * 评论数量
		 */
		public int commentcount;
		/**
		 * 当前商品ID
		 */
		public int id;
		/**
		 * 当前商品市场价
		 */
		public int marketprice;
		/**
		 * 当前商品名字
		 */
		public String name;
		/**
		 * 当前商品的图片url,需要自己拼接baseURl
		 */
		public String pic;
		/**
		 * 当前商品的会员价格
		 */
		public int price;
		/**
		 * 当前商品销量
		 */
		public int sales;
		
	}
}

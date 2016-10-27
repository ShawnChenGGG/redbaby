package zz.itcast.redbody7.bean;

import java.util.List;

public class ProductBean {
	public String list_count;

	public List<Product> productlist;

	public class Product {
		// 商品ID
		public int id;
		// 会员价
		public int marketprice;
		// 商品名称
		public String name;
		// 商品图片Url
		public String pic;
		// 商品价格
		public int price;
		// 销售数量
		public int sales;

	}
}

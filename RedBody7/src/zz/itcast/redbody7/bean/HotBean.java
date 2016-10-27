package zz.itcast.redbody7.bean;

import java.util.List;

public class HotBean {

	// 商品数量
	public String list_count;

	public List<Hot> productlist;

	public class Hot {
		// 商品ID
		public int id;

		public int marketprice;
		// 商品名称
		public String name;

		// 商品Url
		public String pic;

		// 商品价格
		public int price;

		// 销售
		public int sales;

	}

}

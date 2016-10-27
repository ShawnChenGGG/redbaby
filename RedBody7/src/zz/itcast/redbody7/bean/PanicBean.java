package zz.itcast.redbody7.bean;

import java.util.List;

public class PanicBean {
	// 商品数量
	public String list_count;

	public List<Trade> productlist;

	public class Trade {

		// 商品名称
		public String NAME;
		// 商品ID
		public int id;
		// 剩余时间
		public String lefttime;
		// 限时特价
		public int limitprice;

		// 商品Url
		public String pic;
		// 商品价格
		public int price;

		// 销售
		public int sales;

	}

}

package zz.itcast.redbody7.bean;

import java.util.List;

public class FavoriteBean {

	public int list_count;

	public List<Favorite> productlist;

	public class Favorite {

		public int commentcount;

		public int id;

		public boolean isgift;
		public int marketprice;

		public String name;
		public String pic;
		public int price;

	}

	// http://192.168.12.253:8080/ECServicez8/favorites?page=1&pageNum=10&userId=1
}

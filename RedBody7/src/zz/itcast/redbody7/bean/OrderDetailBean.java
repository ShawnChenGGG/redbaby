/**
 * @date 2016-4-25 下午5:18:49
 */
package zz.itcast.redbody7.bean;

/**
 * @author ma
 * 
 * @date 2016-4-25 下午5:18:49
 */
public class OrderDetailBean {

	public DeliveryInfoBean delivery_info;

	public OrderInfoBean order_info;

	public static class DeliveryInfoBean {
		public int type;

	}

	public static class OrderInfoBean {
		public int addressid;
		public String delivery_info;
		public int flag;
		public int orderid;
		public int price;
		public String productlist;
		public int status;
		public String time;
		public int userid;

	}
}

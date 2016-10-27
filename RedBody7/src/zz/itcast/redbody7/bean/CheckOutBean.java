/**
 * @date 2016-4-24 下午3:29:18
 */
package zz.itcast.redbody7.bean;

import java.util.List;

/**
 * @author ma
 * 
 * @date 2016-4-24 下午3:29:18
 */
public class CheckOutBean {

	public List<DeliveryListBean> deliveryList;

	public List<PaymentListBean> paymentList;

	public List<ProductListBean> productList;

	public static class DeliveryListBean {
		public String des;
		public String type;

		@Override
		public String toString() {
			return "DeliveryListBean [des=" + des + ", type=" + type + "]";
		}

	}

	public static class PaymentListBean {
		public String des;
		public String type;

		@Override
		public String toString() {
			return "PaymentListBean [des=" + des + ", type=" + type + "]";
		}

	}

	public static class ProductListBean {
		public boolean available;
		public boolean gift;
		public int id;
		public int marketPrick;
		public String name;
		public int number;

		public PicBean pic;
		public int price;
		public int prodNum;

		@Override
		public String toString() {
			return "ProductListBean [available=" + available + ", gift=" + gift
					+ ", id=" + id + ", marketPrick=" + marketPrick + ", name="
					+ name + ", number=" + number + ", pic=" + pic + ", price="
					+ price + ", prodNum=" + prodNum + "]";
		}

		public static class PicBean {
			public String picUrl;

			@Override
			public String toString() {
				return "PicBean [picUrl=" + picUrl + "]";
			}

		}
	}

	@Override
	public String toString() {
		return "CheckOutBean [deliveryList=" + deliveryList + ", paymentList="
				+ paymentList + ", productList=" + productList + "]";
	}

}

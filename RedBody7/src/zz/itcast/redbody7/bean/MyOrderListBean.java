package zz.itcast.redbody7.bean;

import java.util.ArrayList;

/**
 * @author gjl
 * 我的订单bean
 * */
public class MyOrderListBean {

	public ArrayList<OrderBean> orderBeans;
	public class OrderBean{
		/**
		 * {
			"orderId": "412423145",                //订单ID
			"status": "未处理",                    //订单显示状态
			"time": "2011/10/100 12:16:40",         //下单时间
			"price": "1234.23",                    //订单金额
			"flag":"1" 
		 * */
		public String orderId;
		public String status;
		public String time;
		public String price;
		public String flag;
	}
}

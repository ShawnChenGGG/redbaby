package zz.itcast.redbody7.MyConstant;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.redbody7.bean.CheckOutBean;
import zz.itcast.redbody7.bean.CheckOutBean.DeliveryListBean;
import zz.itcast.redbody7.bean.CheckOutBean.PaymentListBean;

/**
 * 常用的常量 例如：sp，url等... 直接用(接口名.成员变量)调用
 * 
 * @author 李胜杰
 * 
 */
public interface MyConstants {

	/**
	 * 服务器的url，http://192.168.12.253:8080/ECServicez8
	 */
	public String BASE_URL = "http://192.168.12.253:8080/ECServicez8";
	// 备用服务器
	// public String BASE_URL = "http://192.168.253.6:8080/ECServicez8";

	/**
	 * 全组共用的Tag值，redbody7
	 */
	public String TAG_REDBODY7 = "redbody7";

	
	
	/**
	 * 结算中心解析的json字符串
	 */
	public String KEY_CHECK_OUT_JSON = "check_out_json";
	
	
	public List<PaymentListBean> paymenType = new ArrayList<CheckOutBean.PaymentListBean>();
	
	public List<DeliveryListBean> deliveryType = new ArrayList<CheckOutBean.DeliveryListBean>();

	public String ID_XML_NAME = "id_xml";
	       
	public static String CommodityList_URL=BASE_URL+"/productlist?page=1&pagenum=10&cid=1";


}

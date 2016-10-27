/**
 * @date 2016-4-25 下午3:57:16
 */
package zz.itcast.redbody7.bean;

import java.util.List;

/**
 * @author ma
 *
 * @date 2016-4-25 下午3:57:16
 */
public class OrderListType1 {


    /**
     * orderlist : [{"flag":1,"orderid":96,"paymenttype":"货到付款","price":148,"status":1,"time":"2016-04-25"}]
     * response : orderlist
     */

    private String response;
    /**
     * flag : 1
     * orderid : 96
     * paymenttype : 货到付款
     * price : 148
     * status : 1
     * time : 2016-04-25
     */

    public List<OrderlistBean> orderlist;

    public static class OrderlistBean {
        public int flag;
        public int orderid;
        public String paymenttype;
        public int price;
        public int status;
        public String time;

        
    }
}

package zz.itcast.redbody7.bean;

import java.util.List;

/**
 * 这个类请不要格式化
 * @author 李胜杰
 *
 */
public class CartShoppingBean {


    /**
     * cart : [{"prodNum":"3","product":{"id":3,"isgift":"false","name":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":78,"product_property":[],"prom":[],"uplimit":"5678"}},{"prodNum":"3","product":{"id":3,"isgift":"false","name":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":78,"product_property":[],"prom":[],"uplimit":"5678"}},{"prodNum":"3","product":{"id":3,"isgift":"false","name":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":78,"product_property":[],"prom":[],"uplimit":"5678"}},{"prodNum":"3","product":{"id":3,"isgift":"false","name":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":78,"product_property":[],"prom":[],"uplimit":"5678"}},{"prodNum":"3","product":{"id":3,"isgift":"false","name":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":78,"product_property":[],"prom":[],"uplimit":"5678"}},{"prodNum":"3","product":{"id":3,"isgift":"false","name":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":78,"product_property":[],"prom":[],"uplimit":"5678"}}]
     * response : cart
     * totalCount : 18
     * totalPoint : 7992
     * totalPrice : 1404
     */

    public String response;
    /**
	 * 商品数量总计
	 */
    public int totalCount;
    /**
	 * 商品积分总计
	 */
    public int totalPoint;
    /**
	 *  商品金额总计
	 */
    public int totalPrice;
    /**
     * prodNum : 3
     * product : {"id":3,"isgift":"false","name":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":78,"product_property":[],"prom":[],"uplimit":"5678"}
     */
    public List<CartBean> cart;

   

    public  class CartBean {
    	
        /**
         * id : 3
         * isgift : false
         * name : 伊利QQ星
         * number : 3333
         * pic : /images/12.jpg
         * price : 78
         * product_property : []
         * prom : []
         * uplimit : 5678
         */
    	public ProductBean product;
    	public int prodNum;
        

        public  class ProductBean {
        	public int id;
        	public String isgift;
        	public String name;
        	public int number;
        	public String pic;
        	public int price;
        	public String uplimit;
        	public List<property> product_property;
        	public List<String> prom;

        	public class property{
        		public int id;
        		public String k;
        		public String v;
        	}
           
        }
    }
}

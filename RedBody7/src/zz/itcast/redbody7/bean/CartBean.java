/**
 * @date 2016-4-23 上午1:51:50
 */
package zz.itcast.redbody7.bean;

/**
 * @author ma
 * 
 * @date 2016-4-23 上午1:51:50
 */
public class CartBean {

	public int userId;
	public int goodsId;
	public int count;
	public String attrId;

	@Override
	public String toString() {
		return "CartBean [userId=" + userId + ", goodsId=" + goodsId
				+ ", count=" + count + ", attrId=" + attrId + "]";
	}

}

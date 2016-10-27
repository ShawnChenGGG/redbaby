/**
 * @date 2016-4-23 上午11:20:04
 */
package zz.itcast.redbody7;

import java.util.List;

import zz.itcast.redbody7.bean.CartBean;
import zz.itcast.redbody7.dao.CartDao;
import android.test.AndroidTestCase;

/**
 * @author ma
 *
 * @date 2016-4-23 上午11:20:04
 */
public class TestDb extends AndroidTestCase {

	public void testAddCart(){
		CartDao dao = CartDao.getInstance(getContext());
		dao.addCartHight(2, 5, 1, 1+"");
//		dao.addCart(2, 5, 2, 2+"");
		//3:3:1|5:2:2,3
		int count = dao.getGoodsCountByGoodsid(1);
		System.out.println(count+"************");
		
	}
	public void testFind(){
		CartDao dao = CartDao.getInstance(getContext());
		List<CartBean> findGoodsFromUerId = dao.findGoodsFromUerId(2);
		StringBuilder sb = new StringBuilder();
		
		for (CartBean cartBean : findGoodsFromUerId) {

			int goodsId = cartBean.goodsId;
			int count = cartBean.count;
			String attrId = cartBean.attrId;
			
			sb.append(goodsId+":"+count+":"+attrId+"|");
			
		}
		
		String sbString = sb.toString(); 
		System.out.println(sbString);
		
	}
	
}

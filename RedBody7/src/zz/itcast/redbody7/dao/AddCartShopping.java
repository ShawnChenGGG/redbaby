package zz.itcast.redbody7.dao;

import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnPostRequestCallBack;
import zz.itcast.redbody7.utils.MyUtils;
import android.content.Context;

import com.lidroid.xutils.http.RequestParams;

/**
 * 添加购物车，前提是用户已经登录
 * 
 * @author 李胜杰
 * 
 */
public class AddCartShopping {

	/**
	 * 商品属性id
	 */
	private String propertyId;

	/**
	 * 商品数量
	 */
	private int count;

	/**
	 * 商品id
	 */
	private int goodsId;

	/**
	 * 添加购物车的后半部分URL
	 */
	private final String CART_ADD = "/cart/add";

	/**
	 * 用户id，从登录后获取的
	 */
	private int userId = MyUtils.userId;

	/**
	 * 添加商品成功后，返回的message内容
	 */
	private String message;

	/**
	 * 判断请求网络成功之后，解析json数据是否解析成功
	 */
	private boolean isSuccess;

	/**
	 * 上下文环境
	 */
	private Context context;

	/**
	 * 添加购物车
	 * 
	 * @param Context
	 *            上下文环境
	 * @param goodsId
	 *            商品id
	 * @param count
	 *            商品数量
	 * @param propertyId
	 *            商品属性id
	 */
	public AddCartShopping(Context context, int goodsId, int count,
			String propertyId) {

		this.context = context;
		this.goodsId = goodsId;
		this.count = count;
		this.propertyId = propertyId;

	}

	/**
	 * 把商品添加到购物车，前提是用户已经登录
	 * 
	 * @return 返回true，表示添加成功，返回false，表示添加失败
	 */
	public boolean AddCartShoppingImpl() {

		isSuccess(false);

		RequestParams params = new RequestParams();

		params.addBodyParameter("userid", userId + "");
		params.addBodyParameter("sku", goodsId + ":" + count + ":" + propertyId);

		MyHttpUtils myHttpUtils = new MyHttpUtils(MyConstants.BASE_URL
				+ CART_ADD, params,true);

		/**
		 * 用张贺封装的方法，请求网络数据
		 */
		myHttpUtils.setPostRequestCallBack(new OnPostRequestCallBack() {

			/**
			 * 请求网络成功之后调用
			 */
			@Override
			public void onPostSuccess(String result) {

				if (MyUtils.checkJson(result)) {

					try {

						JSONObject jo = new JSONObject(result);
						message = jo.getString("message");

						isSuccess(true);
						LogPrint.logI("lsj", message);

						// 添加商品到购物车数据库中
						addCartShoppingToDb();

					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					LogPrint.logI("lsj", "AddCartShoppingImpl---json字符串校验失败");
				}

			}
		});

		return isSuccess;
	}

	/**
	 * 添加商品到购物车数据库中
	 */
	protected void addCartShoppingToDb() {

		CartDao cartDao = CartDao.getInstance(context);

		cartDao.addCartHight(userId, goodsId, count, propertyId);

	}

	private boolean isSuccess(boolean isSuccess) {

		return this.isSuccess = isSuccess;
	}

}

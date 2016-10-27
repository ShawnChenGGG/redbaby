package zz.itcast.redbody7.utils;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * httpUtils工具类
 * 
 * @author Administrator
 * 
 */
public class MyHttpUtils {

	private HttpUtils http = new HttpUtils();
	private String url;
	private String urlPost;
	private RequestParams params;
	public boolean isSleep;
	
	

	/**
	 * GET方式请求数据
	 * @param url url
	 */
	public MyHttpUtils(String url,boolean isSleep) {
		this.url = url;
		this.isSleep = isSleep;
		getServer();
	}
	
	

	/**
	 * POST方式请求数据
	 * @param urlPost url
	 * @param params  请求需要的参数
	 */
	public MyHttpUtils(String urlPost , RequestParams params,boolean isSleep) {
		this.urlPost = urlPost;
		this.params = params;
		this.isSleep = isSleep;
		postServer();
	}



	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1:
				String result = (String) msg.obj;
				if(callBack != null){
					
					callBack.onSuccess(result);
				}
				
				break;
			case 2:
				
				String urlPost = (String) msg.obj;
				if(postRequestCallBack != null){
					
					postRequestCallBack.onPostSuccess(urlPost);
				}
				break;

			default:
				break;
			}
		}
	};
	
	/**
	 * get方式请求成功后，返回参数
	 * @param callBack OnRequestCallBack接口
	 */
	public void setOnRequestCallBack(OnRequestCallBack callBack){
		this.callBack =callBack;
	}
	
	private OnRequestCallBack callBack;
	
	public interface OnRequestCallBack{
		void onSuccess(String result);
	}
	
	
	/**
	 * POST方式请求数据，
	 * @param postRequestCallBack 回调接口
	 */
	public void setPostRequestCallBack(OnPostRequestCallBack postRequestCallBack){
		this.postRequestCallBack = postRequestCallBack;
	}
	
	private OnPostRequestCallBack postRequestCallBack;
	
	public interface OnPostRequestCallBack{
		
		void onPostSuccess(String result);
	}

	
	/*
	 * get方式请求数据
	 */
	private void getServer(){
		http.send(HttpMethod.GET, url, new MyRequestCallBack() {

			@Override
			public void onSuccess(final ResponseInfo<String> responseInfo) {
				
				
				
					new Thread() {
						public void run() {
							if (isSleep) {
								SystemClock.sleep(1500);
							}
							String result = responseInfo.result;
							
							Message msg = Message.obtain();

							msg.obj = result;

							msg.what = 1;

							handler.sendMessage(msg);
						}
					}.start();
				

			}
		});
	}
	
	/*
	 * post方式请求数据
	 */
	private void postServer(){
		
		http.send(HttpMethod.POST, urlPost, params, new MyRequestCallBack() {
			
			@Override
			public void onSuccess(final ResponseInfo<String> responseInfo) {
				
				
				
					new Thread() {
						public void run() {
							if (isSleep) {
								SystemClock.sleep(2000);
							}
							String result = responseInfo.result;
							Message msg = Message.obtain();

							msg.obj = result;

							msg.what = 2;

							handler.sendMessage(msg);
						}
					}.start();
				

				
			}
		});
	}

}

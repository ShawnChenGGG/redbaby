package zz.itcast.redbody7.utils;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public abstract class MyRequestCallBack extends RequestCallBack<String>{

	@Override
	public void onFailure(HttpException error, String msg) {
		
		LogPrint.logI("zh", msg);
		error.printStackTrace();
	}

	@Override
	public abstract void onSuccess(ResponseInfo<String> responseInfo);

}

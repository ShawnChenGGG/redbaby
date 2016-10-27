package zz.itcast.redbody7.activity;

import java.util.List;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.CommentBean;
import zz.itcast.redbody7.bean.CommentBean.Comment;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class ActivityComment extends BaseActivity {

	private TittleBarView tb_coment;
	
	private ListView lv;

	private HttpUtils httpUtils;

	private int id;

	private List<Comment> comments;

	private Myadpater myadpater;

	@Override
	public void initRegister() {
		tb_coment.setOnBackClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}

	@Override
	public void initData() {
	httpUtils = new HttpUtils();
	httpUtils.send(HttpMethod.GET, MyConstants.BASE_URL+"/product/comment?pId="+id, new RequestCallBack<String>() {

		@Override
		public void onFailure(HttpException arg0, String arg1) {
			arg0.printStackTrace();
			LogPrint.logI("ActivityComment", "ActivityComment : http 请求数据失败"+arg1);
			
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			String json = arg0.result;
			
			boolean checkJson = MyUtils.checkJson(json);
			if (checkJson) {
				pareJson(json);
			}else {
				Toast.makeText(mContext, "服务器异常", 0).show();
				LogPrint.logI("ActivityComment : http 请求数据", "请求失败");
			}
			
		}
	});
	
		
	}

	protected void pareJson(String json) {
		Gson gson=new Gson();
		CommentBean commentBean = gson.fromJson(json, CommentBean.class);
		//获得数据源
		comments = commentBean.comment;
		myadpater = new Myadpater();
		lv.setAdapter(myadpater);
		
	}

	@Override
	public void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);
		
		tb_coment = (TittleBarView) findViewById(R.id.tb_comment);
		lv = (ListView) findViewById(R.id.lv_comment);
		//初始化控件
		tb_coment.setOperVisibility(false);
		tb_coment.setTittle("评论专区");
		id = getIntent().getExtras().getInt("pId");
		
		
	}
	
	private class Myadpater extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return comments.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return comments.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHoleder holeder=null;
			if (convertView==null) {
				convertView=View.inflate(mContext, R.layout.item_comment, null);
				holeder=new ViewHoleder();
				holeder.tv_name=(TextView) convertView.findViewById(R.id.tv_name_comment);
				holeder.tv_time=(TextView) convertView.findViewById(R.id.tv_time_comment);
				holeder.tv_content=(TextView) convertView.findViewById(R.id.tv_content_comment);
				holeder.tv_title=(TextView) convertView.findViewById(R.id.tv_title_comment);
				convertView.setTag(holeder);
			}else {
				holeder=(ViewHoleder) convertView.getTag();
			}
			Comment comment = comments.get(position);
			holeder.tv_name.setText(comment.username);
			holeder.tv_time.setText(comment.time);
			holeder.tv_content.setText(comment.content);
			holeder.tv_title.setText(comment.title);
			
			return convertView;
		}
		
	}
	
	
	private class ViewHoleder{
		
		public TextView tv_name,tv_time,tv_title,tv_content;
		
		
	}

}

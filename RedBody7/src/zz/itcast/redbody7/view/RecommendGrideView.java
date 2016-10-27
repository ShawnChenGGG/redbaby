package zz.itcast.redbody7.view;

import java.util.ArrayList;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.RecommendBean.Recommend;
import zz.itcast.redbody7.bean.RecommendBean.Recommend.Values;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

/**
 * 推荐品牌的GrideView组合控件
 * 
 * @author zh
 * 
 */
public class RecommendGrideView extends LinearLayout {

	private GridView gv;
	private TextView tvClass;

	public RecommendGrideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public RecommendGrideView(Context context) {
		this(context, null);
	}

	/**
	 * 初始化控件
	 */
	public void initView() {
		View view = View.inflate(getContext(), R.layout.recommend_gride_view,
				null);
		gv = (GridView) view.findViewById(R.id.custom_gridview);
		tvClass = (TextView) view.findViewById(R.id.custom_class);
		addView(view);
	}

	/**
	 * 给GridView设置数据
	 * 
	 * @param bean
	 */
	public void setAdpaterData(Recommend bean) {
		if (bean != null) {
			tvClass.setText(bean.key);
			// System.out.println(bean.key+"-------------");
			gv.setAdapter(new MyRecommendItemAdapter(bean.value));
		}
	}

	/**
	 * 是否隐藏条目的头标题
	 * @param isShow true 隐藏        false 显示
	 */
	public void setItemTileVisibility(boolean isShow){
		if(!isShow){
			tvClass.setVisibility(View.VISIBLE);
		}else{
			tvClass.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置GV显示的列数，默认3列
	 * @param num  需要显示几列
	 */
	public void setGVNumColumns(int num){
		
		if(num < 1){
			throw new IllegalArgumentException("必须大于1列");
		}
		
		gv.setNumColumns(num);
	}
	
	/**
	 * 设置头条目的文字颜色
	 * @param color
	 */
	public void setItemTileColor(ColorStateList color){
		
		tvClass.setTextColor(color);
	}
	
	/**
	 * 使用十六进制表示头条目的文字颜色
	 * @param colorId
	 */
	public void setItemTileColor(int colorId){
		tvClass.setTextColor(colorId);
	}
	
	/**
	 * 为gridView设置条目点击监听事件
	 * 
	 * @param listener
	 */
	public void setGVOnItemClickListener(OnItemClickListener listener) {
		if (listener != null ) {
			gv.setOnItemClickListener(listener);
		}
	}

	class MyRecommendItemAdapter extends BaseAdapter {
		private BitmapUtils bUtils;
		private ArrayList<Values> value;

		public MyRecommendItemAdapter(ArrayList<Values> value) {
			bUtils = new BitmapUtils(getContext());
			this.value = value;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyRecommendHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(),
						R.layout.gridview_item_recommend, null);

				holder = new MyRecommendHolder();

				holder.iv_img = (ImageView) convertView
						.findViewById(R.id.recommend_item_img);

				convertView.setTag(holder);

			} else {
				holder = (MyRecommendHolder) convertView.getTag();
			}
			// 填充数据

			Values item = getItem(position);
			// System.out.println(item.name+"=============");
			bUtils.display(holder.iv_img, MyConstants.BASE_URL + item.pic);

			return convertView;
		}

		@Override
		public int getCount() {
			System.out.println(value.size());
			return value.size();
		}

		@Override
		public Values getItem(int position) {
			Values values = value.get(position);
			return values;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}

	class MyRecommendHolder {

		public ImageView iv_img;

	}
	
	

}

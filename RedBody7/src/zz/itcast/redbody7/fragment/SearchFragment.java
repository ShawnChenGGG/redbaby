package zz.itcast.redbody7.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.activity.SearchResultActivity1;
import zz.itcast.redbody7.activity.SearchResultActivity2;
import zz.itcast.redbody7.utils.PreferenceUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 搜索Fragment
 * 
 * @author zh
 * 
 */
public class SearchFragment extends BaseFragment implements OnClickListener {

	/**
	 * 顶部标题初始化
	 */
	private TittleBarView search_title_bar;
	private EditText et_search;
	private Button btn_search;
	private ImageView iv_tiger;
	private HttpUtils httpUtils;
	private SharedPreferences sp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sp = mContext.getSharedPreferences("search", 0);
		/**
		 * 联网获取数据
		 */
		getHotSearch();

		initData();
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View initView() {

		View view = View.inflate(getActivity(), R.layout.fragment_search, null);

		initTitle(view);
		iv_tiger = (ImageView) view.findViewById(R.id.iv_tiger);
		return view;
	}

	private void getHotSearch() {
		httpUtils = new HttpUtils();
		String url = MyConstants.BASE_URL + "/search/recommend";
		RequestParams params = new RequestParams();
		httpUtils.send(HttpMethod.GET, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 联网成功
						String hotSearch = responseInfo.result;
						// 解析数据

						parseJson(hotSearch);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// 联网失败
					}

				});
	}

	private void parseJson(String hotSearch) {
		/**
		 * {"response":"searchrecommend","search_keywords":["羽绒服","学习机","早餐奶",
		 * "生活用品"]}
		 */
		try {
			JSONObject jsonObject = new JSONObject(hotSearch);
			String response = jsonObject.getString("response");
			if ("error".equals(response)) {
				JSONObject errorObj = jsonObject.getJSONObject("error");
				String errorText = errorObj.getString("text");
				Toast.makeText(mContext, "请求错误:" + errorText,
						Toast.LENGTH_SHORT).show();
			} else {
				jsonArray = jsonObject.getJSONArray("search_keywords");
				list_hot.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						hotString = (String) jsonArray.get(i);

						list_hot.add(hotString);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initTitle(View view) {

		expandableListview(view);
		search_title_bar = (TittleBarView) view
				.findViewById(R.id.search_title_bar);
		et_search = (EditText) view.findViewById(R.id.et_serach);
		btn_search = (Button) view.findViewById(R.id.btn_search);

		/**
		 * 按钮点击事件
		 */
		btn_search.setOnClickListener(this);
		search_title_bar.setTittle("搜索");
		search_title_bar.setBackVisibility(false);
		search_title_bar.setOperVisibility(false);

	}

	private void expandableListview(View view) {
		main_expandablelistview = (ExpandableListView) view
				.findViewById(R.id.main_expandablelistview);
		MyExpandableListView adapter = new MyExpandableListView();
		main_expandablelistview.setAdapter(adapter);
		main_expandablelistview.setGroupIndicator(null);// 去掉默认左边箭头

	}

	private List<String> list_hot = new ArrayList<String>();
	private List<String> list_history;
	private JSONArray jsonArray;
	private String hotString;
	private String searchContent;
	private ExpandableListView main_expandablelistview;

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			et_search.setFocusable(true);
			searchContent = et_search.getText().toString().trim();
			sp.edit().putString("search", searchContent).commit();
			// View view = View.inflate(mContext, R.layout.fragment_search,
			// null);
			// expandableListview(view);
			list_history = new ArrayList<String>();
			list_history.add(searchContent);
			if (TextUtils.isEmpty(searchContent)) {
				Toast.makeText(mContext, "请输入关键字", Toast.LENGTH_SHORT).show();
			} else {
				String keyword = PreferenceUtils.getString(mContext, "keyword",
						"");
				if (keyword.contains(searchContent)
						|| searchContent.contains(keyword)) {
					Intent intent = new Intent(mContext,
							SearchResultActivity1.class);
					intent.putExtra("key", searchContent);
					startActivity(intent);
				} else {
					Intent intent3 = new Intent(mContext,
							SearchResultActivity2.class);
					startActivity(intent3);

				}

			}
			et_search.setText("");
			break;
		// TODO
		// case R.id.rl_search_hot:
		// if (isUp) {
		// lv_search_hot.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Intent intent2 = new Intent(mContext,
		// SearchResultActivity1.class);
		// startActivity(intent2);
		// }
		// });
		//
		// MySearchHotAdapter adapter = new MySearchHotAdapter();
		// main_expandablelistview.setAdapter(adapter);
		// } else {
		// }
		//
		// break;
		default:
			break;
		}

	}

	class MyExpandableListView extends BaseExpandableListAdapter {
		/**
		 * 父亲的个数
		 */
		@Override
		public int getGroupCount() {
			return 2;
		}

		/**
		 * 儿子的个数
		 */
		@Override
		public int getChildrenCount(int groupPosition) {
			int size = 0;
			if (groupPosition == 0 && list_hot != null) {
				size = list_hot.size();
			} else if (groupPosition == 1 && list_history != null) {
				size = list_history.size();
			}
			return size;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			convertView = View.inflate(mContext, R.layout.search_group_item,
					null);
			TextView textView = (TextView) convertView
					.findViewById(R.id.parent_textview);
			if (groupPosition == 0) {
				textView.setText("热门搜索");
				textView.setTextSize(17);
			} else if (groupPosition == 1) {
				textView.setText("搜索历史");
				textView.setTextSize(17);
			}

			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.iv_search_arrow);
			// 展开的时候箭头变为向下
			if (isExpanded) {
				iv_tiger.setVisibility(View.GONE);
				Animation a = new AlphaAnimation(1f, 0.0f);
				a.setDuration(500);
				a.setRepeatCount(0);
				a.setFillAfter(true);
				a.setRepeatMode(Animation.REVERSE);
				iv_tiger.startAnimation(a);
				imageView.setImageResource(R.drawable.arrow1);
			} else {
				iv_tiger.setVisibility(View.VISIBLE);
				Animation a = new AlphaAnimation(0.0f, 1f);
				a.setDuration(500);
				a.setFillAfter(true);
				a.setRepeatCount(0);
				a.setRepeatMode(Animation.REVERSE);
				iv_tiger.startAnimation(a);
				imageView.setImageResource(R.drawable.arrow);
			}
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			convertView = View.inflate(mContext, R.layout.search_child_item,
					null);
			TextView textView = (TextView) convertView
					.findViewById(R.id.second_textview);
			if (groupPosition == 0) {
				textView.setText(list_hot.get(childPosition));
				textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext,
								SearchResultActivity1.class);
						startActivity(intent);
					}
				});

			} else {

				String string = sp.getString("search", "");

				textView.setText(string);
				textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext,
								SearchResultActivity1.class);
						startActivity(intent);
					}
				});
			}

			return convertView;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

	}
}

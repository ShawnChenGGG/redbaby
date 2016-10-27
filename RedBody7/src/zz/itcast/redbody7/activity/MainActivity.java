package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.exception.DbException;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.MyConstant.VariableClass;
import zz.itcast.redbody7.bean.DBBrowse;
import zz.itcast.redbody7.fragment.BrandFragment;
import zz.itcast.redbody7.fragment.HomeFragment;
import zz.itcast.redbody7.fragment.MoreFragment;
import zz.itcast.redbody7.fragment.SearchFragment;
import zz.itcast.redbody7.fragment.ShoppingFragment;
import zz.itcast.redbody7.pager.CommodityListPager;
import zz.itcast.redbody7.utils.LogPrint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * 咱们尽量写文档注释，提交代码前尽量格式化代码：Ctrl+Shift+F
 * 
 * @author 李胜杰
 * 
 */

public class MainActivity extends FragmentActivity {

	public static DaoConfig daoConfig;
	
	private RadioGroup rg;

	/**
	 * 再点一次退出，记录上一次点击时间
	 */
	private long lastTime = 0;

	/**
	 * 主页tag
	 */
	private static final String HOME_FRAGMENT = "home_fragment";
	/**
	 * 搜索tag
	 */
	private static final String SEARCH_FRAGMENT = "search_fragment";

	/**
	 * 分类tag
	 */
	private static final String BRAND_FRAGMENT = "brand_fragment";
	/**
	 * 购物侧tag
	 */
	private static final String SHOPPING_FRAGMENT = "shopping_fragment";
	/**
	 * 更多tag
	 */
	private static final String MORE_FRAGMENT = "more_fragment";
	private FragmentManager manager;
	
	/**
	 * 五个RadioButton的id
	 */
	public int[] pagerId = { R.id.main_rb_home, R.id.main_rb_search,
			R.id.main_rb_brand, R.id.main_rb_shopping, R.id.main_rb_more };

	/**
	 * 底部导航栏对应的Fragment列表
	 */
	private List<Fragment> fragmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		VariableClass.mainActivity = this;
		// 将isFirst标志变为false
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		sp.edit().putBoolean("isFirst", false).commit();

		// 初始化界面
		initView();

		manager = getSupportFragmentManager();
		// 默认选择
		rg.check(R.id.main_rb_home);
		manager.beginTransaction()
				.replace(R.id.main_fl, fragmentList.get(0), HOME_FRAGMENT)
				.commit();
		// 注册监听
		register();

		// TODO wx daoConfig
		daoConfig = new DaoConfig(this);
		daoConfig.setDbName("browse");
		daoConfig.setDbVersion(1);
		dbUtils = getDbUtils();
		try {
			dbUtils.createTableIfNotExist(DBBrowse.class);
		} catch (DbException e) {
			e.printStackTrace();
			LogPrint.logI("MainActivity", "创建浏览记录数据库失败");
		}
		
	}

	/**
	 * 注册监听
	 */
	private void register() {

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				jumpPager(checkedId);
			}

			
		});
	}
	
	/**
	 * 跳转界面的方法
	 * @param checkedId
	 */
	public void jumpPager(int checkedId) {
		FragmentTransaction beginTransaction = manager.beginTransaction();
		switch (checkedId) {
		case R.id.main_rb_home:

			beginTransaction.replace(R.id.main_fl, fragmentList.get(0),
					HOME_FRAGMENT);

			break;
		case R.id.main_rb_search:
			beginTransaction.replace(R.id.main_fl, fragmentList.get(1),
					SEARCH_FRAGMENT);

			break;
		case R.id.main_rb_brand:
			beginTransaction.replace(R.id.main_fl, fragmentList.get(2),
					BRAND_FRAGMENT);

			break;
		case R.id.main_rb_shopping:
			beginTransaction.replace(R.id.main_fl, fragmentList.get(3),
					SHOPPING_FRAGMENT);

			break;
		case R.id.main_rb_more:
			beginTransaction.replace(R.id.main_fl, fragmentList.get(4),
					MORE_FRAGMENT);

			break;
		}
		rg.check(checkedId);
		beginTransaction.commit();
	}
	
	/**
	 * 初始化View
	 */
	private void initView() {
		setContentView(R.layout.activity_main);

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new HomeFragment());
		fragmentList.add(new SearchFragment());
		fragmentList.add(new BrandFragment());
		fragmentList.add(new ShoppingFragment());
		fragmentList.add(new MoreFragment());

		rg = (RadioGroup) findViewById(R.id.main_rg);
	}

	/**
	 * 再点一次退出
	 */
	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - lastTime > 2000) {
			// 两次返回时间超出两秒
			Toast.makeText(this, "再点一次退出程序", Toast.LENGTH_SHORT).show();
			lastTime = System.currentTimeMillis();
		} else {
			// 两次返回时间小于两秒，可以退出
			finish();
		}
	}

	/**
	 * 主页Fragment
	 * 
	 * @return
	 */
	public HomeFragment getHomeFragment() {
		return (HomeFragment) fragmentList.get(0);
		//return (HomeFragment) manager.findFragmentByTag(HOME_FRAGMENT);
	}

	/**
	 * 搜索Fragment
	 * 
	 * @return
	 */
	public SearchFragment getSearchFragment() {
		return (SearchFragment) fragmentList.get(1);
		//return (SearchFragment) manager.findFragmentByTag(SEARCH_FRAGMENT);
	}

	/**
	 * 分类Fragment
	 * 
	 * @return
	 */
	public BrandFragment getBrandFragment() {
		return (BrandFragment) fragmentList.get(2);
		//return (BrandFragment) manager.findFragmentByTag(BRAND_FRAGMENT);
	}

	/**
	 * 购物车Fragment
	 * 
	 * @return
	 */
	public ShoppingFragment getShoppingFragment() {
		return (ShoppingFragment) fragmentList.get(3);
		// return (ShoppingFragment)
		// manager.findFragmentByTag(SHOPPING_FRAGMENT);
	}

	/**
	 * 更多Fragment
	 * 
	 * @return
	 */
	public MoreFragment getMoreFragment() {
		return (MoreFragment) fragmentList.get(4);
		//return (MoreFragment) manager.findFragmentByTag(MORE_FRAGMENT);
	}

	public static boolean isToBrandFragment = false;
	public static boolean isToShoppingFragment = false;

	@Override
	protected void onResume() {
		super.onResume();
		if (isToBrandFragment) {
			jumpPager(pagerId[2]);
			BrandFragment.isToCommodityList=true;
			isToBrandFragment=false;
		}
		
	}	
	
	/**
	 * 获取DB 对象
	 * @return
	 */
	//TODO -wx	DbUtils getDbUtils() finish();
	public static DbUtils dbUtils;
	
	public synchronized DbUtils getDbUtils(){
		
		if (dbUtils==null) {
			dbUtils=DbUtils.create(daoConfig);
		}
		return dbUtils;
	}
	
	//TODO -wx 
	@Override
	protected void onDestroy() {
		if (ActivityBrowseData.browseData != null
				&& ActivityBrowseData.browseData.size() != 0) {
			try {
				//数据库只记录20个
				//先查询数据库是否有内容,有的话,就删除
				DBBrowse first = dbUtils.findFirst(DBBrowse.class);
				if (first!=null) {
					dbUtils.deleteAll(DBBrowse.class);
				}
				dbUtils.saveAll(ActivityBrowseData.browseData);
			} catch (DbException e) {
				e.printStackTrace();
				LogPrint.logI("MainActivity", "存储浏览记录失败");
			}
		}
//		getSharedPreferences(MyConstants.ID_XML_NAME, MODE_PRIVATE)
//		.edit().putInt("id", ProductDetailActivity.id);
		
		super.onDestroy();
	}
	
	

}

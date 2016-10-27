package zz.itcast.redbody7.activity;


import zz.itcast.redbody7.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity{

	

	private ImageView barcodeImageView;
	private TextView formatTextView;
	private TextView timeTextView;
	private TextView metaTextView;
	private TextView resultTextView;
	private Bitmap barcodeBitmap;
	private String barcodeFormat;
	private String decodeDate;
	private CharSequence metadataText;
	private String resultString;
	private Bundle bundle;
	private WebView wv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		initView();
		mGetIntentData();
		setView();

	}

	private void setView() {
		barcodeImageView.setImageBitmap(barcodeBitmap);
		formatTextView.setText(barcodeFormat);
		timeTextView.setText(decodeDate);
		metaTextView.setText(metadataText);
		resultTextView.setText(resultString);
		
		wv.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				
				
			}
			
		});
		
		//http://weixin.qq.com/r/Vkz749HEo4zcrZWA9xlE

		System.out.println("======="+resultString);
		wv.loadUrl(resultString);
		
	}

	private void mGetIntentData() {
		bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		barcodeBitmap = bundle.getParcelable("bitmap");
		barcodeFormat = bundle.getString("barcodeFormat");
		decodeDate = bundle.getString("decodeDate");
		metadataText = bundle.getCharSequence("metadataText");
		resultString = bundle.getString("resultString");
	}

	private void initView() {
		barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
		formatTextView = (TextView) findViewById(R.id.format_text_view);
		timeTextView = (TextView) findViewById(R.id.time_text_view);
		metaTextView = (TextView) findViewById(R.id.meta_text_view);
		resultTextView = (TextView) findViewById(R.id.contents_text_view);
		wv = (WebView) findViewById(R.id.contents_web_view);

	}

	public void backCapture(View view) {
		runBack();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			runBack();
		}
		return false;
	}

	public void runBack() {
		Intent intent = new Intent(ResultActivity.this, CaptureActivity.class);
		startActivity(intent);
		ResultActivity.this.finish();
	}
}

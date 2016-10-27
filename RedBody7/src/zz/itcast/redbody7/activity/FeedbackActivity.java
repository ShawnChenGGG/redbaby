package zz.itcast.redbody7.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import zz.itcast.redbody7.R;
import zz.itcast.redbody7.view.TittleBarView;

public class FeedbackActivity extends BaseActivity {
	private TittleBarView tb_feedback_title;

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {

	}

	private EditText et_feedback_input, et_feedback_phone;

	@Override
	public void initView() {

		setContentView(R.layout.activity_feedback);
		et_feedback_input = (EditText) findViewById(R.id.et_feedback_input);
		et_feedback_phone = (EditText) findViewById(R.id.et_feedback_phone);
		tb_feedback_title = (TittleBarView) findViewById(R.id.tb_feedback_title);
		tb_feedback_title.setOperText("提交");

		tb_feedback_title.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		tb_feedback_title.setOnOperClick(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String inputPhone = et_feedback_phone.getText().toString()
						.trim();
				String inputInput = et_feedback_input.getText().toString()
						.trim();
				if (TextUtils.isEmpty(inputInput)
						|| TextUtils.isEmpty(inputPhone)) {
					Toast.makeText(mContext, "输入内容不能为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				Toast.makeText(mContext, "提交成功 ，感谢您的参与", 0).show();
				finish();
			}
		});
		tb_feedback_title.setTittle("关于用户反馈");
		tb_feedback_title.setTextSize(15);
	}

}

package com.geeklub.vass.mc4android.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.feedback.FeedbackAction;
import com.geeklub.vass.mc4android.app.feedback.FeedbackActivity;
import com.geeklub.vass.mc4android.app.feedback.FeedbackRecordActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 */
public class FeedbackFragment extends Fragment{
	 @InjectView(R.id.feedback_contact_edit)
	 EditText mContactEdit = null;
	 @InjectView(R.id.feedback_content_edit)
	EditText mContentEdit = null;
	@InjectView(R.id.left_btn)
	ImageView mLeftBtn = null;
	@InjectView(R.id.right_btn)
	ImageView mRightBtn = null;
	@InjectView(R.id.submit_button)
	Button mSubmitBtn = null;

    public static FeedbackFragment newInstance() {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FeedbackFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main, null);
		ButterKnife.inject(this, view);
		initView();
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN,
				InputMethodManager.HIDE_NOT_ALWAYS);

		return view;
	}


	private void initView() {

		mContentEdit.requestFocus();

		mLeftBtn.setVisibility(View.GONE);
		mRightBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(getActivity(), FeedbackRecordActivity.class);
				startActivity(intent);
			}
		});

		mSubmitBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String content = mContentEdit.getText().toString().trim();
				String contact = mContactEdit.getText().toString().trim();
				if (content.equals("")) {
					Toast.makeText(getActivity(), R.string.request_content, Toast.LENGTH_SHORT).show();
					return;
				}
				SendFeedbackTask task = new SendFeedbackTask(getActivity(), content, contact);
				task.execute("");

			}
		});
	}

	private class SendFeedbackTask extends AsyncTask<Object, Object, Object> {

		private Context mContext = null;
		private String mContact = "";
		private String mContent = "";
		private ProgressDialog mProgDialog = null;

		public SendFeedbackTask(Context context, String content, String contact) {
			mContext = context;
			mContent = content;
			mContact = contact;
		}

		@Override
		protected Object doInBackground(Object... arg0) {
			return Integer.valueOf(new FeedbackAction(mContext)
					.sendFeedbackMessage(mContent, mContact));
		}

		@Override
		protected void onPostExecute(Object result) {
			if (mProgDialog != null) {
				mProgDialog.dismiss();
			}
			int resultCode = ((Integer) result).intValue();
			if (resultCode == 0) {
				Toast.makeText(getActivity(),
						R.string.feedback_success, Toast.LENGTH_SHORT);
				//FeedbackActivity.this.finish();
			} else {
				Toast.makeText(getActivity(), R.string.feedback_failed,
						Toast.LENGTH_SHORT);
			}
			return;
		}

		@Override
		protected void onPreExecute() {
			mProgDialog = new ProgressDialog(getActivity());
			mProgDialog.setMessage(getActivity()
					.getString(R.string.waiting));
			mProgDialog.setCancelable(false);
			mProgDialog.show();
		}

	}
}

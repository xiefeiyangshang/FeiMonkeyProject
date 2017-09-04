package com.whatyplugin.imooc.ui.view;

import java.util.List;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUploadModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailService;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.logic.utils.StringUtils;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.dialog.MCLoadDialog;
import com.whatyplugin.uikit.dialog.MCLoadDialog.TimeCount;
import com.whatyplugin.uikit.toast.MCToast;

public class MCSendTalkDialogFragment extends DialogFragment implements OnClickListener, MCAnalyzeBackBlock {
	private TextView tv_title;
	private EditText ce_EditText;
	private ImageView im_back;
	private ImageView im_send;
	private String title;
	private InputMethodManager imm;
	private Handler handler;
	private TextView tv_write_num;
	private int maxLen = 400;
	private MCCourseDetailService mCourseDetailService;
	private MCStudyServiceInterface mCStudyService;
	private String courseId;
	private MCCommonDialog loading_dialog; // 正在加载
	private MCCreateDialog createDialog = new MCCreateDialog();
	private Dialog dialog_load_error; // 加载错误
	private Dialog dialog_load_success; // 加载成功
	private LinearLayout pic_content_layout;
	private ChoicePicFromLocalView pic_layout;
	private View view;
	private int isQestion;
	
	public ChoicePicFromLocalView getPic_layout() {
		return pic_layout;
	}

	public MCSendTalkDialogFragment(String title, String courseId) {
		this.title = title;
		this.handler = new Handler();
		this.mCourseDetailService = new MCCourseDetailService();
		this.mCStudyService = new MCStudyService();
		this.courseId = courseId;

	}

	private void intView(View view) {
		im_back = (ImageView) view.findViewById(R.id.back);
		tv_title = (TextView) view.findViewById(R.id.title_label);
		tv_title.setText(title);

		this.pic_content_layout = (LinearLayout) view.findViewById(R.id.pic_linearlayout);
		this.pic_layout = (ChoicePicFromLocalView) view.findViewById(R.id.pic_layout);
		this.pic_layout.setContentWrap(this.pic_content_layout);

		pic_layout.setContext(getActivity());
		pic_layout.setVisibility(isQestion);
		ce_EditText = (EditText) view.findViewById(R.id.content);
		im_send = (ImageView) view.findViewById(R.id.send);
		tv_write_num = (TextView) view.findViewById(R.id.write_prompt);
		this.tv_write_num.setText(this.getActivity().getString(R.string.write_prompt,
				new Object[] { Integer.valueOf(400 - this.ce_EditText.getText().toString().length()) }));
		im_back.setOnClickListener(this);
		im_send.setOnClickListener(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
		getDialog().getWindow().setBackgroundDrawableResource(R.color.white);
		getDialog().getWindow().findViewById(R.id.title_layout).setBackgroundResource(R.color.theme_color);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		this.dialog_load_success = MCLoadDialog.createLoadingDialog(getActivity(), getActivity().getString(R.string.dialog_load_success),
				R.drawable.dialog_load_success, null);
		this.dialog_load_error = MCLoadDialog.createLoadingDialog(getActivity(), this.getActivity().getString(R.string.dialog_load_error),
				R.drawable.dialog_load_error, null);
		popupInputMethodWindow();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().setGravity(Gravity.TOP);
		this.view = inflater.inflate(R.layout.share_layout, container, false);
		// 只有答疑才显示  ,用于判断答疑还是笔记
		isQestion = title.equals(getActivity().getString(R.string.course_question_label)) ? View.VISIBLE : View.GONE;
		intView(view);
		return view;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.back) {
			this.imm.hideSoftInputFromWindow(this.view.getWindowToken(), 0);
			MCSendTalkDialogFragment.this.dismiss();
		}

		if (id == R.id.send) {
			String text = this.ce_EditText.getText().toString();
			if (TextUtils.isEmpty(text)) {// 为空
				MCToast.show(getActivity(), this.getResources().getString(R.string.send_null_tip), 0);
				return;
			}
			if (StringUtils.isWhiteSpace(text)) {// 只是符号
				MCToast.show(getActivity(), this.getResources().getString(R.string.send_nullstring_tip), 0);
				return;
			}
			if (text.length() > 400) {// 验证长度
				MCToast.show(getActivity(), this.getResources().getString(R.string.send_longstring_tip), 0);
				return;
			}
			if (text.contains("😏")) {
				MCToast.show(getActivity(), "该功能不支持输入表情！", 0);
				return;
			}
			// 发送笔记的核心service
			if (this.title.toString().equals("笔记")) {
				this.mCourseDetailService.getSendNote(this.courseId, this.ce_EditText.getText().toString(), null, 0, this, getActivity());
			} else {
				if (this.ce_EditText.getText().toString().length() < 4) {
					Toast.makeText(getActivity(), "至少四个字，不解释...", Toast.LENGTH_SHORT).show();
					return;
				} else {
					checkPicAndSendQuestion();
				}
			}
			// 进度提示效果
			loading_dialog = createDialog.createLoadingDialog(this.getActivity(), this.getActivity().getString(R.string.dialog_loading),0);
			return;
		}

	}

	/**
	 * 打开输入法
	 */
	private void popupInputMethodWindow() {
		this.handler.postDelayed(new Runnable() {
			public void run() {
				imm = (InputMethodManager) ce_EditText.getContext().getSystemService("input_method");
				imm.toggleSoftInput(0, 2);
			}
		}, 100);
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {

		long v1 = 2000;
		long v3 = 1000;

		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			if (!MCSendTalkDialogFragment.this.isHidden()) {
				this.ce_EditText.setFocusable(false);
				MCSendTalkDialogFragment.this.dismiss();
			}

			if (this.loading_dialog != null && (this.loading_dialog.isVisible())) {
				this.loading_dialog.dismiss();
			}

			dialog_load_success.show();
			new TimeCount(v1, v3, dialog_load_success).start();
			if (!this.title.equals(this.getResources().getString(R.string.course_note_label))) {
				return;
			}

		} else {
			if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
				if (this.loading_dialog != null && (this.loading_dialog.isVisible())) {
					this.loading_dialog.dismiss();
				}

				this.dialog_load_error.show();
				new TimeCount(v1, v3, this.dialog_load_error).start();
				return;
			}

			if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
				if (this.loading_dialog != null && (this.loading_dialog.isVisible())) {
					this.loading_dialog.dismiss();
				}

				this.dialog_load_error.show();
				new TimeCount(v1, v3, this.dialog_load_error).start();
				return;
			}

			if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
				if (this.loading_dialog != null && (this.loading_dialog.isVisible())) {
					this.loading_dialog.dismiss();
				}

				MCToast.show(this.getActivity(), this.getActivity().getString(R.string.download_nonetwork_label));
				return;
			}

			if (result.isExposedToUser()) {
				return;
			}

			if (this.loading_dialog != null && (this.loading_dialog.isVisible())) {
				this.loading_dialog.dismiss();
			}

			this.dialog_load_error.show();
			new TimeCount(v1, v3, this.dialog_load_error).start();
		}

	}

	public void checkPicAndSendQuestion() {
		final String content = ce_EditText.getText().toString();
		List<String> pathList = this.pic_layout.getAllFilePaths();
		if (pathList != null && pathList.size() > 0) {
			// 上传文件
			mCStudyService.uploadFiles(pathList, new MCAnalyzeBackBlock() {
				@Override
				public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
					if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
						sendQuestionContent(resultList, content);
					} else {
						loading_dialog.dismiss();
						createDialog.createOkDialog(MCSendTalkDialogFragment.this.getActivity(), "附件上传失败……");
					}
				}
			}, getActivity());
		} else {
			sendQuestionContent(null, content);
		}

	}

	// 最终的保存方法
	private void sendQuestionContent(List<MCUploadModel> fileList, String content) {
		this.mCStudyService.sendQuestion(this.courseId, content, fileList, this, getActivity());
	}

}

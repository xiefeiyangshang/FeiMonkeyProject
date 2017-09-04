package com.whatyplugin.imooc.ui.note;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCMyNoteModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailService;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailServiceInterface;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 笔记详情
 * 
 * @author 马彦君
 * 
 */
public class MCNoteDetailActivity extends MCBaseActivity implements View.OnClickListener {
	private int collectnum;
	private Context mContext;
	private MCMyNoteModel note;
	private int praisenum;
	private String userid;
	private ImageView iv_edit;
	private TextView tvNoteName;
	private EditText tvNoteContent;
	private TextView tvNoteUsername;
	private TextView tvNoteTime;
	private ImageView iv_recmd;
	private boolean isEdit = false;
	private TextView tvContent;
	private TextView tvCommit;
	private boolean isDataChange = false;
	private MCCourseDetailServiceInterface service;
	private BaseTitleView titleView;;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private Handler mHandler = new Handler();

	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_edit) {
			isEdit = !isEdit;
			updateEdit(isEdit);
		} else if (id == R.id.tv_commit) {
			commitNote();
		}
	}

	private void commitNote() {
		String content = tvNoteContent.getText().toString();
		if(TextUtils.isEmpty(content)){
			MCToast.show(this, "笔记内容不能为空！");
			return;
		}
		if (service != null) {
			service.updateNote(note.getCourseId(), content, note.getId(), "", new MCAnalyzeBackBlock() {
				@Override
				public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
					if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
						MCToast.show(MCNoteDetailActivity.this, "提交成功");
						isDataChange = true;
						finishWithResult();
						return;
					}
					if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY) {
						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
							MCToast.show(MCNoteDetailActivity.this.mContext, MCNoteDetailActivity.this.mContext.getString(R.string.no_network_label));
							return;
						}
						MCToast.show(MCNoteDetailActivity.this.mContext, "请不要含有特殊字符,如表情符号等");
					}
				}
			}, this);
		}
	}

	private void updateEdit(boolean isEd) {
		if (isEd) {
			tvCommit.setVisibility(View.VISIBLE);
			iv_edit.setVisibility(View.GONE);
			tvContent.setVisibility(View.GONE);
			tvNoteContent.setVisibility(View.VISIBLE);
			tvNoteContent.setFocusable(true);
			tvNoteContent.setFocusableInTouchMode(true);
			tvNoteContent.requestFocus();
			tvNoteContent.requestFocusFromTouch();
			CharSequence text = tvNoteContent.getText();
			if (text instanceof Spannable) {
				Spannable spanText = (Spannable) text;
				Selection.setSelection(spanText, text.length());
			}
			toggleSoftInput();
		} else {
			tvCommit.setVisibility(View.GONE);
			iv_edit.setVisibility(View.VISIBLE);
			tvContent.setVisibility(View.VISIBLE);
			tvNoteContent.setVisibility(View.GONE);
		}
	}

	private void deleteNote(final String id) {
		final MCCommonDialog dialogView = new MCCommonDialog(this.getString(R.string.download_network_title),this.getResources().getString(
				R.string.clear_note_msg),R.layout.network_dialog_layout);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				dialogView.setCommitListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (service != null) {
							service.delNote(id, new MCAnalyzeBackBlock() {
								@Override
								public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
									if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
										MCToast.show(MCNoteDetailActivity.this, "删除成功");
										isDataChange = true;
										finishWithResult();
										return;
									}
									if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY) {
										if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
											MCToast.show(MCNoteDetailActivity.this.mContext, MCNoteDetailActivity.this.mContext.getString(R.string.no_network_label));
											return;
										}
										MCToast.show(MCNoteDetailActivity.this.mContext, "删除失败，请稍候重试");
									}
								}
							}, MCNoteDetailActivity.this);
						}
						dialogView.dismiss();
					}
				});
			}
		}, 200);
		dialogView.show(createDialog.getFragmentTransaction(this), "删除");
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		//判断笔记是不是自己的
		if (this.getIntent().getExtras().containsKey("note")) {
			this.note = (MCMyNoteModel) this.getIntent().getExtras().getSerializable("note");
		}
		boolean isMyNote =note.getUserId().equals(MCSaveData.getUserInfo(Contants.USERID, this).toString());
		this.setContentView(isMyNote? R.layout.activity_mynote_detail_layout:R.layout.activity_note_detail_layout);
		
		initView();
		initEvent();
		initData();
		titleView.findViewById(R.id.right_img).setVisibility(isMyNote?View.VISIBLE:View.GONE);
	}

	private void initData() {
		this.service = new MCCourseDetailService();
		this.mContext = this;
		this.userid = MCSaveData.getUserInfo(Contants.USERID, this.mContext).toString();
		
		updateUI(note);
	}

	private void initEvent() {
		iv_edit.setOnClickListener(this);
		tvCommit.setOnClickListener(this);

		titleView.setRigImageListener(new RightClickListener() {
			@Override
			public void onRightViewClick() {
				deleteNote(note.getId());
			}
		});
		titleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishWithResult();
			}
		});
	}

	private void initView() {
		iv_edit = (ImageView) findViewById(R.id.iv_edit);
		iv_recmd = (ImageView) findViewById(R.id.iv_recmd);
		tvNoteName = (TextView) findViewById(R.id.note_name);
		tvNoteUsername = (TextView) findViewById(R.id.note_user_name);
		tvNoteContent = (EditText) findViewById(R.id.note_content);
		tvContent = (TextView) findViewById(R.id.tv_content);
		tvNoteTime = (TextView) findViewById(R.id.note_time);
		tvCommit = (TextView) findViewById(R.id.tv_commit);
		titleView = (BaseTitleView) this.findViewById(R.id.rl_titile);
	}

	private void toggleSoftInput() {
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (tvNoteContent != null)
			imm.toggleSoftInputFromWindow(tvNoteContent.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void updateUI(MCMyNoteModel myNote) {
		tvNoteName.setText(myNote.getTitle());
		tvNoteContent.setText(Html.fromHtml(myNote.getContent()));
		tvContent.setText(Html.fromHtml(myNote.getContent()));
		if (isEdit) {
			tvNoteContent.setVisibility(View.VISIBLE);
			tvContent.setVisibility(View.GONE);
		} else {
			tvNoteContent.setVisibility(View.GONE);
			tvContent.setVisibility(View.VISIBLE);
		}
		Date date = DateUtil.parseAll(myNote.getCreateDate());
		tvNoteTime.setText(DateUtil.format(date, DateUtil.FORMAT_SHORT));
		tvNoteUsername.setText(myNote.getSsoUserTrueName());
		if (myNote.getUserId().equals(userid)) {
			iv_edit.setVisibility(View.VISIBLE);
		} else {
			iv_edit.setVisibility(View.GONE);
		}
		if (myNote.isTRecommend()){
			iv_recmd.setVisibility(View.VISIBLE);
			titleView.findViewById(R.id.right_img).setVisibility(View.GONE);
			iv_edit.setVisibility(View.GONE);
			
		}
		else
			iv_recmd.setVisibility(View.GONE);

	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == 4) {
			finishWithResult();
		}
		return super.onKeyUp(keyCode, event);
	}

	private void finishWithResult() {
		if (isDataChange) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putInt("notepraise", this.praisenum);
			bundle.putInt("notecollect", this.collectnum);
			bundle.putSerializable("note", this.note);
			intent.putExtras(bundle);
			this.setResult(-1, intent);
		}
		this.finish();
	}
}

package com.whatyplugin.imooc.ui.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.ui.search.MCMyNoteSingleSearchActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
/**
 * 左侧菜单点击出来的按课程分类的笔记某个条目进入后的笔记内容
 * 
 * @author 马彦君
 * 
 */
public class MCMyNoteActivity extends FragmentActivity{

	private MCCourseModel mCourse;
	private Fragment mNoteFragment;
	private BaseTitleView titleView;
	private TextView tvCourseName;
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_mynotelist);
		initView();
		initEvent();
		initData();
	}
	private void initView() {
		titleView = (BaseTitleView) this.findViewById(R.id.rl_titile);
		tvCourseName = (TextView) findViewById(R.id.course_name);
	}
	private void initEvent() {
		titleView.setRigImageListener(new RightClickListener() {
			@Override
			public void onRightViewClick() {
				gotoSearch();
			}
		});
	}
	private void initData() {
		this.mCourse = (MCCourseModel) this.getIntent().getExtras()
				.getSerializable("course");
		mNoteFragment = MCNoteListFragment.newInstance(2, mCourse.getId());
		this.getSupportFragmentManager().beginTransaction().add(R.id.content_frame, this.mNoteFragment).commit();
		tvCourseName.setText(mCourse.getName());
	}
	private void gotoSearch(){
		Intent intent = new Intent(this, MCMyNoteSingleSearchActivity.class);
		intent.putExtra("courseId", mCourse.getId());
		startActivity(intent);
	}
	
}

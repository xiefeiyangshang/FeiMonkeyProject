package com.whatyplugin.imooc.ui.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;

public abstract class MCBaseSearchActivity extends MCBaseListActivity implements OnClickListener {
	protected Context mContext;
	protected SharedPreferences sp;
	protected InputMethodManager imm;
	protected ListView searchListView;
	protected TextView tv_search_cancel;
	protected ImageView iv_search_delete;
	protected RelativeLayout layout_search_delete;
	protected EditText et_search_content;
	protected boolean isClick = false;
	protected List<String> list = new ArrayList<String>();
	protected String hisKey = "";
	private static final String SPLIT_STR = "______";
	protected SearchAdapter sAdapter;

	/**
	 * 子类有自己布局的时候重写此方法
	 * 
	 * @param resultList
	 */
	public int getRootViewId() {
		return R.layout.common_search_layout;
	}

	protected class SearchAdapter extends ArrayAdapter {
		private Context context;
		private List<String> datas;

		public SearchAdapter(MCBaseSearchActivity arg2, Context context, List<String> list) {
			super(context, 0, list);
			this.datas = list;
			this.context = context;
		}

		public int getCount() {
			return this.datas != null ? this.datas.size() : 0;
		}

		public String getItem(int position) {
			return this.datas.get(position);
		}

		public long getItemId(int position) {
			return ((long) position);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			ViewGroup group = null;

			// 没有创建，有的话从tag里取
			if (convertView == null) {
				convertView = LayoutInflater.from(this.context).inflate(R.layout.allcourse_search_item_layout, group);
				holder = new ViewHolder(MCBaseSearchActivity.this);
				holder.layout = (RelativeLayout) convertView.findViewById(R.id.list_item_layout);
				holder.courseName = (TextView) convertView.findViewById(R.id.coursename);
				holder.clearHistory = (TextView) convertView.findViewById(R.id.clear_history);
				holder.nextImage = (ImageView) convertView.findViewById(R.id.next);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String keyWord =  (String) this.datas.get(position);
			if (MCBaseSearchActivity.this.getResources().getString(R.string.clear_search_history).equals(keyWord)) {
				holder.layout.setVisibility(View.GONE);
				holder.clearHistory.setVisibility(View.VISIBLE);
				holder.clearHistory.setText(keyWord);
				holder.nextImage.setVisibility(View.GONE);
			} else {
				holder.layout.setVisibility(View.VISIBLE);
				holder.clearHistory.setVisibility(View.GONE);
				holder.courseName.setText(keyWord);
				holder.nextImage.setVisibility(View.VISIBLE);
			}

			return convertView;
		}
	}

	class ViewHolder {
		TextView clearHistory;
		TextView courseName;
		RelativeLayout layout;
		ImageView nextImage;

		ViewHolder(MCBaseSearchActivity arg1, ViewGroup v7) {
		}

		private ViewHolder(MCBaseSearchActivity arg1) {
			super();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.mContext = ((Context) this);
		this.sp = this.getSharedPreferences(hisKey, 0);
		this.imm = (InputMethodManager) this.getSystemService("input_method");
		this.setRequestWhenCreate(false);
		super.onCreate(savedInstanceState);
		showHistory(null);
		initCommonView();
		
	}

	protected void initCommonView() {
		this.sAdapter = new SearchAdapter(this, this, this.list);
		this.searchListView = (ListView) this.findViewById(R.id.search_listview);
	
		this.searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView adapterView, View view, int position, long id) {
				String model = (String) MCBaseSearchActivity.this.list.get(position);
				if (MCBaseSearchActivity.this.getResources().getString(R.string.clear_search_history).equals(model)) {
					MCBaseSearchActivity.this.list.clear();
					MCBaseSearchActivity.this.sAdapter.notifyDataSetChanged();
					MCBaseSearchActivity.this.clearData();
				} else {
					MCBaseSearchActivity.this.isClick = true;
					MCBaseSearchActivity.this.et_search_content.setText(model);
					MCBaseSearchActivity.this.et_search_content.setSelection((model).length());
					MCBaseSearchActivity.this.mCurrentPage = 1;
					MCBaseSearchActivity.this.reloadData();
					MCBaseSearchActivity.this.saveHistory((model));
				}
			}
		});
		this.searchListView.setAdapter(this.sAdapter);
		this.tv_search_cancel = (TextView) this.findViewById(R.id.search_cancel);
		this.tv_search_cancel.setOnClickListener(this);
		this.layout_search_delete = (RelativeLayout) this.findViewById(R.id.search_delete_layout);
		this.iv_search_delete = (ImageView) this.findViewById(R.id.search_delete);
		this.layout_search_delete.setOnClickListener(((View.OnClickListener) this));
		this.et_search_content = (EditText) this.findViewById(R.id.search_content);
		this.et_search_content.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				MCBaseSearchActivity.this.searchListView.setVisibility(0);
				MCBaseSearchActivity.this.mListView.setVisibility(View.GONE);
				if (MCBaseSearchActivity.this.et_search_content.getText().toString().length() > 0) {
					MCBaseSearchActivity.this.layout_search_delete.setVisibility(0);
					MCBaseSearchActivity.this.searchListView.setVisibility(0);
				} else {
					MCBaseSearchActivity.this.layout_search_delete.setVisibility(View.GONE);
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(final CharSequence s, int start, int before, int count) {
				if (!MCBaseSearchActivity.this.isClick) {
					if (MCBaseSearchActivity.this.adapter != null) {
						MCBaseSearchActivity.this.adapter.clear();
					}

					if (MCBaseSearchActivity.this.isEmpty(s)) {
						MCBaseSearchActivity.this.showHistory(null);
						MCBaseSearchActivity.this.tv_search_cancel.setText(MCBaseSearchActivity.this.mContext.getResources().getString(
								R.string.cancel));
						return;
					}

					MCBaseSearchActivity.this.tv_search_cancel.setText(MCBaseSearchActivity.this.mContext.getResources().getString(R.string.search));
				}
			}
		});

		this.searchListView.setAdapter(this.sAdapter);
	}

	private String getData(String key) {
		String result = this.sp != null ? this.sp.getString(key, "") : null;
		MCLog.d(getTag(), "搜索历史 ： " + key + " = " + result);
		return result;
	}

	protected boolean isEmpty(CharSequence oldString) {
		boolean result = true;
		if (!TextUtils.isEmpty(oldString) && oldString.toString().trim().length() != 0) {
			result = false;
		}

		return result;
	}

	protected String[] getHistory() {
		String[] arr;
		if (this.sp != null) {
			String str = this.getData(hisKey);
			arr = !this.isEmpty(str) ? str.split(SPLIT_STR) : null;
		} else {
			arr = null;
		}

		return arr;
	}

	protected void showHistory(String searchHistory) {
		if (this.isEmpty(searchHistory)) {
			if (this.list != null) {
				this.list.clear();
			}
			String[] arr = this.getHistory();
			if (arr != null) {
				for (int i = 0; i < arr.length; ++i) {
					this.list.add(arr[i]);
					if (i == arr.length - 1) {
						String str = this.getResources().getString(R.string.clear_search_history);
						this.list.add(str);
					}
				}
			} 
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.search_delete_layout) {
			this.et_search_content.setText("");
		} else if (id == R.id.search_cancel) {
			if (this.tv_search_cancel.getText().equals(this.getResources().getString(R.string.cancel))) {
				this.finish();
				return;
			}
			if (!this.tv_search_cancel.getText().equals(this.getResources().getString(R.string.search))) {
				return;
			}
			this.reloadData();
			this.saveHistory(this.et_search_content.getText().toString());
		}
	}

	public void reloadData() {
		this.adapter.clear();
		this.mCurrentPage = 1;

		requestData();
		initLoadingWithTitle();
	}

	@Override
	public void doSomethingWithResult(List resultList) {
		if (this.imm != null && (this.imm.isActive())) {
			this.imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 2);
		}

		if (this.isClick) {
			this.isClick = false;
		}

		this.mListView.setVisibility(View.VISIBLE);
		this.searchListView.setVisibility(View.GONE);
	}

	@Override
	public String getFunctionTitle() {
		return null;
	}

	/**
	 * 保存历史记录
	 * 
	 * @param searchText
	 */
	protected void saveHistory(String searchText) {
		if (!this.isEmpty(searchText) && this.sp != null) {
			String strHistory = this.getData(hisKey);
			StringBuffer strBuffer = new StringBuffer(strHistory);
			boolean hasMore = this.isMore(strHistory);
			searchText = searchText.trim();
			int index = this.cotainsValue(searchText);
			if (index != -1) {
				String[] arr = this.getHistory();
				if (!arr[0].equalsIgnoreCase(searchText)) {
					if (arr[arr.length - 1].equalsIgnoreCase(searchText)) {
						strBuffer.delete(strHistory.lastIndexOf(SPLIT_STR), strHistory.length());
					} else {
						int v6 = 0;
						int v3;
						for (v3 = index - 1; v3 >= 0; --v3) {
							v6 += arr[v3].length();
						}

						int v8 = v6 + index;
						strBuffer.delete(v8, searchText.length() + v8 + 1);
					}

					strBuffer.insert(0, String.valueOf(arr[index]) + SPLIT_STR);
				} else {
					return;
				}
			} else {
				if (hasMore) {
					strBuffer.delete(strHistory.lastIndexOf(SPLIT_STR), strHistory.length());
					strBuffer.insert(0, String.valueOf(searchText) + SPLIT_STR);
					this.clearData();
					this.saveData(hisKey, strBuffer.toString());
					return;
				}

				if (this.getHistory() == null) {
					strBuffer.insert(0, searchText);
					this.clearData();
					this.saveData(hisKey, strBuffer.toString());
					return;
				}

				strBuffer.insert(0, String.valueOf(searchText) + SPLIT_STR);
			}

			this.clearData();
			this.saveData(hisKey, strBuffer.toString());
		}
	}

	protected void clearData() {
		if (this.sp != null) {
			this.sp.edit().clear().commit();
		}
	}

	protected void saveData(String key, String value) {
		if (this.sp != null) {
			this.sp.edit().putString(key, value).commit();
		}
	}

	private int cotainsValue(String value) {
		int result;
		String[] arr = this.getHistory();
		if (arr != null) {
			result = 0;
			while (true) {
				if (result >= arr.length) {
					result = -1;
					break;
				} else if (!arr[result].equalsIgnoreCase(value)) {
					++result;
					continue;
				}

				return result;
			}
		} else {
			result = -1;
		}

		return result;
	}

	protected boolean isMore(String history) {
		boolean isMore;
		if (history == null || !history.contains(SPLIT_STR)) {
			isMore = false;
		} else {
			String[] arr = history.split(SPLIT_STR);
			if (arr == null) {
				isMore = false;
			} else if (arr.length >= 10) {
				isMore = true;
			} else {
				isMore = false;
			}
		}

		return isMore;
	}
}

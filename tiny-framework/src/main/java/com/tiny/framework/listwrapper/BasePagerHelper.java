package com.tiny.framework.listwrapper;

import android.util.Log;

public class BasePagerHelper {
	public static boolean DEBUG = false;
	public final String TAG = this.getClass().getSimpleName();

	public final static int PAGE_SIZE = 20;

	// 总页数
	protected int pageCount;
	// 单页行数
	protected int rowCount;
	// 目前页码
	protected int mCurrentIndex;

	// 记录查询下一页之前的页码，如果查询失败则页码可以回滚
	protected int mPreIndex;

	protected int mFirstPage=0;

	protected boolean isLastPageTag;

	protected int mOffset;


	public BasePagerHelper(){
		this(PAGE_SIZE,0);
	}
	public BasePagerHelper(int rowCount) {
		this(rowCount,0);
	}
	public BasePagerHelper(int rowCount,int mFirstPage){
		this.mFirstPage=mFirstPage;
		this.rowCount = rowCount;
		mCurrentIndex=mFirstPage;
		mOffset=mFirstPage;
		mPreIndex = mFirstPage;
	}


	/**
	 * 得到当前页码
	 * 
	 * @return The current page index;
	 */
	public int getCurrentIndex() {
		return mCurrentIndex;
	}

	public void indexUp() {
		savePreState();
		mCurrentIndex++;
	}

	/**
	 * 
	 * 描述:查询失败时 页码回滚到++之前的
	 * 
	 */
	public void rollBackToPreIndex() {
		mCurrentIndex = mPreIndex;
		Log("rollBackToPreIndex");

	}
	public void setCurrentIndex(int index) {
		savePreState();
		this.mCurrentIndex = index;
	}
	public boolean isLastPage() {
		if (pageCount == 0) {
			return true;
		}
		return mCurrentIndex >= pageCount - (1-mOffset);
	}
	public void setLastPageTag(boolean last){
		this.isLastPageTag=last;
	}
	public boolean isLastPageByTag(){
		return isLastPageTag;
	}
	/**
	 * 得到总页数
	 * 
	 * @return The page count;
	 */
	public int getPageCount() {
		return pageCount;
	}
    public int getRowCount(){
        return rowCount;
    }
	public void resetIndex() {
		savePreState();
		this.mCurrentIndex = mFirstPage;
		Log("resetIndex");

	}
	public void resetPageCount() {
		this.pageCount = 1;
		Log("resetPageCount");
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
		Log("pageCount =" + pageCount);
	}
	public void savePreState() {
		mPreIndex = mCurrentIndex;

	}
	public int getPageSize(){
		return rowCount;
	}
	public void Log(String message) {
		if (DEBUG) {
			Log.d(TAG, message);
		}

	}

}

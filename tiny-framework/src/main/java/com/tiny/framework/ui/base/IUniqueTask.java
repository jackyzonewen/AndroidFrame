package com.tiny.framework.ui.base;

public interface IUniqueTask {
	
	/**
	 * 
	 * 描述:		将事务id包装成唯一id
	 *
	 * @param transactionCode 事务id
	 * @return
	 */
	String getUniqueTaskId(Integer transactionCode);
}

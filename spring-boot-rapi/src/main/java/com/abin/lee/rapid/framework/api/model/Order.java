package com.abin.lee.rapid.framework.api.model;
import java.util.HashMap;
import java.util.Map;

public class Order{
 
	  // 【in 查询使用，形式：1,2,3,4,5】
	 private String ids;
	 // 【排序  使用】
	 private String sortColumns;
	 // 【like  使用】
	 private Map<String, Integer> selectType = new HashMap<String, Integer>();
	 
 
	 /**
     * 描述:主键id     
     * 字段: id  BIGINT(19)  
     */	
	private Long id;

 
	 /**
     * 描述:user_id     
     * 字段: user_id  BIGINT(19)  
     */	
	private Long userId;

 
	 /**
     * 描述:商品id     
     * 字段: business_id  BIGINT(19)  
     */	
	private Long businessId;

 
	 /**
     * 描述:订单名     
     * 字段: order_name  VARCHAR(64)  
     */	
	private String orderName;

 
	 /**
     * 描述:创建时间     
     * 字段: create_time  DATETIME(19)  
     */	
	private java.util.Date createTime;

	//【非数据库字段，查询时使用】
	private java.util.Date createTimeBegin;
	//【非数据库字段，查询时使用】
	private java.util.Date createTimeEnd;
 
	 /**
     * 描述:更新时间     
     * 字段: update_time  DATETIME(19)  
     */	
	private java.util.Date updateTime;

	//【非数据库字段，查询时使用】
	private java.util.Date updateTimeBegin;
	//【非数据库字段，查询时使用】
	private java.util.Date updateTimeEnd;
 
	 /**
     * 描述:版本号     
     * 字段: version  INT(10)  
     */	
	private Integer version;

 
 
	public Order(){
	}

	public Order(
		Long id
	){
		this.id = id;
	}

	public String getIds() {
	    return ids;
	}
	
	public void setIds(String ids) {
	    this.ids = ids;
	}
	
	public String getSortColumns() {
	    return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
	    this.sortColumns = sortColumns;
	}
	
	public Map<String, Integer> getSelectType() {
		return selectType;
	}

	public void setSelectType(Map<String, Integer> selectType) {
		this.selectType = selectType;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
    
    
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
    
    
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	
	public Long getBusinessId() {
		return this.businessId;
	}
	
    
    
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	public String getOrderName() {
		return this.orderName;
	}
	
    
    
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
    public void setCreateTimeBegin(java.util.Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}
	
	public java.util.Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeEnd(java.util.Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	
	public java.util.Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}	
    
    
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
    public void setUpdateTimeBegin(java.util.Date updateTimeBegin) {
		this.updateTimeBegin = updateTimeBegin;
	}
	
	public java.util.Date getUpdateTimeBegin() {
		return this.updateTimeBegin;
	}
	
	public void setUpdateTimeEnd(java.util.Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
	
	public java.util.Date getUpdateTimeEnd() {
		return this.updateTimeEnd;
	}	
    
    
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Integer getVersion() {
		return this.version;
	}
	
    
    
 
}


/*
 * Copyright 2015-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.agiletec.apsadmin.tags;

import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts2.util.SubsetIteratorFilter;
import org.apache.struts2.views.jsp.StrutsBodyTagSupport;
import org.entando.entando.ent.util.EntLogging.EntLogger;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;

import com.agiletec.aps.tags.util.IPagerVO;
import com.agiletec.apsadmin.tags.util.AdminPagerTagHelper;
import com.agiletec.apsadmin.tags.util.ComponentPagerVO;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * This tag is intend for the administration interface only.
 * Takes an iterator and outputs a subset of it.
 * @author E.Santoboni
 */
public class AdminPagerTag extends StrutsBodyTagSupport {

	private static final EntLogger _logger = EntLogFactory.getSanitizedLogger(AdminPagerTag.class);
	
	@Override
	public int doStartTag() throws JspException {
		Object source = this.findValue(this.getSource());
		ServletRequest request =  this.pageContext.getRequest();
		ValueStack stack = this.getStack();
		ComponentPagerVO compPagerVo = new ComponentPagerVO(stack);
		try {
			AdminPagerTagHelper helper = this.getPagerHelper();
			IPagerVO pagerVo = helper.getPagerVO((Collection)source, 
					this.getPagerId(), this.getCount(), this.isAdvanced(), this.getOffset(), request);
			compPagerVo.initPager(pagerVo);
			stack.getContext().put(this.getObjectName(), compPagerVo);
			stack.setValue("#attr['" + this.getObjectName() + "']", compPagerVo, false);
		} catch (Throwable t) {
			_logger.error("Error creating the pager", t);
			//ApsSystemUtils.logThrowable(t, this, "doStartTag");
			throw new JspException("Error creating the pager", t);
		}
		SubsetIteratorFilter subsetIteratorFilter = new SubsetIteratorFilter();
		subsetIteratorFilter.setCount(this.getCount());
		subsetIteratorFilter.setDecider(null);
		subsetIteratorFilter.setSource(source);
		subsetIteratorFilter.setStart(compPagerVo.getBegin());
		subsetIteratorFilter.execute();
		this.setSubsetIteratorFilter(subsetIteratorFilter);
		this.getStack().push(subsetIteratorFilter);
		if (getId() != null) {
			pageContext.setAttribute(getId(), subsetIteratorFilter);
		}
		return EVAL_BODY_INCLUDE;
	}
	
	protected AdminPagerTagHelper getPagerHelper() {
		return new AdminPagerTagHelper();
	}
	
	public String getPagerId() {
		return _pagerId;
	}
	public void setPagerId(String pagerId) {
		this._pagerId = pagerId;
	}
	
	@Override
	public int doEndTag() throws JspException {
		this.getStack().pop();
		this.setSubsetIteratorFilter(null);
		return EVAL_PAGE;
	}
	
	public int getCount() {
		return _count;
	}
	public void setCount(int count) {
		this._count = count;
	}
	
	public String getSource() {
		return _source;
	}
	public void setSource(String source) {
		this._source = source;
	}
	
	public SubsetIteratorFilter getSubsetIteratorFilter() {
		return _subsetIteratorFilter;
	}
	public void setSubsetIteratorFilter(SubsetIteratorFilter subsetIteratorFilter) {
		this._subsetIteratorFilter = subsetIteratorFilter;
	}
	
	protected String getObjectName() {
		return _objectName;
	}
	public void setObjectName(String objectName) {
		this._objectName = objectName;
	}

	protected boolean isAdvanced() {
		return _advanced;
	}
	public void setAdvanced(boolean advanced) {
		this._advanced = advanced;
	}
	
	protected int getOffset() {
		return _offset;
	}
	public void setOffset(int offset) {
		this._offset = offset;
	}
	
	private String _pagerId;
	
	private int _count;
	private String _source;

	private SubsetIteratorFilter _subsetIteratorFilter = null;

	private String _objectName;

	private int _offset;
	private boolean _advanced;

}

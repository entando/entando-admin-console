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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.entando.entando.ent.util.EntLogging.EntLogger;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;

import com.agiletec.apsadmin.tags.util.AutoIndexingTagHelper;
import com.agiletec.apsadmin.tags.util.IAutoIndexingTag;

/**
 * Print the current value of the counter.
 * The counter should be used inside the tabindex attribute in HTML tags that allow the use.
 * @author E.Santoboni
 */
public class CounterTag extends TagSupport implements IAutoIndexingTag {

	private static final EntLogger _logger = EntLogFactory.getSanitizedLogger(CounterTag.class);
	
	public CounterTag() {
		super();
		this.release();
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			String currentCounter = this.getCurrentIndex();
			this.pageContext.getOut().print(currentCounter);
		} catch (Throwable t) {
			_logger.error("error creating (or modifying) counter", t);
			//ApsSystemUtils.logThrowable(t, this, "doStartTag", "error creating (or modifying) counter");
			throw new JspException("error creating (or modifying) counter", t);
		}
		return super.doStartTag();
	}
	
	@Override
	public String getCurrentIndex() {
		return AutoIndexingTagHelper.getCurrentIndex(this, this.pageContext.getRequest());
	}
	
	@Override
	public void release() {
		super.release();
		this._step = 1;
	}
	
	@Override
	public Integer getStep() {
		return _step;
	}
	public void setStep(int step) {
		this._step = step;
	}
	
	@Override
	public Boolean getUseTabindexAutoIncrement() {
		return true;
	}
	
	private Integer _step = 1;
	
}
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
package org.entando.entando.apsadmin.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.entando.entando.aps.system.services.actionlog.IActionLogManager;
import org.entando.entando.aps.system.services.actionlog.model.ActivityStreamSeachBean;
import org.entando.entando.ent.util.EntLogging.EntLogger;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;

import com.agiletec.aps.system.services.group.Group;
import com.agiletec.apsadmin.system.BaseAction;

/**
 * @author S.Loru
 */
public class ActivityStreamAction extends BaseAction{

	private static final EntLogger _logger = EntLogFactory.getSanitizedLogger(ActivityStreamAction.class);
	
	public String viewMore(){
		List<Integer> actionRecordIds = new ArrayList<Integer>();
		try {
			Date timestamp = this.getTimestamp();
			ActivityStreamSeachBean searchBean = new ActivityStreamSeachBean();
			List<Group> userGroups = this.getAuthorizationManager().getUserGroups(this.getCurrentUser());
			searchBean.setUserGroupCodes(groupsToStringCode(userGroups));
			if(timestamp != null){
				timestamp.setTime(timestamp.getTime() - 100);
			}
			searchBean.setEndCreation(timestamp);
			actionRecordIds = this.getActionLogManager().getActionRecords(searchBean);
		} catch (Throwable t) {
			_logger.error("Error on loading more activities", t);
        }
		this.setActionRecordIds(actionRecordIds);
		return SUCCESS;
	}
	
	public String update(){
		List<Integer> actionRecordIds = new ArrayList<Integer>();
		try {
			Date timestamp = this.getTimestamp();
			if(timestamp != null){
				timestamp.setTime(timestamp.getTime() + 100);
			}
			ActivityStreamSeachBean searchBean = new ActivityStreamSeachBean();
			List<Group> userGroups = this.getAuthorizationManager().getUserGroups(this.getCurrentUser());
			searchBean.setUserGroupCodes(groupsToStringCode(userGroups));
			searchBean.setStartUpdate(timestamp);
			searchBean.setEndUpdate(new Date());
			actionRecordIds = this.getActionLogManager().getActionRecords(searchBean);
		} catch (Throwable t) {
			_logger.error("Error on loading updated activities", t);
        }
		this.setActionRecordIds(actionRecordIds);
		return SUCCESS;
	}
	
	private List<String> groupsToStringCode(List<Group> groups) {
		List<String> groupCodes = new ArrayList<String>();
		for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);
			groupCodes.add(group.getName());
		}
		return groupCodes;
	}

	public IActionLogManager getActionLogManager() {
		return _actionLogManager;
	}

	public void setActionLogManager(IActionLogManager actionLogManager) {
		this._actionLogManager = actionLogManager;
	}

	public Date getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this._timestamp = timestamp;
	}

	public List<Integer> getActionRecordIds() {
		return _actionRecordIds;
	}

	public void setActionRecordIds(List<Integer> actionRecordIds) {
		this._actionRecordIds = actionRecordIds;
	}

	
	private IActionLogManager _actionLogManager;
	private Date _timestamp;
	private List<Integer> _actionRecordIds;

}

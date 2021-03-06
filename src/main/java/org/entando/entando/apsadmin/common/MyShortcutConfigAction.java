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

import org.entando.entando.ent.util.EntLogging.EntLogger;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;

import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.agiletec.apsadmin.system.BaseAction;

import org.entando.entando.apsadmin.system.services.shortcut.IShortcutManager;
import org.entando.entando.apsadmin.system.services.shortcut.model.Shortcut;
import org.entando.entando.apsadmin.system.services.shortcut.model.UserConfigBean;

/**
 * Action that manage the shortcut configuration of the current user.
 * @author E.Santoboni
 */
public class MyShortcutConfigAction extends BaseAction {

	private static final EntLogger _logger = EntLogFactory.getSanitizedLogger(MyShortcutConfigAction.class);

	/**
	 * Join a shortcut in the user configuration.
	 * @return The result code.
	 */
	public String joinMyShortcut() {
		if (this.getStrutsAction() != ApsAdminSystemConstants.ADD) {
			this.addFieldError("strutsAction", this.getText("error.myShortcut.invalidAction"));
			return INPUT;
		}
		return this.executeUpdateConfig(this.getPosition(), this.getShortcutCode());
	}
	
	/**
	 * Remove a shortcut from the user configuration.
	 * @return The result code.
	 */
	public String removeMyShortcut() {
		return this.executeUpdateConfig(this.getPosition(), null);
	}
	
	private String executeUpdateConfig(Integer position, String shortcutCode) {
		try {
			String[] config = this.getUserConfig();
			if (null == config) {
				config = new String[this.getUserShortcutsMaxNumber()];
			}
			config[position] = shortcutCode;
			String[] savedConfig = this.getShortcutManager().saveUserConfig(this.getCurrentUser(), config);
			this.setUserConfig(savedConfig);
		} catch (Throwable t) {
			_logger.error("error in executeUpdateConfig", t);
			return FAILURE;
		}
		return SUCCESS;
	}
	
	/**
	 * Swap a shortcut whith other one in the user configuration.
	 * @return The result code.
	 */
	public String swapMyShortcut() {
		try {
			String[] config = this.getUserConfig();
			if (null == config) {
				config = new String[this.getUserShortcutsMaxNumber()];
			}
			String shortcutToMove = config[this.getPositionTarget()];
			config[this.getPositionTarget()] = config[this.getPositionDest()];
			config[this.getPositionDest()] = shortcutToMove;
			String[] savedConfig = this.getShortcutManager().saveUserConfig(this.getCurrentUser(), config);
			this.setUserConfig(savedConfig);
			this.setPositionDest(null);
			this.setPositionTarget(null);
		} catch (Throwable t) {
			_logger.error("error in swapMyShortcut", t);
			return FAILURE;
		}
		return SUCCESS;
	}

	public String[] getUserConfig() {
		return this.getUserConfigBean().getConfig();
	}
	
	public UserConfigBean getUserConfigBean() {
		UserConfigBean config = null;
		try {
			config = (UserConfigBean) this.getRequest().getSession().getAttribute(SESSION_PARAM_MY_SHORTCUTS);
			if (null == config || !this.getCurrentUser().getUsername().equals(config.getUsername())) {
				config = this.getShortcutManager().getUserConfigBean(this.getCurrentUser());
				this.setUserConfigBean(config);
			}
		} catch (Throwable t) {
			_logger.error("Error extracting user config bean by user {}", this.getCurrentUser(), t);
			throw new RuntimeException("Error extracting user config bean by user " + this.getCurrentUser(), t);
		}
		return config;
	}
	
	protected void setUserConfig(String[] config) {
		UserConfigBean userConfig = new UserConfigBean(this.getCurrentUser().getUsername(), config);
		this.setUserConfigBean(userConfig);
	}
	
	protected void setUserConfigBean(UserConfigBean userConfig) {
		this.getRequest().getSession().setAttribute(SESSION_PARAM_MY_SHORTCUTS, userConfig);
	}
	
	public Shortcut getShortcut(String code) {
		return this.getShortcutManager().getShortcut(code);
	}
	
	public boolean isShortcutAllowed(String shortcutCode) {
		Shortcut shortcut = this.getShortcut(shortcutCode);
		if (null != shortcut) {
			String reqPerm = shortcut.getRequiredPermission();
			return (null == reqPerm || this.getAuthorizationManager().isAuthOnPermission(this.getCurrentUser(), reqPerm));
		}
		return false;
	}
	
	public Integer getUserShortcutsMaxNumber() {
		return this.getShortcutManager().getUserShortcutsMaxNumber();
	}
	
	public Integer getStrutsAction() {
		return _strutsAction;
	}
	public void setStrutsAction(Integer strutsAction) {
		this._strutsAction = strutsAction;
	}
	
	public Integer getPosition() {
		return _position;
	}
	public void setPosition(Integer position) {
		this._position = position;
	}
	
	public String getShortcutCode() {
		return _shortcutCode;
	}
	public void setShortcutCode(String shortcutCode) {
		this._shortcutCode = shortcutCode;
	}
	
	public Integer getPositionTarget() {
		return _positionTarget;
	}
	public void setPositionTarget(Integer positionTarget) {
		this._positionTarget = positionTarget;
	}
	
	public Integer getPositionDest() {
		return _positionDest;
	}
	public void setPositionDest(Integer positionDest) {
		this._positionDest = positionDest;
	}
	
	protected IShortcutManager getShortcutManager() {
		return _shortcutManager;
	}
	public void setShortcutManager(IShortcutManager shortcutManager) {
		this._shortcutManager = shortcutManager;
	}

	private Integer _strutsAction;
	private Integer _position;
	private String _shortcutCode;
	
	private Integer _positionTarget;
	private Integer _positionDest;
	
	private IShortcutManager _shortcutManager;
	
	public static final String SESSION_PARAM_MY_SHORTCUTS = "myShortcuts_sessionParam";
	
}
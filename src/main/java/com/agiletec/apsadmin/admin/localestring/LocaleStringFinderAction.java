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
package com.agiletec.apsadmin.admin.localestring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.entando.entando.ent.util.EntLogging.EntLogger;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;

import com.agiletec.aps.system.services.i18n.II18nManager;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.aps.util.ApsProperties;
import com.agiletec.apsadmin.system.BaseAction;

/**
 * This action class implements all the needed methods to handle the Localization Strings
 * @author E.Santoboni
 */
public class LocaleStringFinderAction extends BaseAction {

	private static final EntLogger _logger = EntLogFactory.getSanitizedLogger(LocaleStringFinderAction.class);
	
	public List<String> getLocaleStrings() {
		List<String> labelKeys = new ArrayList<String>();
		try {
			II18nManager i18nManager = this.getI18nManager();
			String text = this.getText();
			String searchOption = this.getSearchOption();
			if (null==text || text.trim().length()==0) {
				labelKeys.addAll(i18nManager.getLabelGroups().keySet());
			} else if (searchOption==null || searchOption.length()==0 || searchOption.equals("all")) {
				labelKeys = i18nManager.searchLabelsKey(text, false, false, null);
			} else if (searchOption.equals("labelkey")) {
				labelKeys = i18nManager.searchLabelsKey(text, true, false, null);
			} else {
				labelKeys = i18nManager.searchLabelsKey(text, false, true, searchOption);
			}
			if (null != labelKeys) {
				Collections.sort(labelKeys);
			}
		} catch (Exception e) {
			_logger.error("error in getLocaleStrings", e);
		}
		return labelKeys;
	}
	
	public List<Lang> getSystemLangs() {
		return this.getLangManager().getLangs();
	}
	
	public Map<String, ApsProperties> getLabels() {
		return this.getI18nManager().getLabelGroups();
	}
	
	public String getText() {
		return _text;
	}
	public void setText(String text) {
		this._text = text;
	}
	
	public String getSearchOption() {
		return _searchOption;
	}
	public void setSearchOption(String searchOption) {
		this._searchOption = searchOption;
	}
	
	protected II18nManager getI18nManager() {
		return _i18nManager;
	}
	public void setI18nManager(II18nManager i18nManager) {
		_i18nManager = i18nManager;
	}
	
	private String _text;
	private String _searchOption;
	
	private II18nManager _i18nManager;
	
}
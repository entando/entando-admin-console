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
package com.agiletec.apsadmin.admin.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.apsadmin.ApsAdminBaseTestCase;
import com.opensymphony.xwork2.Action;
import org.junit.jupiter.api.Test;

/**
 * @version 1.0
 * @author E.Mezzano
 */
class TestLangFinderAction extends ApsAdminBaseTestCase {
	
	@Test
	void testList() throws Throwable {
		String result = this.executeList("admin");
		assertEquals(Action.SUCCESS, result);
		LangFinderAction langFinderAction = (LangFinderAction) this.getAction();
		
		List<Lang> langs = langFinderAction.getLangs();
		assertEquals(2, langs.size());
		List<Lang> assignableLangs = langFinderAction.getAssignableLangs();
		assertFalse(assignableLangs.isEmpty());
	}
	
	private String executeList(String userName) throws Throwable {
		this.setUserOnSession(userName);
		this.initAction("/do/Lang", "list");
		String result = this.executeAction();
		return result;
	}
	
}
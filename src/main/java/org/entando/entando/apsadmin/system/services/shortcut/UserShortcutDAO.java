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
package org.entando.entando.apsadmin.system.services.shortcut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.entando.entando.ent.exception.EntException;
import org.entando.entando.ent.util.EntLogging.EntLogger;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;

import com.agiletec.aps.system.common.AbstractDAO;


/**
 * Data Access Object for the configuration of user shortcut.
 * @author E.Santoboni
 */
public class UserShortcutDAO extends AbstractDAO implements IUserShortcutDAO {
	
	private static final EntLogger _logger = EntLogFactory.getSanitizedLogger(UserShortcutDAO.class);
	
	@Override
	public void saveUserConfig(String username, String config) {
		Connection conn = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			this.deleteUserConfigRecord(username, conn);
			this.addUserConfigRecord(username, config, conn);
			conn.commit();
		} catch (Throwable t) {
			_logger.error("Error deleting user config by user {}", username,  t);
			throw new RuntimeException("Error deleting user config by user " + username, t);
			//this.processDaoException(t, "Error deleting user config by user " + username, "deleteUserConfig");
		} finally {
			this.closeConnection(conn);
		}
	}
	
	private void addUserConfigRecord(String username, String config, Connection conn) {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(ADD_CONFIG);
			stat.setString(1, username);
			stat.setString(2, config);
			stat.executeUpdate();
		} catch (Throwable t) {
			_logger.error("Error adding user config record",  t);
			throw new RuntimeException("Error adding user config record", t);
			//this.processDaoException(t, "Error adding user config record", "addUserConfigRecord");
		} finally {
			this.closeDaoResources(null, stat);
		}
	}
	
	@Override
	public void deleteUserConfig(String username) {
		Connection conn = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			this.deleteUserConfigRecord(username, conn);
			conn.commit();
		} catch (Throwable t) {
			_logger.error("Error deleting user config by user {}", username,  t);
			throw new RuntimeException("Error deleting user config by user " + username, t);
			//this.processDaoException(t, "Error deleting user config by user " + username, "deleteUserConfig");
		} finally {
			this.closeConnection(conn);
		}
	}
	
	private void deleteUserConfigRecord(String username, Connection conn) {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(DELETE_CONFIG);
			stat.setString(1, username);
			stat.executeUpdate();
		} catch (Throwable t) {
			_logger.error("Error deleting user config record by id {}", username,  t);
			throw new RuntimeException("Error deleting user config record by id " + username, t);
			//this.processDaoException(t, "Error deleting user config record by id " + username, "deleteUserConfigRecord");
		} finally {
			this.closeDaoResources(null, stat);
		}
	}

	@Override
	public String loadUserConfig(String username) {
		Connection conn = null;
		String config = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(LOAD_CONFIG);
			stat.setString(1, username);
			res = stat.executeQuery();
			if (res.next()) {
				config = res.getString(1);
			}
		} catch (Throwable t) {
			_logger.error("Error loading short cut config by user {}", username,  t);
			throw new RuntimeException("Error loading short cut config by user " + username, t);
			//this.processDaoException(t, "Error loading short cut config by user " + username, "loadUserConfig");
		} finally {
			this.closeDaoResources(res, stat, conn);
		}
		return config;
	}
	
	private final String LOAD_CONFIG = 
		"SELECT config FROM authusershortcuts WHERE username = ?";
	
	private final String DELETE_CONFIG = 
		"DELETE FROM authusershortcuts WHERE username = ?";
	
	private final String ADD_CONFIG = 
		"INSERT INTO authusershortcuts(username, config) VALUES (?, ?)";
	
}
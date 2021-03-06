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
package com.agiletec.apsadmin.user.group.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.entando.entando.ent.exception.EntException;
import org.entando.entando.ent.util.EntLogging.EntLogger;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;


import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.group.GroupUtilizer;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.apsadmin.system.BaseActionHelper;

/**
 * Classe action helper della gestione Gruppi.
 * @version 1.0
 * @author E.Santoboni
 */
public class GroupActionHelper extends BaseActionHelper implements IGroupActionHelper {

    private static final EntLogger _logger = EntLogFactory.getSanitizedLogger(GroupActionHelper.class);
    
    @Override
    public Map<String, List<Object>> getReferencingObjects(Group group, HttpServletRequest request) throws EntException {
        Map<String, List<Object>> references = new HashMap<String, List<Object>>();
        try {
            String[] defNames = ApsWebApplicationUtils.getWebApplicationContext(request).getBeanNamesForType(GroupUtilizer.class);
            for (int i=0; i<defNames.length; i++) {
                Object service = null;
                try {
                    service = ApsWebApplicationUtils.getWebApplicationContext(request).getBean(defNames[i]);
                } catch (Throwable t) {
                    _logger.error("error in hasReferencingObjects", t);
                    //ApsSystemUtils.logThrowable(t, this, "hasReferencingObjects");
                    service = null;
                }
                if (service != null) {
                    GroupUtilizer groupUtilizer = (GroupUtilizer) service;
                    List<Object> utilizers = groupUtilizer.getGroupUtilizers(group.getName());
                    if (utilizers != null && !utilizers.isEmpty()) {
                        //FIXME UNIFORMARE E STUDIARE CHIAVE IDONEA
                        references.put(groupUtilizer.getName()+"Utilizers", utilizers);
                    }
                }
            }
        } catch (Throwable t) {
            throw new EntException("Errore in getReferencingObjects", t);
        }
        return references;
    }
    
}

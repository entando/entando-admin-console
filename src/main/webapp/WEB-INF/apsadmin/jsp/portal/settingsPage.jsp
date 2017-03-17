<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/aps-core" prefix="wp" %>
<%@ taglib uri="/apsadmin-core" prefix="wpsa" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<ol class="breadcrumb page-tabs-header breadcrumb-position">
    <li><s:text name="title.pageDesigner" /></li>
    <li class="page-title-container"><s:text name="title.settingsPage" /></li>
</ol>

<h1 class="page-title-container"><s:text name="title.settingsPage" /></h1>

<div id="main" role="main">
    <s:form action="updateSystemParams">
        <s:if test="hasActionMessages()">
            <div class="alert alert-success alert-dismissable fade in">
                <button class="close" data-dismiss="alert"><span class="icon fa fa-times"></span></button>
                <h2 class="h4 margin-none"><s:text name="messages.confirm" /></h2>
                <ul class="margin-base-top">
                    <s:iterator value="actionMessages">
                        <li><s:property escapeHtml="false" /></li>
                        </s:iterator>
                </ul>
            </div>
        </s:if>



        <fieldset class="col-xs-12 settings-form">

            <div class="form-group">
                <div class="row">
                    <div class="col-xs-2">
                        <div class="form-group-label"><s:text name="name.pages" /></div>
                    </div>
                    <div class="col-xs-10">
                        <div class="form-group-separator"><s:text name="label.requiredFields" /></div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <div class="col-xs-2 col-label">
                        <span for="admin-settings-area-homePageCode"><s:text name="sysconfig.homePageCode" /></span>
                        <i class="fa fa-asterisk required-icon"></i>
                    </div>
                    <div class="col-xs-10">
                        <s:set name="paramName" value="'homePageCode'" />
                        <s:include value="/WEB-INF/apsadmin/jsp/admin/selectPageParamBlock.jsp" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <div class="col-xs-2 col-label">
                        <span for="admin-settings-area-notFoundPageCode"><s:text name="sysconfig.notFoundPageCode" /></span>
                        <i class="fa fa-asterisk required-icon"></i>
                    </div>
                    <div class="col-xs-10">
                        <s:set name="paramName" value="'notFoundPageCode'" />
                        <s:include value="/WEB-INF/apsadmin/jsp/admin/selectPageParamBlock.jsp" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <div class="col-xs-2 col-label">
                        <span for="admin-settings-area-errorPageCode"><s:text name="sysconfig.errorPageCode" /></span>
                        <i class="fa fa-asterisk required-icon"></i>
                    </div>
                    <div class="col-xs-10">
                        <s:set name="paramName" value="'errorPageCode'" />
                        <s:include value="/WEB-INF/apsadmin/jsp/admin/selectPageParamBlock.jsp" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <div class="col-xs-2 col-label">
                        <span for="admin-settings-area-loginPageCode"><s:text name="sysconfig.loginPageCode" /></span>
                        <i class="fa fa-asterisk required-icon"></i>
                    </div>
                    <div class="col-xs-10">
                        <s:set name="paramName" value="'loginPageCode'" />
                        <s:include value="/WEB-INF/apsadmin/jsp/admin/selectPageParamBlock.jsp" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <s:set name="paramName" value="'baseUrl'" />
                    <div class="col-xs-2 col-label">
                        <span class="display-block"><s:text name="sysconfig.baseURL" /></span>
                    </div>
                    <div class="col-xs-4">
                        <div class="btn-group" data-toggle="buttons">
                            <label class="btn btn-default <s:if test="%{systemParams[#paramName].equals('relative')}">active</s:if>">
                                <input type="radio" class="radiocheck" id="admin-settings-area-urlStyle-relative" name="<s:property value="#paramName"/>" value="relative" <s:if test="%{systemParams[#paramName].equals('relative')}">checked="checked"</s:if> />
                                <s:text name="baseURL.relative" />
                            </label>
                            <label class="btn btn-default <s:if test="%{systemParams[#paramName].equals('request')}">active</s:if>">
                                <input type="radio" class="radiocheck" id="admin-settings-area-urlStyle-baseUrl" name="<s:property value="#paramName"/>" value="request" <s:if test="%{systemParams[#paramName].equals('request')}">checked="checked"</s:if> />
                                <s:text name="baseURL.request" />
                            </label>
                            <label class="btn btn-default <s:if test="%{systemParams[#paramName].equals('static')}">active</s:if>">
                                <input type="radio" class="radiocheck" id="admin-settings-area-urlStyle-static" name="<s:property value="#paramName"/>" value="static" <s:if test="%{systemParams[#paramName].equals('static')}">checked="checked"</s:if> />
                                <s:text name="baseURL.static" />
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <div class="col-xs-4 col-label">
                        <span class="display-block"><s:text name="sysconfig.baseURL.contextName" /></span>
                    </div>
                    <div class="col-xs-2">
                        <s:set name="paramName" value="'baseUrlContext'" />
                        <input type="hidden" 
                               value="<s:property value="systemParams[#paramName]" />"
                               id="<s:property value="#paramName"/>" 
                               name="<s:property value="#paramName"/>" />
                        <input 
                            type="checkbox" 	
                            value="<s:property value="systemParams[#paramName]" />"
                            id="ch_<s:property value="#paramName"/>" 
                            class="bootstrap-switch" 
                            <s:if test="systemParams[#paramName] == 'true'">checked="checked"</s:if> >
                        </div>

                        <div class="col-xs-4 col-label">
                            <span class="display-block"><s:text name="sysconfig.useJsessionId" /></span>
                    </div>
                    <div class="col-xs-2">
                        <s:set name="paramName" value="'useJsessionId'" />
                        <input type="hidden" 
                               value="<s:property value="systemParams[#paramName]" />"
                               id="<s:property value="#paramName"/>" 
                               name="<s:property value="#paramName"/>" />
                        <input 
                            type="checkbox" 	
                            value="<s:property value="systemParams[#paramName]" />"
                            id="ch_<s:property value="#paramName"/>" 
                            class="bootstrap-switch" 
                            <s:if test="systemParams[#paramName] == 'true'">checked="checked"</s:if> >
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-xs-4 col-label">
                            <span class="display-block"><s:text name="sysconfig.lang.browser" /></span>
                    </div>
                    <div class="col-xs-2">
                        <s:set name="paramName" value="'startLangFromBrowser'" />
                        <input type="hidden" 
                               value="<s:property value="systemParams[#paramName]" />"
                               id="<s:property value="#paramName"/>" 
                               name="<s:property value="#paramName"/>" />
                        <input 
                            type="checkbox" 	
                            value="<s:property value="systemParams[#paramName]" />"
                            id="ch_<s:property value="#paramName"/>" 
                            class="bootstrap-switch" 
                            <s:if test="systemParams[#paramName] == 'true'">checked="checked"</s:if> >
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                    <s:set name="paramName" value="'urlStyle'" />
                    <div class="col-xs-2 col-label">
                        <span class="display-block"><s:text name="sysconfig.URLstyle" /></span>
                    </div>
                    <div class="col-xs-4">
                        <div class="btn-group" data-toggle="buttons">
                            <label class="btn btn-default <s:if test="systemParams['urlStyle'] == 'classic'"> active</s:if>">
                                <input type="radio" class="radiocheck" id="admin-settings-area-urlStyle-classic" name="urlStyle" value="classic" <s:if test="systemParams['urlStyle'] == 'classic'">checked="checked"</s:if> />
                                <s:text name="URLstyle.classic" />
                            </label>
                            <label class="btn btn-default <s:if test="systemParams['urlStyle'] == 'breadcrumbs'"> active</s:if>">
                                <input type="radio" class="radiocheck" id="admin-settings-area-urlStyle-breadcrumbs" name="urlStyle" value="breadcrumbs" <s:if test="systemParams['urlStyle'] == 'breadcrumbs'">checked="checked"</s:if> />
                                <s:text name="URLstyle.breadcrumbs" />
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <s:set name="paramName" value="'treeStyle_page'" />
                    <div class="col-xs-2 col-label">
                        <span class="display-block"><s:text name="sysconfig.chooseYourPagesTreeStyle" /></span>
                    </div>
                    <div class="col-xs-4">
                        <div class="btn-group" data-toggle="buttons">

                            <label class="btn btn-default <s:if test="systemParams[#paramName] == 'classic'"> active</s:if>">
                                <input type="radio" class="radiocheck" id="admin-settings-area-<s:property value="#paramName"/>_classic" name="<s:property value="#paramName"/>" value="classic" <s:if test="systemParams[#paramName] == 'classic'">checked="checked"</s:if> />
                                <s:text name="URLstyle.classic" />
                            </label>
                            <label class="btn btn-default <s:if test="systemParams[#paramName] == 'request'"> active</s:if>">
                                <input type="radio" class="radiocheck" id="admin-settings-area-<s:property value="#paramName"/>_request" name="<s:property value="#paramName"/>" value="request" <s:if test="systemParams[#paramName] == 'request'">checked="checked"</s:if> />
                                <s:text name="treeStyle.request" />
                            </label>
                        </div>
                    </div>

                </div>
            </div>
        </fieldset>

        <div class="form-group bottom-row">
            <div class="row">
                <div class="col-xs-12">
                    <wpsf:submit type="button" cssClass="btn btn-primary btn-block pull-right">
                        <span class="icon fa fa-floppy-o"></span>&#32;
                        <s:text name="label.save" />
                    </wpsf:submit>
                </div>
            </div>
        </div>

    </s:form>

</div>

<script type="application/javascript" >
    $('input[type="checkbox"][id^="ch_"]').on('switchChange.bootstrapSwitch', function (ev, data) {
    var id = ev.target.id.substring(3);
    console.log("id", id);
    var $element = $('#'+id);
    $element.attr('value', ''+data);
    });
</script>



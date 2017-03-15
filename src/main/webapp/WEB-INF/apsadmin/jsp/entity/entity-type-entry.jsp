<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/apsadmin-core" prefix="wpsa" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>

<ol class="breadcrumb page-tabs-header breadcrumb-position">
    <li>
        <a href="<s:url action="initViewEntityTypes" namespace="/do/Entity"><s:param name="entityManagerName"><s:property value="entityManagerName" /></s:param></s:url>" title="<s:text name="note.goToSomewhere" />: <s:text name="title.entityAdmin.manager" />&#32;<s:property value="entityManagerName" />">
            <s:text name="%{'title.' + entityManagerName + '.management'}" />
        </a>
    </li>
    <s:if test="operationId == 1">
        <li class="page-title-container">
            <s:text name="title.entityTypes.editType.new" />
        </li>
    </s:if>
    <s:else>
        <li class="page-title-container">
            <s:text name="title.entityTypes.editType.edit" />
        </li>
    </s:else>
</ol>

<h1 class="page-title-container">
    <s:if test="operationId == 1">
        <s:text name="title.entityTypes.editType.new" />
    </s:if>
    <s:else>
        <s:text name="title.entityTypes.editType.edit" />
    </s:else>
    <span class="pull-right">
        <a tabindex="0" role="button" data-toggle="popover" data-trigger="focus" data-html="true" title="" data-content="TO be inserted" data-placement="left" data-original-title=""><i class="fa fa-question-circle-o" aria-hidden="true"></i></a>
    </span>
</h1>

<div class="text-right">
    <div class="form-group-separator"> <s:text name="note.entityTypes.editType.intro.2" /></div>
</div>


<div id="main" role="main">

    <s:if test="hasFieldErrors()">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                <span class="pficon pficon-close"></span>
            </button>
            <span class="pficon pficon-error-circle-o"></span>
            <h2><s:text name="message.title.FieldErrors" /></h2>
            <ul>
                <s:iterator value="fieldErrors">
                    <s:iterator value="value">
                        <li><s:property escape="false" /></li>
                        </s:iterator>
                    </s:iterator>
            </ul>
        </div>
    </s:if>


    <s:form action="saveEntityType" cssClass="form-horizontal">

        <s:set var="entityType" value="entityType" />
        <s:if test="operationId != 1">
            <p class="sr-only">	
                <wpsf:hidden name="entityTypeCode" value="%{#entityType.typeCode}" />
            </p>
        </s:if>

        <fieldset class="col-xs-12">

            <legend>
                <s:text name="label.info" />
            </legend>

            <div class="form-group">
                <s:if test="operationId == 1">
                    <label class="col-sm-2 control-label" for="entityTypeCode"><s:text name="label.code" />
                        <a role="button" tabindex="0" data-toggle="popover" data-trigger="focus" data-html="true" title="" data-placement="top" data-content="to be inserted   " data-original-title="">
                            <span class="fa fa-info-circle"></span></a>
                    </label>
                    <div class="col-sm-10">
                        <wpsf:textfield name="entityTypeCode" id="entityTypeCode" value="%{#entityType.typeCode}" cssClass="form-control" />
                    </div>
                </s:if>
                <s:else>
                    <label class="col-sm-2 control-label" for="entityTypeCode"><s:text name="label.code" />
                        <a role="button" tabindex="0" data-toggle="popover" data-trigger="focus" data-html="true" title="" data-placement="top" data-content="to be inserted   " data-original-title="">
                            <span class="fa fa-info-circle"></span></a>
                    </label>
                    <div class="col-sm-10">
                        <wpsf:textfield name="entityTypeCode" id="entityTypeCode" value="%{#entityType.typeCode}" cssClass="form-control" disabled="true" />	
                    </div>
                </s:else>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"><s:text name="label.description" />
                    <a role="button" tabindex="0" data-toggle="popover" data-trigger="focus" data-html="true" title="" data-placement="top" data-content="to be inserted   " data-original-title="">
                        <span class="fa fa-info-circle"></span></a>
                </label>
                <div class="col-sm-10">
                    <wpsf:textfield name="entityTypeDescription" id="entityTypeDescription" value="%{#entityType.typeDescr}" cssClass="form-control" />
                </div>
            </div>
        </fieldset>

        <%-- 
                hookpoint for meta-info and the like
                allowed Plugins: jacms (but so far we have not a check on this)
                
                Based on the Plugin Pattern, we can calculate a proper path for this inclusion
                /WEB-INF/plugins/<plugin_code>/apsadmin/jsp/entity/include/entity-type-entry.jsp 
        --%>
        <s:if test="null != #hookpoint_plugin_code">
            <s:include value="%{'/WEB-INF/plugins/' + #hookpoint_plugin_code + '/apsadmin/jsp/entity/include/entity-type-entry.jsp'}" />
        </s:if>

        <!--Attributes-->

        <fieldset class="col-xs-12">
            <legend>
                <s:text name="label.attributes" />
            </legend>

            <s:include value="/WEB-INF/apsadmin/jsp/entity/include/attribute-operations-add.jsp" />
            <s:include value="/WEB-INF/apsadmin/jsp/entity/include/attribute-list.jsp" />
        </fieldset>
        <fieldset class="col-xs-12">

            <div class="form-group">
                <div class="col-xs-12">
                    <wpsf:submit type="button" cssClass="btn btn-primary  pull-right " action="saveEntityType" >
                        <s:text name="label.save" />
                    </wpsf:submit>
                </div>
            </div>
        </fieldset>
    </div>

</s:form>


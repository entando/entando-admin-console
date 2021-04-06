<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<s:set var="currentSize" value="screenSize" />

<c:set var="currentUsernameVar" value="${sessionScope.currentUser}" />
<s:set var="currentUsernameVar" value="#attr.currentUsernameVar" />
<c:if test="${null != sessionScope.currentUser.profile}">
    <c:set var="currentUsernameVar" value="${sessionScope.currentUser.profile.displayName}" />
</c:if>

<s:set var="langstr">[<s:iterator value="langs" status="langstatus">{"code": "<s:property value="code" />", "descr": "<s:property value="descr" />"}<s:if test="!#langstatus.last">,</s:if></s:iterator>]</s:set>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/css/pages/previewPage.css"/>
    <link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/preview-components/static/css/main.d69a348f.chunk.css"/>
    <link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/preview-components/static/css/2.416eeee3.chunk.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script>
        var PROPERTY = {
            baseUrl: '<wp:info key="systemParam" paramName="applicationBaseURL" />',
            lang: '<s:property value="lang" />',
            token: '<s:property value="token" />',
            pageCode: '<s:property value="pageCode" />',
            previewWidth: '<s:property value="#currentSize.width" />',
            previewHeight: '<s:property value="#currentSize.height" />',
            languages: '<s:property value="langstr" />',
        };
    </script>
    <script src="<wp:resourceURL />administration/preview-components/static/js/2.7e154929.chunk.js"></script>
    <script src="<wp:resourceURL />administration/preview-components/static/js/main.7cc52bb1.chunk.js"></script>
    <script src="<wp:resourceURL />administration/preview-components/static/js/runtime-main.4b714230.js"></script>
    <script src="<wp:resourceURL />administration/js/pages/previewPage.js"></script>
</head>
<body>
    <preview-control-bar
        id="controlBar"
        app-builder-domain="<wp:info key="systemParam" paramName="appBuilderBaseURL" />"
        resolution-width="<s:property value="#currentSize.width" />"
        resolution-height="<s:property value="#currentSize.height" />"
        user-logged="${currentUsernameVar}"
        languages="<s:property value="langstr" escapeHtml="true" />"
        current-lang="<s:property value="lang" />"
    ></preview-control-bar>
    <div class="main-container">
        <div class="preview-area">
            <iframe id="previewFrame">
            </iframe>
        </div>
        <preview-comments-bar></preview-comments-bar>
    </div>
</body>
</html>



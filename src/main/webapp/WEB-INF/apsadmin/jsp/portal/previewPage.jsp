<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<s:set var="currentSize" value="screenSize" />

<c:set var="currentUsernameVar" value="${sessionScope.currentUser}" />
<s:set var="currentUsernameVar" value="#attr.currentUsernameVar" />
<c:if test="${null != sessionScope.currentUser.profile}">
    <c:set var="currentUsernameVar" value="${sessionScope.currentUser.profile.displayName}" />
</c:if>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/css/pages/previewPage.css"/>
    <link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/preview-components/static/css/main.629865d4.chunk.css"/>
    <link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/preview-components/static/css/2.416eeee3.chunk.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script>
        var PROPERTY = {
            baseUrl: '<wp:info key="systemParam" paramName="applicationBaseURL" />',
            lang: '<s:property value="lang" />',
            token: '<s:property value="token" />',
            pageCode: '<s:property value="pageCode" />',
            previewWidth: '<s:property value="#currentSize.width" />',
            previewHeight: '<s:property value="#currentSize.height" />'
        };
    </script>
    <script src="<wp:resourceURL />administration/preview-components/static/js/2.01347d7d.chunk.js"></script>
    <script src="<wp:resourceURL />administration/preview-components/static/js/main.ddbb2e71.chunk.js"></script>
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
    ></preview-control-bar>
    <div class="main-container">
        <iframe id="previewFrame">
        </iframe>
    </div>
</body>
</html>



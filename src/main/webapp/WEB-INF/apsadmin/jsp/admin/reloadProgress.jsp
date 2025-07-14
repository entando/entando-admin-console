<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<s:include value="/WEB-INF/apsadmin/jsp/admin/inc/reload-menu-intro.jsp" />

<div class="text-center">
    <wp:ifauthorized permission="superuser">
        <i class="fa fa-question esclamation-big" aria-hidden="true"></i>
        <p class="esclamation-underline"><s:text name="menu.reload.verify"/></p>
        <p>
            <s:text name="label.reload.message.check"/>
        </p>
        <div class="btn button-fixed-width">
            <a id="link-reload-action" href="<s:url namespace="/do/BaseAdmin" action="reloadStatus" />" class="btn-primary button-fixed-width">
                <s:text name="label.reload.check" />
            </a>
        </div>
    <div>
        <h3><s:text name="label.reload.progress"/>:&nbsp;<b><s:property value="%{getReloadProgress()}"/>%</b></h3>
    </div>

    </wp:ifauthorized>
</div>

<script>
  window.addEventListener('DOMContentLoaded', function () {
    const reloadLink = document.getElementById('link-reload-action');

    if (reloadLink) {
      setInterval(function () {
        reloadLink.click();
      }, 2000);
    } else {
      console.warn('Il link con ID "link-reload-action" non è stato trovato.');
    }
  });
</script>

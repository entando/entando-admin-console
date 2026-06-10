<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<s:include value="/WEB-INF/apsadmin/jsp/admin/inc/reload-menu-intro.jsp" />

<div class="text-center">
    <wp:ifauthorized permission="superuser">
        <i class="fa fa-question esclamation-big" aria-hidden="true"></i>
        <p class="esclamation-underline">
            <s:text name="menu.reload.verify"/>
        </p>
        <p>
            <s:text name="label.reload.message.check"/>
        </p>
        <div class="btn button-fixed-width">
            <a id="link-reload-action" href="<s:url namespace="/do/BaseAdmin" action="reloadStatus" />" class="btn-primary button-fixed-width">
                <s:text name="label.reload.check" />
            </a>
        </div>
        <div id="show-reload-progress" style="max-width: 480px; margin: 20px auto 0;">
            <p style="margin-bottom: 6px;">
                <s:text name="label.reload.progress"/>
                <i id="reload-spinner" class="fa fa-spinner fa-spin" aria-hidden="true" style="display:none; margin-left: 6px;"></i>
            </p>
            <div class="progress progress-striped active" title="0%">
                <div id="reload-progress-bar" class="progress-bar" role="progressbar"
                     aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
                     style="width: 0%; min-width: 2em;">
                    <span id="reload-progress-label">0%</span>
                </div>
            </div>
            <div id="reload-connection-error" class="alert alert-danger" style="display:none; margin-top: 16px; text-align: left;">
                <span class="pficon pficon-error-circle-o"></span>
                <strong><s:text name="messages.error" /></strong>&#32;&ndash;
                <s:text name="message.reload.connectionError" />
            </div>
        </div>

    </wp:ifauthorized>
</div>

<script>
  $(document).ready(function () {
    let reloadUrl = "<s:url namespace='/do/BaseAdmin' action='reloadStatusJson' />";

    function updateBar(pct) {
      $('#reload-progress-bar').css('width', pct + '%').attr('aria-valuenow', pct);
      $('#reload-progress-label').text(pct + '%');
      $('#show-reload-progress .progress').attr('title', pct + '%');
    }

    function showConnectionError() {
      clearInterval(pollInterval);
      $('#reload-progress-bar').removeClass('active');
      $('#show-reload-progress .progress').removeClass('active');
      $('#reload-connection-error').show();
    }

    function refreshProgress() {
      $('#reload-spinner').show();
      $.getJSON(reloadUrl, function (data) {
        let progress = parseInt(data, 10);
        if (!isNaN(progress)) {
          if (progress === -1) {
            updateBar(100);
            window.location.href = $('#link-reload-action').attr('href');
          } else {
            updateBar(progress);
          }
        } else {
          console.warn('NaN, leaving...');
          window.location.href = $('#link-reload-action').attr('href');
        }
      }).fail(function (jqxhr, textStatus, error) {
        console.error('AJAX error:', textStatus, error);
        showConnectionError();
      }).always(function () {
        $('#reload-spinner').hide();
      });
    }

    refreshProgress();
    let pollInterval = setInterval(refreshProgress, 2000);
  });
</script>

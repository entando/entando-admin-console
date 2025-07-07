package com.agiletec.apsadmin.admin.reload;

import com.agiletec.aps.util.ApsWebApplicationUtils;
import javax.servlet.http.HttpServletRequest;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;
import org.slf4j.Logger;

public class ReloadConfigThread extends Thread {
    Logger log = EntLogFactory.getSanitizedLogger(ReloadConfigThread.class);

    private HttpServletRequest request;

    public ReloadConfigThread(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        try {
            ApsWebApplicationUtils.executeSystemRefresh(request);
            log.info("ReloadConfigThread completed execution");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted", e);
        } catch (Throwable e) {
            log.error("unexpected thread error", e);
            Thread.currentThread().interrupt();
        }
    }

}

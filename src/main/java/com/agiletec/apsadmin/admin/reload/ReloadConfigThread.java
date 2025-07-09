package com.agiletec.apsadmin.admin.reload;

import com.agiletec.aps.util.ApsWebApplicationUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.entando.entando.ent.util.EntLogging.EntLogFactory;
import org.slf4j.Logger;

public class ReloadConfigThread extends Thread {

    Logger log = EntLogFactory.getSanitizedLogger(ReloadConfigThread.class);

    private final HttpServletRequest request;

    public ReloadConfigThread(final HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        try {
            List<String> result = ApsWebApplicationUtils.executeSystemRefresh(request);
            log.info("ReloadConfigThread completed execution");
            HttpSession session = request.getSession();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted", e);
        } catch (Throwable e) {
            log.error("unexpected thread error", e);
            Thread.currentThread().interrupt();
        }
    }

}

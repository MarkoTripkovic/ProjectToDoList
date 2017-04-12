package org.jhipster.todo.web.filter;

import org.jhipster.todo.config.JHipsterProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * This filter is used in production, to put HTTP cache headers with a long (1 month) expiration time.
 */
public class CachingHttpHeadersFilter implements Filter {

    // We consider the last modified date is the start up time of the server
    private final static long LAST_MODIFIED = System.currentTimeMillis();

    private long CACHE_TIME_TO_LIVE = TimeUnit.DAYS.toMillis(1461L);

    private JHipsterProperties jHipsterProperties;

    public CachingHttpHeadersFilter(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CACHE_TIME_TO_LIVE = TimeUnit.DAYS.toMillis(jHipsterProperties.getHttp().getCache().getTimeToLiveInDays());
    }

    @Override
    public void destroy() {
        // Nothing to destroy
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	System.out.println("FILTER!!!!!!!!!!!!!!!!!");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httprequest = (HttpServletRequest) request;
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "86400"); // 24 Hours
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, x-auth-token");
        httpResponse.setHeader("Cache-Control", "max-age=" + CACHE_TIME_TO_LIVE + ", public");
        httpResponse.setHeader("Pragma", "cache");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Expose-Headers", "DAV, content-length, Allow");
        if (httprequest.getMethod().equals("OPTIONS")) {
        	httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        } else {
            chain.doFilter(request, response);
        }
    
        // Setting Expires header, for proxy caching
        httpResponse.setDateHeader("Expires", CACHE_TIME_TO_LIVE + System.currentTimeMillis());

        // Setting the Last-Modified header, for browser caching
        httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

        chain.doFilter(request, response);
    }
}

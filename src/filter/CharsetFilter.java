package filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Amysue on 2016/3/4.
 */
public class CharsetFilter implements Filter{
    private FilterConfig config;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void destroy() {
        config = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String encoding = config.getInitParameter("encode");
        if (encoding == null) {
            encoding = "UTF-8";
        }
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}

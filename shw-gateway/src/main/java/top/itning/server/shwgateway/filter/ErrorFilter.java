package top.itning.server.shwgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

/**
 * 错误信息过滤
 *
 * @date 2019/4/30 2:53
 */
@Component
public class ErrorFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_ERROR_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getThrowable() != null;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        Throwable throwable = requestContext.getThrowable();

        String msg = throwable.getMessage();
        Throwable cause = throwable.getCause();
        if (cause != null) {
            msg = cause.getMessage();
        }
        logger.debug(msg, throwable);

        if (throwable instanceof ZuulException) {
            ZuulException e = (ZuulException) throwable;
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(e.nStatusCode);
            HttpServletResponse response = requestContext.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{" +
                        "\"code\":" +
                        e.nStatusCode +
                        "," +
                        "\"msg\":\"" +
                        e.errorCause +
                        "\",\"data\":\"\"}");
                writer.flush();
                requestContext.setResponse(response);
            } catch (IOException ex) {
                throw new ZuulException(e.errorCause, e.nStatusCode, "");
            }
        } else {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(500);
            HttpServletResponse response = requestContext.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"code\":500,\"msg\":\"" + msg + "\",\"data\":\"\"}");
                writer.flush();
                requestContext.setResponse(response);
            } catch (IOException e) {
                throw new ZuulException(msg, 500, "");
            }
        }

        return null;
    }
}

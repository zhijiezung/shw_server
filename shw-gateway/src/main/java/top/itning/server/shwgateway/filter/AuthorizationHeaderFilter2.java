package top.itning.server.shwgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import feign.FeignException;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import top.itning.server.common.model.LoginUser;
import top.itning.server.shwgateway.client.SecurityClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * {@link HttpHeaders#AUTHORIZATION}请求头检查过滤器
 * 该过滤器检查请求是否包含{@link HttpHeaders#AUTHORIZATION}请求头，并将
 * 其转换为用户信息，如果转换失败将返回错误视图
 *
 * @date 2020/07/31 09:25
 */
@Component
public class AuthorizationHeaderFilter2 extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityClient securityClient;

    /**
     * 忽略过滤路径
     */
    private static final String[] IGNORE_SERVER_PATH = {
            "/v2/user",
            "/v2/file/down"
    };

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String servletPath = requestContext.getRequest().getServletPath();
        for (String path : IGNORE_SERVER_PATH) {
            if (servletPath.startsWith(path)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String ah = request.getHeader(HttpHeaders.AUTHORIZATION);

        if ("null".equalsIgnoreCase(ah) || StringUtils.isBlank(ah)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            HttpServletResponse response = requestContext.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"code\": 401,\"msg\": \"请先登陆\",\"data\": \"\"}");
                writer.flush();
                requestContext.setResponse(response);
            } catch (IOException e) {
                throw new ZuulException("请先登陆", 401, "请先登陆");
            }
        } else {
            try {
                LoginUser loginUser = securityClient.getLoginUser().orElse(null);
                Map<String, List<String>> qp = new HashMap<>(6);
                qp.put("id", Collections.singletonList(loginUser.getId()));
                qp.put("no", Collections.singletonList(loginUser.getNo()));
                qp.put("loginName", Collections.singletonList(loginUser.getLoginName()));
                qp.put("userType", Collections.singletonList(loginUser.getUserType()));
                qp.put("name", Collections.singletonList(loginUser.getName()));
                qp.put("loginIp", Collections.singletonList(loginUser.getLoginIp()));
                requestContext.setRequestQueryParams(qp);
                logger.debug("已登录用户信息：{}", loginUser);
            } catch (FeignException e) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(e.status());
                HttpServletResponse response = requestContext.getResponse();
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                try (PrintWriter writer = response.getWriter()) {
                    writer.write("{" +
                            "\"code\":" +
                            e.status() +
                            "," +
                            "\"msg\":\"" +
                            e.getMessage() +
                            "\",\"data\":\"\"}");
                    writer.flush();
                    requestContext.setResponse(response);
                } catch (IOException ex) {
                    throw new ZuulException(e.getMessage(), e.status(), e.getMessage());
                }
            }
        }

        return null;
    }
}

package top.itning.server.shwgateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import top.itning.server.common.model.LoginUser;

import java.util.Optional;

/**
 * Security服务远程接口
 *
 *
 * @date 2020/07/31 10:02
 */
@FeignClient(name = "security", configuration = FeignConfiguration.class)
@Component
public interface SecurityClient {
    /**
     * 验证 token，解析登录用户信息
     */
    @GetMapping("/internal/getLoginUser")
    Optional<LoginUser> getLoginUser();
}

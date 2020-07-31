package top.itning.server.shwsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 安全中心
 */
@SpringCloudApplication
@EnableFeignClients
public class ShwSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwSecurityApplication.class, args);
    }

}

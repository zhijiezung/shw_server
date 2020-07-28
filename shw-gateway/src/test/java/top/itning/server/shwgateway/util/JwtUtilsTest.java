package top.itning.server.shwgateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import top.itning.server.common.model.LoginUser;

public class JwtUtilsTest {

    /**
     * test JWT token 生成
     *
     * @throws JsonProcessingException
     */
    @Test
    public void buildJwt() throws JsonProcessingException {
        LoginUser loginUser = new LoginUser();
        loginUser.setId("1");
        loginUser.setNo("2");
        loginUser.setLoginName("3");
        loginUser.setUserType("13");
        loginUser.setName("5");
        loginUser.setPhone("6");
        loginUser.setEmail("7");
        loginUser.setMobile("8");
        loginUser.setLoginIp("9");
        loginUser.setRemarks("0");
        loginUser.setRoleId("11");
        loginUser.setDormId("12");
        loginUser.setClazzId("13");
        loginUser.setCompanyId("14");
        loginUser.setOfficeId("15");
        String s = JwtUtils.buildJwt(loginUser);
        System.out.println(s);
    }
}

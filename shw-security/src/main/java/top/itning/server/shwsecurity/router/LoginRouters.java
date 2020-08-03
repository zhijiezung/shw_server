package top.itning.server.shwsecurity.router;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.BizException;
import top.itning.server.common.exception.NullFiledException;
import top.itning.server.shwsecurity.entity.Student;
import top.itning.server.shwsecurity.handler.JwtHandler;
import top.itning.server.shwsecurity.handler.LoginHandler;
import top.itning.server.shwsecurity.repository.StudentRepository;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static top.itning.server.common.model.RestModel.ok;

/**
 * 登陆路由
 *
 * @date 2019/4/30 12:02
 */
@Configuration
public class LoginRouters {

    private final StudentRepository studentRepository;

    public LoginRouters(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Bean
    RouterFunction<ServerResponse> userRouter(LoginHandler loginHandler,
                                              JwtHandler jwtHandler)
    {
        return nest(
                all(),
                route()
                        .GET("/", loginHandler::ticket)
                        .GET("/login", loginHandler::login)
                        .GET("/logout", loginHandler::logout)
                        .build())
                .andNest(
                        path("/user/student"),
                        route()
                                // POST http://127.0.0.1:8888/v2/security/user/student/register
                                // body: {
                                //    "loginName": "huangguoyong",
                                //    "loginPwd": "123456",
                                //    "name": "黄国勇",
                                //    "clazzId": 2
                                //}
                                .POST("/register", jwtHandler::register)

                                // GET http://127.0.0.1:8888/v2/security/user/student/login?loginPwd=123456&loginName=test
                                //.GET("/login", serverRequest -> ServerResponse.ok().body(studentRepository.findByLoginNameAndLoginPwd(serverRequest.queryParam("loginName").get(), serverRequest.queryParam("loginPwd").get()), Student.class))
                                .GET("/login", serverRequest -> ok(studentRepository.findByLoginNameAndLoginPwd(serverRequest.queryParam("loginName").get(), serverRequest.queryParam("loginPwd").get())))

                                // POST http://127.0.0.1:8888/v2/security/user/student/login
                                // body: {
                                //    "loginName": "huangguoyong",
                                //    "loginPwd": "123456"
                                //}
                                /*.POST("/login", serverRequest -> serverRequest
                                        .bodyToMono(JSONObject.class)
                                        .flatMap(body -> Mono.justOrEmpty(body))
                                        .switchIfEmpty(Mono.error(new NullFiledException("登录信息不能为空", HttpStatus.BAD_REQUEST)))
                                        .flatMap(map -> ServerResponse
                                                .ok()
                                                .body(studentRepository
                                                        .findByLoginNameAndLoginPwd(
                                                                map.getString("loginName"),
                                                                map.getString("loginPwd"))
                                                        .switchIfEmpty(Mono.error(new BizException("账号或密码错误", HttpStatus.BAD_REQUEST))),
                                                        Student.class
                                                )
                                        )
                                )*/
                                .POST("/login", serverRequest -> serverRequest
                                        .bodyToMono(JSONObject.class)
                                        .flatMap(body -> Mono.justOrEmpty(body))
                                        .switchIfEmpty(Mono.error(new NullFiledException("登录信息不能为空", HttpStatus.BAD_REQUEST)))
                                        .flatMap(map -> ok(studentRepository
                                                .findByLoginNameAndLoginPwd(
                                                        map.getString("loginName"),
                                                        map.getString("loginPwd"))
                                                .switchIfEmpty(Mono.error(new BizException("账号或密码错误", HttpStatus.BAD_REQUEST)))
                                            )
                                        )
                                )
                                .build()
                )
                .andNest(
                        path("/internal"),
                        route()
                                .GET("/findStudentById/{id}", serverRequest -> ServerResponse.ok().body(studentRepository.findById(serverRequest.pathVariable("id")), Student.class))
                                .GET("/getLoginUser", jwtHandler::getLoginUser)
                                .build()
                );
    }
}

package top.itning.server.shwsecurity.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.BizException;
import top.itning.server.common.exception.NullFiledException;
import top.itning.server.shwsecurity.entity.Student;
import top.itning.server.shwsecurity.repository.StudentRepository;
import top.itning.server.shwsecurity.util.JwtUtils;

import static top.itning.server.common.model.RestModel.created;

/**
 * JTW处理器
 *
 * @date 2019/4/30 12:02
 */
@Component
public class JwtHandler {
    private static final Logger logger = LoggerFactory.getLogger(JwtHandler.class);

    private final StudentRepository studentRepository;

    @Autowired
    public JwtHandler(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * 验证 token，解析登录用户信息
     */
    @NonNull
    public Mono<ServerResponse> getLoginUser(ServerRequest request) {
//        String jwt = request.headers().firstHeader("Authorization");
        String jwt = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(JwtUtils.getLoginUser(jwt)));
    }

    /**
     * 学生注册
     *
     * @param request
     * @return
     */
    @NonNull
    public Mono<ServerResponse> register(ServerRequest request) {
        /*return request
                .formData()  // Content type 'application/x-www-form-urlencoded;charset=UTF-8'
                .flatMap(m -> {
                    Student student = null;
                    if (!StringUtils.isEmpty(m.getFirst("loginName"))) {
                        student = new Student();
                        student.setNo(m.getFirst("no"));
                        student.setLoginName(m.getFirst("loginName"));
                        student.setLoginPwd(m.getFirst("loginPwd"));
                        student.setName(m.getFirst("name"));
                        student.setClazzId(m.getFirst("clazzId"));
                    }
                    logger.debug("注册信息：{}", student);
                    return Mono.justOrEmpty(student);
                })
                .switchIfEmpty(Mono.error(new NullFiledException("注册信息不能为空", HttpStatus.BAD_REQUEST)))
                .flatMap(s -> created(studentRepository.save(s), "注册成功"));*/

        /*return request
                .bodyToMono(Student.class)  // Content type 'application/json'
                .flatMap(b -> {
                    logger.debug("注册信息：{}", b);
                    return Mono.justOrEmpty(b);
                })
                .switchIfEmpty(Mono.error(new NullFiledException("注册信息不能为空", HttpStatus.BAD_REQUEST)))
                .flatMap(s -> created(studentRepository.save(s), "注册成功"));*/  // insert or update

        return request
                .bodyToMono(Student.class)  // Content type 'application/json'
                .flatMap(b -> {
                    logger.debug("注册信息：{}", b);
                    return Mono.justOrEmpty(b);
                })
                .switchIfEmpty(Mono.error(new NullFiledException("注册信息不能为空", HttpStatus.BAD_REQUEST)))
                .flatMap(form -> studentRepository
                        //.existsById(form.getLoginName())
                        .existsByLoginName(form.getLoginName())
                        .flatMap(bool -> {
                            if (bool) {
                                return Mono.error(new BizException("用户已经存在", HttpStatus.FORBIDDEN));
                            } else {
                                form.setNo(null);
                                return created(studentRepository.save(form), "注册成功");
                            }
                        })
                );
    }

    @NonNull
    public Mono<ServerResponse> login(ServerRequest request) {
        return ServerResponse.ok().build();
    }

    @NonNull
    public Mono<ServerResponse> logout(ServerRequest request) {
        return ServerResponse.ok().build();
    }
}

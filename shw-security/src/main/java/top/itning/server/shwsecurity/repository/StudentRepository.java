package top.itning.server.shwsecurity.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import top.itning.server.shwsecurity.entity.Student;

/**
 * 学生存储库
 *
 * @date 2019/4/30 13:10
 */
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
    /**
     * 根据 loginName 查询用户是否已经存在
     *
     * @param loginName 用户名
     * @return
     */
    Mono<Boolean> existsByLoginName(String loginName);

    Mono<Student> findByLoginNameAndLoginPwd(String loginName, String loginPwd);
}

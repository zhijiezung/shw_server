package top.itning.server.shwstudentgroup.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.itning.server.shwstudentgroup.dto.StudentGroupDTO;
import top.itning.server.shwstudentgroup.entity.StudentGroup;

import java.util.List;

/**
 * 学生群组服务
 *
 *
 * @date 2019/4/30 18:14
 */
public interface StudentGroupService {
    /**
     * 学生加群
     *
     * @param code          邀请码
     * @param studentNumber 学号
     * @return 加入的群组
     */
    Mono<StudentGroup> joinGroup(String code, String studentNumber);

    /**
     * 学生退出群组
     *
     * @param groupId   要退出的群组ID
     * @param studentId 学生ID
     * @return 操作完成后的信号
     */
    Mono<Void> dropOutGroup(String groupId, String studentId);

    /**
     * 获取学生所在的所有群组
     *
     * @param studentNumber 学生学号
     * @param page          页码
     * @param size          每页数量
     * @return 群组分页信息
     */
    Mono<Page<StudentGroupDTO>> findStudentAllGroups(String studentNumber, int page, int size);

    /**
     * 检查学生是否有群组
     *
     * @param studentNumber 学号
     * @return 如果有返回<code>true</code>
     */
    Mono<Boolean> isHaveGroup(String studentNumber);

    /**
     * 根据学号查询学生群组中的群组ID信息
     *
     * @param studentNumber 学号
     * @return 群组ID集合
     */
    Flux<String> findGroupIdByStudentNumber(String studentNumber);

    /**
     * 根据GroupID查询群组内学生
     *
     * @param groupId 群组ID
     * @param page    页码
     * @param size    每页数量
     * @return 学生集合
     */
    Mono<List<StudentGroup>> findAllByGroupID(String groupId, int page, int size);

    /**
     * 根据群ID计算数量
     *
     * @param groupId 群ID
     * @return 数量
     */
    Mono<Long> countAllByGroupID(String groupId);

    /**
     * 教师删除群组
     *
     * @param groupId 群组ID
     * @return 操作完成后的信号
     */
    Mono<Void> teacherDelGroupMessage(String groupId);
}

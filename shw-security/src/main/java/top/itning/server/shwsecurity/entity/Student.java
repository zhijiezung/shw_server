package top.itning.server.shwsecurity.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 学生实体
 *
 * @date 2019/4/30 13:08
 */
@Document(collection = "student")
public class Student {
    /**
     * 学号
     */
    @Id
    private String no;  // 如果为空，则mongodb生成默认ID

    /**
     * 登录名
     */
    @Field("login_name")
    private String loginName;
    /**
     * 登录密码
     */
    @Field("login_pwd")
    private String loginPwd;

    /**
     * 姓名
     */
    @Field("name")
    @Indexed
    private String name;
    /**
     * 班级
     */
    @Field("clazz_id")
    private String clazzId;

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(String clazzId) {
        this.clazzId = clazzId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "no='" + no + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", name='" + name + '\'' +
                ", clazzId='" + clazzId + '\'' +
                '}';
    }
}

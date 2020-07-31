package top.itning.server.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 空属性异常
 *
 *
 */
public class NullFiledException extends BaseException {
    public NullFiledException(String msg, HttpStatus code) {
        super(msg, code);
    }
}

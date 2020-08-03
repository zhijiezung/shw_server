package top.itning.server.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 业务异常
 */
public class BizException extends BaseException {
    public BizException(String msg, HttpStatus code) {
        super(msg, code);
    }
}

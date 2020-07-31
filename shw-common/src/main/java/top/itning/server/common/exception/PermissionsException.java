package top.itning.server.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 权限异常
 *
 *
 */
public class PermissionsException extends BaseException {
    public PermissionsException(String msg) {
        super(msg, HttpStatus.FORBIDDEN);
    }
}

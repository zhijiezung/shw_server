package top.itning.server.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 文件异常
 *
 *
 */
public class FileException extends BaseException {
    public FileException(String msg, HttpStatus code) {
        super(msg, code);
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.exception;

/**
 * 自定义异常...
 *
 * @author chenpi
 * @since 2018/3/24 10:43
 */
public class TankBattleGameException extends RuntimeException {
    public TankBattleGameException() {
        super();
    }

    public TankBattleGameException(String message) {
        super(message);
    }

    public TankBattleGameException(Throwable cause) {
        super(cause);
    }

    public TankBattleGameException(String message, Throwable cause) {
        super(message, cause);
    }
}

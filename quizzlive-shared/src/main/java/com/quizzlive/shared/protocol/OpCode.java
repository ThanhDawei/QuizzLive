package com.quizzlive.shared.protocol;

/**
 * Operation Codes (OpCodes) for Quizzlive Network Protocol
 */
public enum OpCode {
    LOGIN_REQ(1),
    LOGIN_RESP(2),
    CREATE_ROOM_REQ(10),
    CREATE_ROOM_RESP(11),
    JOIN_ROOM_REQ(12),
    JOIN_ROOM_RESP(13),
    START_GAME_REQ(20),
    QUESTION_SEND(21),
    SUBMIT_ANSWER_REQ(22),
    SUBMIT_ANSWER_RESP(23),
    SCORE_UPDATE_BROADCAST(30),
    GAME_OVER_BROADCAST(31),
    ERROR_RESP(99);

    private final int code;

    OpCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OpCode fromCode(int code) {
        for (OpCode op : OpCode.values()) {
            if (op.code == code) {
                return op;
            }
        }
        return ERROR_RESP;
    }
}

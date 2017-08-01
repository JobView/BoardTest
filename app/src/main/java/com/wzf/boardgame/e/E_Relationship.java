package com.wzf.boardgame.e;

/**
 * Author : Bean
 * 2017-06-20 15:07
 */
public enum E_Relationship implements BaseEnum{

    UN_FOLLOW(0,"未关注"),
    FOLLOW(1,"关注"),
    FUNS(2,"粉丝"),
    FOLLOW_EACH_OTHER(3,"互相关注"),
    ;

    int code;
    String msg;

    E_Relationship(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public static E_Relationship getRelationShip(int code){
        E_Relationship ship = E_Relationship.UN_FOLLOW;
       for(E_Relationship e : E_Relationship.values()){
            if(code == e.getCode()){
                ship = e;
            }
       }
        return ship;
    }
}

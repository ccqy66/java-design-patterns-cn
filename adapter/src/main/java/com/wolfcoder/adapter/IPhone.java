package com.wolfcoder.adapter;

/**
 * Created with IntelliJ IDEA.
 * User: chenchen42
 * Date: 2018/1/18
 * Time: 下午8:21
 * To change this template use File | Settings | File Templates.
 */
public interface IPhone {
    /**
     * 发短信
     * @param content
     */
    void sendMail(String content);

    /**
     * 打电话
     * @param phoneNumber
     */
    void callPhone(String phoneNumber);
}

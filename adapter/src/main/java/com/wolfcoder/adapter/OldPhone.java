package com.wolfcoder.adapter;

/**
 * Created with IntelliJ IDEA.
 * User: chenchen42
 * Date: 2018/1/18
 * Time: 下午8:22
 * To change this template use File | Settings | File Templates.
 * role:Adaptee
 */
public class OldPhone implements IPhone{
    @Override
    public void sendMail(String content) {
        System.out.println("send mail to "+content);
    }

    @Override
    public void callPhone(String phoneNumber) {
        System.out.println("call phone to "+phoneNumber);
    }
}

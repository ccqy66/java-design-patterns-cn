package com.wolfcoder.adapter;

/**
 * Created with IntelliJ IDEA.
 * User: chenchen42
 * Date: 2018/1/18
 * Time: 下午8:24
 * To change this template use File | Settings | File Templates.
 * role:Adapter
 */
public class PhoneAdapter extends OldPhone implements IModernPhone{
    @Override
    public void userApp(String appname) {
        System.out.println("download app:"+appname);
    }
}

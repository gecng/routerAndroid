package com.gecng.routerlib

class TODO {


    //todo 对于有 @Route @Interceptor 注解的类 进行一个类型判断

    //todo 拦截转发 ，后续完成跳转后 前往拦截前的路径，暂时不做



    /**
     * 路由拦截转发实现：
     * 存在一个或以上的拦截器 拦截了本次路由，并需要转发的时候
     * 拉起一个透明的activity，转发至 某拦截界面，完成操作后
     * 第二次回到透明activity 界面
     * 再次check 拦截器，若条件满足，finish 当前透明activity 界面，跳转目的界面
     */
}
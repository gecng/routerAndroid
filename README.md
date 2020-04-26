### Kotlin版 安卓路由 ，借鉴了其他路由框架，自己实现了一个轮子。

#### 为什么需要路由？

1.外部链接跳转app内的相关界面。

2.内嵌h5拉起native相关界面，或者调用native的方法。TODO:场景补充。

3.跨模块。TODO:场景补充

#### 实现方式

1.收集所有的路由表。

2.

#### 1.定义路由表，实现url到intent的映射。

#### 在每个activity上添加 Route注解，并为注解的url属性赋值。形成 url 到 activity的映射关系。每个module都有一个自己的路由表，通过kotlin poet 生成相关的代码，并打包到apk 中。路由表的生成的位置 和名称 都是自己定义好的，也是方便反射获取实例对象。

#### 2.在application 初始化的时候，反射获取映射表的实例，放到一个map中。这样就可以通过一个url 获取 相应的 activity.





---
### 完成的功能
#### 1.activity 跳转
#### 2.拦截器
---
### 相关资料
#### 1. kotlin 注解生成代码 https://github.com/JetBrains/kotlin-examples/tree/master/gradle/kotlin-code-generation
#### 2. kotlin poet
---

### TodoList
#### 1. 添加UrlParser ，解析url ，URL可能非明文。
#### 2. 路由匹配  原生跳转原生，原生跳转h5,当前只支持 原生-》原生。</br>{  h5 跳转 原生 ， 目前的想法是： webView 层面 进行拦截 ，判断当前加载url , 如果是 原生支持的 拦截交给Router去处理}
#### 3. 被注解的类  类型 需要验证

#### 4. activity 栈管理

问题1:

使用rxjava订阅onActivityResult事件存在的问题：

本质：在activity中注入一个透明的Fragment。在该Fragment的onActivityResult方法中，通过subject发射result的数据。但是存在以下场景 同一个activity同时启动了两个activity，且都用了startActivityForResult。一个activity调用finish方法后，subjecct 发射onComplete事件，导致另一个activity的回掉结果无法正常被接受。

解决方案：这里可能需要参考Rxpermission 的处理方式，对于同时订阅多个，如何区分处理。

方案2：设置requestCode区分 请求方。fragment内部管理一个map，每次发起startActivityResult，rc自减。

disposable 加入 map。在onActivityResult里面，subject.onNext() , subscriber 根据自己的rc 判断是否需要接受事件。接收后，subscriber 自己手动接触订阅。

问题2:

路由拦截，转发如何处理

情景：前往A界面，由于没有登陆，被登陆拦截器拦截，重定向前往Login界面。Login完成后，如何重定向。

问题3：

现在收集路由表信息，需要手动注入module的name，获取各个模块的路由表。

方案1:

使用ASM，避免在application中初始化手动初始化路由表。KAPT 无法获取其他modlu的路由表，对于android主工程无法感知其他模块生成的路由表。通过gradle transform 可以遍历所有的class 文件，通过类名以及接口定义，可以获取到所有模块下的路由表。在kapt生成代码的时候可以指定生成路由表的位置，扫码指定包名下的class文件。

方案2:

采用kapt时期，替project插入一个task，在assets文件，创建包含模块信息的file。这样通过context.assets.list("")，曲线获取路由表的位置。










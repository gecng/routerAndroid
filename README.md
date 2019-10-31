### Kotlin版 安卓路由 ，借鉴了其他路由框架，自己实现了一个轮子。
### 思路
#### 1.在每个activity上添加 Route注解，并为注解的url属性赋值。形成 url 到 activity的映射关系。每个module都有一个自己的路由表，通过kotlin poet 生成相关的代码，并打包到apk 中。路由表的生成的位置 和名称 都是自己定义好的，也是方便反射获取实例对象。
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



#### 上传


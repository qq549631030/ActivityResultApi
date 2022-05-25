# Android Activity Result API 封装
androidx.activity 1.2.0 开始新加了Activity Result API，原本的startActivityForResult被标记为过时的。
官方做法是要在onStart之前registerForActivityResult，这就导致必须提前知道当前页面会发起哪些页面请求提前注册，但是往往我们不一定确定当前页面会发生什么，比如跳转到登录页面。
本库做了下封装，可以在任意时机调用，并且在回调参数里增加了调用者(caller)和启动Intent，这在Activity recreate的情况下非常有用！

要验证效果，可在“开发者选项”中把“不保留活动”打开

### gradle依赖

```groovy
dependencies {
    implementation 'com.github.qq549631030:activity-result-api:x.x.x'
}
```

### 初始化配置

#### 1、初始化

在Application的onCreate方法调用ActivityResultManager.init(this)初始化

```kotlin
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ActivityResultManager.init(this)
    }
}
```

#### 2、ActivityResultSource配置

通过代理方式：

ActivityResultSource by ActivityResultSourceImpl()完成任意Activity和Fragment配置

```kotlin
open class BaseActivity : AppCompatActivity(), ActivityResultSource by ActivityResultSourceImpl() {
}
open class BaseFragment : Fragment(), ActivityResultSource by ActivityResultSourceImpl() {
}
```

#### 3、使用

```kotlin
startActivityForResult(Intent(this, SecondActivity::class.java)) {
     //回调参数ActivityResultInfo各属性说明：
     //resultCode：调用结果
     //data：额外返回数据
     //sourceUuid：调用者uuid
     //startIntent：启动本次调用的Intent
     //getCaller()方法: 返回非空调用者，为空抛异常（通常不会出现，除非在调用者onCreate之前调用）
     //getSafeCaller()方法: 返回可空调用者，

    //这里只能通过getCaller()访问外部类的方法、属性
    //不可直接访问外部类方法、属性，因为Activity recreate后的情况下原调用者已经不存在了
    //getCaller()是本库的精华所在，在Activity recreate后getCaller()是重建后的Activity或Fragment
    (it.caller as YourActivityResultSourceActivity).someMethod(it.resultCode, it.data, it.startIntent)
    (it.caller as YourActivityResultSourceFragment).someMethod(it.resultCode, it.data, it.startIntent)
}
```


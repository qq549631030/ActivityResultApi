# Android Activity Result API 封装

相比默认实现，本库在回调参数里增加了调用者，这在Activity recreate的情况下非常有用！

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
    //这里只能通过caller访问外部类的方法、属性
    //不可直接访问外部类方法、属性，因为Activity recreate后的情况下原caller已经不存在了
    //caller是本库的精华所在，在Activity recreate后caller是重建后的Activity或Fragment
    (it.caller as? MainActivity)?.receiveResult(it.resultCode, it.data)
}
```


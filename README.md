# sp

Flutter Android SharedPreferences plugin.

## 快速开始

* 依赖

```yaml
dependencies:
  sp: ^0.0.1
```

* 使用

获取缓存值

> 第一个参数为key，第二个参数默认value。获取的value类型取决第二个参数的类型。

```dart
await Sp.getValue("test", "")//获取String
await Sp.getValue("test", 0)//获取int
await Sp.getValue("test", 1.0)//获取float
/***/
```

保存缓存值

> 第一个参数为key，第二个参数为存储value。

```dart
await Sp.saveValue("test", "111")
```

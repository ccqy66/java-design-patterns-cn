|    设计    | 标题 | 文件夹 | 链接 | 类型 | 标签|
| ---------- | --- | ---- | ---| ---| ---|
| 模式 |  适配器 | adapter | /patterns/adapter/| 结构型 |Java|

#### 别名
包装器设计模式
#### 目的
将接口转换为客户所期望的另一个接口。适配器让类能够让互不兼容的接口进行协同工作。
#### 解释
一些现实中的例子：
>考虑一下你的存储卡中有一些照片，你需要把它们传送到你的电脑上。为了传输它们，
您需要某种与您的计算机端口兼容的适配器，以便您可以将存储卡连接到您的计算机。在这种情况下，
读卡器是一个适配器。

>另一个例子是著名的电源适配器;一个三脚插头不能连接到双叉插座上，需要使
用一个电源适配器，使其与双插头插座兼容。

>又一个例子是翻译者将一个人所说的话翻译成另一个人。

简单来说：
>适配器模式可让您将适配器中的其他不兼容对象封装起来，使其与另一个类兼容。

维基百科的定义：
>在软件工程中，适配器模式是一种软件设计模式，它允许将现有类的接口用作另一个接口。它通常用于使现有类与其他类一起工作而不修改其源代码。

#### 编程示例

考虑一个只能使用划艇的船长，根本不能航行。

我们有两个接口：`RowingBoat`和`FishingBoat`

```JAVA
public interface RowingBoat {
  void row();
}

public class FishingBoat {
  private static final Logger LOGGER = LoggerFactory.getLogger(FishingBoat.class);
  public void sail() {
    LOGGER.info("The fishing boat is sailing");
  }
}
```
船长希望RowingBoat接口的实现能够进行移动：
```JAVA
public class Captain implements RowingBoat {

  private RowingBoat rowingBoat;

  public Captain(RowingBoat rowingBoat) {
    this.rowingBoat = rowingBoat;
  }

  @Override
  public void row() {
    rowingBoat.row();
  }
}
```
此时，我们假设，海盗来了，我们的船长需要逃跑，但只有渔船可用。我们需要创建一个适配器，允许船长用他的划船技能操作渔船。
```JAVA
public class FishingBoatAdapter implements RowingBoat {

  private static final Logger LOGGER = LoggerFactory.getLogger(FishingBoatAdapter.class);

  private FishingBoat boat;

  public FishingBoatAdapter() {
    boat = new FishingBoat();
  }

  @Override
  public void row() {
    boat.sail();
  }
}
```
现在船长可以使用渔船逃离海盗。
```JAVA
Captain captain = new Captain(new FishingBoatAdapter());
captain.row();
```
#### 适用性
当以下情况时使用适配器模式：
- 你想使用现有的类，但是其接口不匹配你现在的需要
- 你想创建一个可重用的类，与不相关的或不可预见的类进行合作，也就是说，不需要兼容接口的类
- 您需要使用几个现有的子类，但是通过继承每个子类来调整它们的接口是不切实际的。对象适配器可以调整其父类的接口。
- 大多数使用第三方库的应用程序都使用适配器作为应用程序和第三方库之间的中间层，以将应用程序与库分离。如果必须使用另一个库，则只需要开发用于新库的适配器即可，而不必更改应用程序代码。

#### 结论
类适配器模式和对象适配器模式使用时需要进行权衡。
类适配器模式需要如下考虑：
- 通过一个具体的被适配者类（Adaptee）将适配者接口（Adapteee）与目标接口（Target）进行适配，这将导致一个后果，当我们想调整一个类及其所有子类时，类适配器将不起作用。
- 让我们的Adapter重写一些Adaptee的行为，因为Adapter是Adaptee的一个子类。
- 只引入一个对象，并且不需要额外的指针间接寻找适配器。

对象适配器模式使用时有如下考虑：

- 让我们用一个适配器与许多适配器一起工作，即适配器本身及其所有子类（如果有的话）。适配器也可以一次为所有的适配器添加功能。
- 使得难以覆盖Adaptee的行为。这将需要子类Adaptee和使适配器指的是子类而不是适配器本身。

#### 真实的例子
- java.util.Arrays#asList()
- java.util.Collections#list()
- java.util.Collections#enumeration()
- javax.xml.bind.annotation.adapters.XMLAdapter

#### 译者总结
适配器模式共有3个角色：
- 目标接口（Target）
- 被适配者类（Adaptee）
- 适配器类（Adapter）

使用的形式为：适配器类（Adapter）继承或者使用组合方式与被适配者类（Adaptee）进行关联。并实现目标接口（Target)
一句话总结：适配器通过继承或者组合方式持有配适配者类的行为，并实现目标接口，使其具有目标接口的特性。
#### 感谢
* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [J2EE Design Patterns](http://www.amazon.com/J2EE-Design-Patterns-William-Crawford/dp/0596004273/ref=sr_1_2)
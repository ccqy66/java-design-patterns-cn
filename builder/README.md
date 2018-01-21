---
设计: 模式
标题: 构建者
文件: builder
链接: /patterns/builder/
分类: 创建型
标签:
 - Java
 - 四人帮
 - 困难
---

#### 目标
将复杂对象的构造与其表示分开，以便相同的构造过程可以创建不同的表示。

#### 解释
现实世界的例子：
>设想一个角色扮演游戏的角色生成器。 最简单的选择是让电脑为你创造角色。 但是如果你想选择职业，性别，发色等角色细节时，角色的生成就成了一个循序渐进的过程，当所有的选择都准备好的时候就完成了。

简单的说：
>允许您创建不同的对象风格，同时避免造成构造器污染。 当可能有几种风格的对象时 或者当创建对象时涉及很多步骤时该模式很有用。

维基百科：
>构建者模式是一个对象创建的设计模式，旨在寻找重叠构造反模式的解决方案。

现在，让我加一些关于什么是重叠构造反模式。 某种情况下我们会看到了如下构造函数：
```java
public Hero(Profession profession, String name, HairType hairType, HairColor hairColor, Armor armor, Weapon weapon) {
}
```
正如您所看到的，构造函数参数的数量可能会很快失控，并且从参数的排列方式来看可能难以理解这些参数。 再加上这个参数列表可以继续增长，如果你想在将来添加更多的选项。 这被称为重叠构造反模式。

#### 编程示例
理智的选择是使用Builder模式。 首先创造我们想要的的英雄
```java
public final class Hero {
  private final Profession profession;
  private final String name;
  private final HairType hairType;
  private final HairColor hairColor;
  private final Armor armor;
  private final Weapon weapon;

  private Hero(Builder builder) {
    this.profession = builder.profession;
    this.name = builder.name;
    this.hairColor = builder.hairColor;
    this.hairType = builder.hairType;
    this.weapon = builder.weapon;
    this.armor = builder.armor;
  }
}
```
然后创建我们的构建器
```java
 public static class Builder {
    private final Profession profession;
    private final String name;
    private HairType hairType;
    private HairColor hairColor;
    private Armor armor;
    private Weapon weapon;

    public Builder(Profession profession, String name) {
      if (profession == null || name == null) {
        throw new IllegalArgumentException("profession and name can not be null");
      }
      this.profession = profession;
      this.name = name;
    }

    public Builder withHairType(HairType hairType) {
      this.hairType = hairType;
      return this;
    }

    public Builder withHairColor(HairColor hairColor) {
      this.hairColor = hairColor;
      return this;
    }

    public Builder withArmor(Armor armor) {
      this.armor = armor;
      return this;
    }

    public Builder withWeapon(Weapon weapon) {
      this.weapon = weapon;
      return this;
    }

    public Hero build() {
      return new Hero(this);
    }
  }
```
我们可以采取如下的方式使用：
```java
Hero mage = new Hero.Builder(Profession.MAGE, "Riobard").withHairColor(HairColor.BLACK).withWeapon(Weapon.DAGGER).build();
```
#### 适用性
当如下情况可以考虑使用构建者模式
- 创建复杂对象的算法应该独立于组成对象本身以及构建过程。
- 构建过程必须允许对构建的对象进行不同的表示

#### 现实中的例子
* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
* [java.nio.ByteBuffer](http://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html#put-byte-) as well as similar buffers such as FloatBuffer, IntBuffer and so on.
* [java.lang.StringBuffer](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuffer.html#append-boolean-)
* All implementations of [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)

#### 感谢
* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [Effective Java (2nd Edition)](http://www.amazon.com/Effective-Java-Edition-Joshua-Bloch/dp/0321356683)
---
设计: 模式
标题: 责任链
文件: chain
链接: /patterns/chain/
分类: 行为
标签:
 - Java
 - 四人帮
 - 困难
---

#### 目标
---
通过给多个对象一个处理请求的机会，避免将请求的发送者耦合到它的接收者。将接收对象连接起来，并沿着链传递请求，直到对象处理它为止。

#### 解释
---
现实世界中的例子
>兽人国的国王大声命令他的军队。离国王最接近的分别是指挥官，然后是军官，然后是士兵。这里的指挥官、军官和士兵构成了一个责任链。

简单的说
>它有助于建立一连串的对象。 请求从一端进入，并且在链中的对象中传递，直到找到合适的处理逻辑。

维基百科
>在面向对象设计中，责任链模式是由命令对象源和一系列处理对象组成的设计模式。 每个处理对象都包含用于定义可处理的命令对象类型的逻辑; 其余的被传递给链中的下一个处理对象。

#### 编程示例
---
用上面的兽人翻译我们的例子。 首先实现请求类。
```java
public class Request {

  private final RequestType requestType;
  private final String requestDescription;
  private boolean handled;

  public Request(final RequestType requestType, final String requestDescription) {
    this.requestType = Objects.requireNonNull(requestType);
    this.requestDescription = Objects.requireNonNull(requestDescription);
  }

  public String getRequestDescription() { return requestDescription; }

  public RequestType getRequestType() { return requestType; }

  public void markHandled() { this.handled = true; }

  public boolean isHandled() { return this.handled; }

  @Override
  public String toString() { return getRequestDescription(); }
}

public enum RequestType {
  DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```
然后请求处理器层次结构
```java
public abstract class RequestHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
  private RequestHandler next;

  public RequestHandler(RequestHandler next) {
    this.next = next;
  }

  public void handleRequest(Request req) {
    if (next != null) {
      next.handleRequest(req);
    }
  }

  protected void printHandling(Request req) {
    LOGGER.info("{} handling request \"{}\"", this, req);
  }

  @Override
  public abstract String toString();
}

public class OrcCommander extends RequestHandler {
  public OrcCommander(RequestHandler handler) {
    super(handler);
  }

  @Override
  public void handleRequest(Request req) {
    if (req.getRequestType().equals(RequestType.DEFEND_CASTLE)) {
      printHandling(req);
      req.markHandled();
    } else {
      super.handleRequest(req);
    }
  }

  @Override
  public String toString() {
    return "Orc commander";
  }
}

// OrcOfficer and OrcSoldier are defined similarly as OrcCommander

```
然后，国王发出命令，并且形成了一条链
```java
public class OrcKing {
  RequestHandler chain;

  public OrcKing() {
    buildChain();
  }

  private void buildChain() {
    chain = new OrcCommander(new OrcOfficer(new OrcSoldier(null)));
  }

  public void makeRequest(Request req) {
    chain.handleRequest(req);
  }
}
```
如下的方式来使用
```java
OrcKing king = new OrcKing();
king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle")); // Orc commander handling request "defend castle"
king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner")); // Orc officer handling request "torture prisoner"
king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax")); // Orc soldier handling request "collect tax"
```
#### 适用性
---
如下情况考虑使用责任链设计模式：
- 多于一个的对象可以处理请求，并且该处理程序不是先验的。 处理程序应该根据参数自动确定。
- 你想发出一个请求到几个对象中的一个，而不能明确指定接收处理者。
- 可以动态地指定可以处理请求的对象集合。

#### 真实例子
---
* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
* [javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)

#### 感谢
---
* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
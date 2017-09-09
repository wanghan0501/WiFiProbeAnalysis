package education.cs.scu.webSocket.handler;

import education.cs.scu.webSocket.Monitor;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @author uptop
 */
@ServerEndpoint(value="/websocket", encoders = {UserFlowEncoder.class})
public class WebSocketEndPointTest {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static CopyOnWriteArraySet<WebSocketEndPointTest> webSocketSet = new CopyOnWriteArraySet<WebSocketEndPointTest>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("建立新连接，ID:" + getOnlineCount());
        Monitor monitor = new Monitor();
        monitor.sendMsg();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前连接数:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("message:" + message);
        //群发消息
        for (WebSocketEndPointTest item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param object
     * @throws IOException
     */
    public void sendMessage(Object object) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(object);

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketEndPointTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketEndPointTest.onlineCount--;
    }
    public void sendMsg(Object object) {
        for (WebSocketEndPointTest item : webSocketSet) {
            try {
                item.sendMessage(object);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}

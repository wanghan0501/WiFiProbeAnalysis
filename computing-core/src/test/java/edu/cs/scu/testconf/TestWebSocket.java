package edu.cs.scu.testconf;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

public class TestWebSocket {
    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("testChannel", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("testChannel", data);
            }
        });
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }
}

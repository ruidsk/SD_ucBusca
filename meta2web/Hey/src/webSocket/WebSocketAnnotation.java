package webSocket;

import hey.model.FacebookBean;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/ws")
public class WebSocketAnnotation {
    private static final AtomicInteger sequence = new AtomicInteger(1);
    private final String username;
    private Session session;
    private FacebookBean ws_methods;
    private HttpSession httpSession;


    private static final Set<WebSocketAnnotation> users = new CopyOnWriteArraySet<>();


    public WebSocketAnnotation() {
        username = "User" + sequence.getAndIncrement();
    }

    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        httpSession = (HttpSession)request.getHttpSession();
        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }


    @OnOpen
    public void start(Session session, EndpointConfig config) {
        this.session = session;
        String message = "*" + username + "* connected.";

        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());



        try {
            this.ws_methods = new FacebookBean(this,httpSession);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @OnClose
    public void end() {
        // clean up once the WebSocket connection is closed
        try {
            this.session.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @OnMessage
    public void receiveMessage(String message) {
        // one should never trust the client, and sensitive HTML
        // characters should be replaced with &lt; &gt; &quot; &amp;
        String upperCaseMessage = message.toUpperCase();
        sendMessage("[" + username + "] " + upperCaseMessage);
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    public void sendMessage(String text) {
        // uses *this* object's session to call sendText()
        try {
            this.session.getBasicRemote().sendText(text);

            //this.session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            // clean up once the WebSocket connection is closed
            try {
                this.session.close();
                users.remove(this);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
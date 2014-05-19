package mu.codeoffice.messaging;

import mu.codeoffice.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MessageWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private MessageService messageService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("New connection");
		messageService.registerOpenConnection(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		messageService.registerCloseConnection(session);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		messageService.registerCloseConnection(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("New message: " + message.getPayload());
		messageService.processMessage(session, message.getPayload());
	}
}

package com.upstox.config;

import java.lang.reflect.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.upstox.upstoxohlc.dto.Subscription;
import com.upstox.upstoxohlc.dto.TradeOHLCResponse;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/ohlc and 
 * send a sample message to server.
 * @author arastogi
 */
public class TradeStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(TradeStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/ohlc", this);
        logger.info("Subscribed to /topic/ohlc");
        session.send("/app/trades", getSampleSubscription());
        logger.info("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return TradeOHLCResponse.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
    	TradeOHLCResponse tradeOHLCResponse = (TradeOHLCResponse) payload;
        logger.info("tradeOHLCResponse Received : ---> {} ",tradeOHLCResponse);
    }

    /**
     * A sample subscription
     */
    private Subscription getSampleSubscription() {
    	Subscription subscription= new Subscription();
    	subscription.setEvent("subscribe");
	 	subscription.setSymbol("XXBTZUSD");
	 	subscription.setInterval(15);
        return subscription;
    }
}
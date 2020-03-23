package com.upstox.upstoxohlc.trades.consumer;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.upstox.upstoxohlc.dto.Trade;
import com.upstox.upstoxohlc.dto.TradeOHLCResponse;
import com.upstox.upstoxohlc.fsm.TradeMachine;
import com.upstox.upstoxohlc.trades.broker.TradeBroker;

/**
 * Trade comsumer
 * @author arastogi
 */
public class TradeConsumer  implements Runnable{
	
	private Logger logger = LogManager.getLogger(TradeConsumer.class);
	
	private TradeBroker tradeBroker;
	
	private TradeMachine tradeMachine;
	
	private int barNum = 1;
	
	private SimpMessagingTemplate simpMessagingTemplate;
	
	public TradeConsumer(TradeBroker tradeBroker,SimpMessagingTemplate simpMessagingTemplate){
		this.tradeBroker = tradeBroker;
		this.tradeMachine = new TradeMachine();
		this.simpMessagingTemplate = simpMessagingTemplate;
	}
	@Override
	public void run() {
		int cnt = 1;
		try {
			int interval = tradeBroker.getSubscription().getInterval();
			long start = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
			
			while(true) {
                Trade  trade = tradeBroker.get();
                //Check the last trade
                if(trade.getStkName().equals("lastTrade")) {
                	break;
                }
                //Thread.sleep(100);
                TradeOHLCResponse ohldRes  = tradeMachine.getOhlc(trade, barNum);
                long end = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                long total = end-start;
                if(total >= interval) {
                	interval = tradeBroker.getSubscription().getInterval();
                	start = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                	ohldRes.setClose(ohldRes.getLow());
                	barNum++;
                	tradeMachine.restOld();
                }
                cnt++;
                logger.info("Ohlc response :{}",ohldRes);
                
                this.simpMessagingTemplate.convertAndSend("/topic/ohlc", ohldRes);
			}
			logger.info("cnt: {}",cnt );
			logger.info("Comsumer finished its job; terminating.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}

package com.upstox.upstoxohlc.trades.producer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upstox.upstoxohlc.dto.Trade;
import com.upstox.upstoxohlc.trades.broker.TradeBroker;

public class TradeProducer implements Runnable{

	private Logger logger = LogManager.getLogger(TradeProducer.class);
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private TradeBroker tradeBroker;
	
	public TradeProducer(TradeBroker tradeBroker) {
		this.tradeBroker = tradeBroker;	
	}

	private static final String TRADE_FILE_PATH = "trades.json";
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(TRADE_FILE_PATH));
			try {
			    String line = reader.readLine();
			    while (line != null) {
			        line = reader.readLine();
			        if(line != null && !line.equals("")) {
			        	Trade trade = getTrade(line);
			        	if(tradeBroker.getSubscription().getSymbol().equals(trade.getStkName())) {
				        	tradeBroker.put(trade);	
			        	}
			        }
			    }
			    Trade lastTrade = new Trade();
			    lastTrade.setStkName("lastTrade");
			    tradeBroker.put(lastTrade);
			} finally {
			    reader.close();
			}			
			logger.info("Producer finished its job; terminating.");
		} catch (IOException e) {
			logger.error("Error Occur getting the trade data: {}",e.getMessage());
		} catch (InterruptedException e) {
			logger.error("Error Occur while producesing the trade: {}",e.getMessage());
		}
	}
	
	
	/**
	 * Map tarde json to Trade POJO
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 */
	private static Trade getTrade(String json) throws JsonProcessingException{
		TypeReference<Trade> tradeListRef = new TypeReference<Trade>() { };		
		return objectMapper.readValue(json, tradeListRef);	
	}
}

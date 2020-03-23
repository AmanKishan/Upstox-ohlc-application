package com.upstox.upstoxohlc.fsm;

import com.upstox.upstoxohlc.dto.Trade;
import com.upstox.upstoxohlc.dto.TradeOHLCResponse;

/**
 * Machine to compute ohlc
 * @author arastogi
 */
public class TradeMachine {
	
	private static TradeOHLCResponse oldOhlc;
	
	public TradeMachine() {
		oldOhlc = new TradeOHLCResponse();
		oldOhlc.setSymbol(null);
		oldOhlc.setVolume(0.0);
	}
	
	public TradeOHLCResponse getOhlc(Trade trade, int barNum) {
		TradeOHLCResponse newOhlc = null;  
		Double tradePrice = trade.getTradePrice();
		if(oldOhlc.getSymbol() == null) {
			newOhlc = new TradeOHLCResponse();
			newOhlc.setBarNum(barNum);
			newOhlc.setOpen(tradePrice);
			newOhlc.setHigh(tradePrice);
			newOhlc.setLow(tradePrice);
			newOhlc.setClose(0.0);
			newOhlc.setEvent("ohlc_notify");
			newOhlc.setSymbol(trade.getStkName());
			newOhlc.setVolume(getAggregatedVolumn(trade.getTradeQty()));
		}else {
			newOhlc = getNewTrade(trade,barNum);
		}
		deepCopy(newOhlc);
		return newOhlc;
	}
	
	/**
	 * Hold previous trade value
	 * @param newOhlc
	 */
	private void deepCopy(TradeOHLCResponse newOhlc) {
		oldOhlc.setBarNum(newOhlc.getBarNum());
		oldOhlc.setClose(newOhlc.getClose());
		oldOhlc.setEvent(newOhlc.getEvent());
		oldOhlc.setHigh(newOhlc.getHigh());
		oldOhlc.setLow(newOhlc.getLow());
		oldOhlc.setOpen(newOhlc.getOpen());
		oldOhlc.setSymbol(newOhlc.getSymbol());
		oldOhlc.setVolume(newOhlc.getVolume());
	}
	
	/**
	 * compute next trade
	 * @param trade
	 * @param barNum
	 * @return
	 */
	private TradeOHLCResponse getNewTrade(Trade trade, int barNum) {
		Double newtradePrice = trade.getTradePrice();
		TradeOHLCResponse newOhlc = new TradeOHLCResponse();
		newOhlc.setBarNum(barNum);
		newOhlc.setEvent("ohlc_notify");
		newOhlc.setOpen(oldOhlc.getOpen());
		newOhlc.setHigh(getHigh(newtradePrice));
		newOhlc.setLow(getLow(newtradePrice));
		newOhlc.setClose(oldOhlc.getClose());
		newOhlc.setSymbol(trade.getStkName());
		newOhlc.setVolume(getAggregatedVolumn(trade.getTradeQty()));
		return newOhlc;
	}
	
	/**
	 * Compute low 
	 * @param newTradePrice
	 * @return
	 */
	private Double getLow(Double newTradePrice  ) {
		if(newTradePrice < oldOhlc.getLow() ) {
			return newTradePrice;
		}
		return oldOhlc.getLow();
	}
	
	/**
	 * Compute high
	 * @param newTradePrice
	 * @return
	 */
	private Double getHigh(Double newTradePrice  ) {
		if(newTradePrice > oldOhlc.getHigh() ) {
			return newTradePrice;
		}
		return oldOhlc.getHigh();
	}
	
	/**
	 * Compute voulmn 
	 * @param newVolumn
	 * @return
	 */
	private Double getAggregatedVolumn(Double newVolumn) {
		return newVolumn + oldOhlc.getVolume();
	}
	
	/**
	 * Reset the old trade when as specific interval i.e, 15 seconds 
	 */
	public void restOld() {
		oldOhlc.setSymbol(null);
	}
	
}

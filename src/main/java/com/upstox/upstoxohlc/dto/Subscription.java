package com.upstox.upstoxohlc.dto;

/**
 * @author arastogi
 * User Subscription of trades
 */
public class Subscription {

	private String event;
	private String symbol;
	private int interval;
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subscription [event=");
		builder.append(event);
		builder.append(", symbol=");
		builder.append(symbol);
		builder.append(", interval=");
		builder.append(interval);
		builder.append("]");
		return builder.toString();
	}	
	
}

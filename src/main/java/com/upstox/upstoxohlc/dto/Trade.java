package com.upstox.upstoxohlc.dto;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Trades data
 * @author arastogi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Trade {

	@JsonProperty("sym")
	private String stkName;

	@JsonProperty("T")
	private String tradeType;

	@JsonProperty("P")
	private Double tradePrice;

	@JsonProperty("Q")
	private Double tradeQty;

	@JsonProperty("TS")
	@JsonIgnore
	private BigInteger ts;

	@JsonProperty("side")
	@JsonIgnore
	private String side;

	@JsonProperty("TS2")
	private BigInteger timeStamp;

	public String getStkName() {
		return stkName;
	}

	public void setStkName(String stkName) {
		this.stkName = stkName;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Double getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(Double tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Date getDate() {
		if(timeStamp != null)
			return new Date(timeStamp.longValue());
		else
			return null;
	}

	public void setTimeStamp(BigInteger timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Double getTradeQty() {
		return tradeQty;
	}

	public void setTradeQty(Double tradeQty) {
		this.tradeQty = tradeQty;
	}

	public BigInteger getTs() {
		return ts;
	}

	public void setTs(BigInteger ts) {
		this.ts = ts;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Trade [stkName=");
		builder.append(stkName);
		builder.append(", tradeType=");
		builder.append(tradeType);
		builder.append(", tradePrice=");
		builder.append(tradePrice);
		builder.append(", tradeQty=");
		builder.append(tradeQty);
		builder.append(", ts=");
		builder.append(ts);
		builder.append(", side=");
		builder.append(side);
		builder.append(", timeStamp=");
		builder.append(timeStamp);
		builder.append(", Date=");
		builder.append(getDate());
		builder.append("]");
		return builder.toString();
	}
}

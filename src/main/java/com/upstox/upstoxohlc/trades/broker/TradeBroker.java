package com.upstox.upstoxohlc.trades.broker;

import java.util.concurrent.ArrayBlockingQueue;

import com.upstox.upstoxohlc.dto.Subscription;
import com.upstox.upstoxohlc.dto.Trade;

/**
 * Message broker in pub-sub
 * @author arastogi
 */
public class TradeBroker {
	
	public ArrayBlockingQueue<Trade> queue = new ArrayBlockingQueue<>(1);
	private Subscription subscription;
	
	public TradeBroker(Subscription subscription){
		this.subscription = subscription;
	}
 
    public void put(Trade data) throws InterruptedException
    {
        this.queue.put(data);
    }
 
    public Trade get() throws InterruptedException
    {
        return this.queue.take();
    }
    
    public Subscription getSubscription()
    {
        return this.subscription;
    }
}

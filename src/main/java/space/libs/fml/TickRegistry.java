/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * Contributors:
 *     cpw - implementation
 */

package space.libs.fml;

import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.Queues;

import cpw.mods.fml.relauncher.Side;

public class TickRegistry {

    public static class TickQueueElement implements Comparable<TickQueueElement> {
        public TickQueueElement(IScheduledTickHandler ticker, long tickCounter) {
            this.ticker = ticker;
            update(tickCounter);
        }

        @Override
        public int compareTo(TickQueueElement o) {
            return (int)(next - o.next);
        }

        public void update(long tickCounter) {
            next = tickCounter + Math.max(ticker.nextTickSpacing(),1);
        }

        private long next;

        public IScheduledTickHandler ticker;

        public boolean scheduledNow(long tickCounter) {
            return tickCounter >= next;
        }
    }

    public static PriorityQueue<TickQueueElement> clientTickHandlers = Queues.newPriorityQueue();

    public static PriorityQueue<TickQueueElement> serverTickHandlers = Queues.newPriorityQueue();

    public static AtomicLong clientTickCounter = new AtomicLong();

    public static AtomicLong serverTickCounter = new AtomicLong();

    public static void registerScheduledTickHandler(IScheduledTickHandler handler, Side side) {
        getQueue(side).add(new TickQueueElement(handler, getCounter(side).get()));
    }

    /**
     * @param side the side to get the tick queue for
     * @return the queue for the effective side
     */
    public static PriorityQueue<TickQueueElement> getQueue(Side side) {
        return side.isClient() ? clientTickHandlers : serverTickHandlers;
    }

    public static AtomicLong getCounter(Side side) {
        return side.isClient() ? clientTickCounter : serverTickCounter;
    }

    public static void registerTickHandler(ITickHandler handler, Side side) {
        registerScheduledTickHandler(new SingleIntervalHandler(handler), side);
    }

    @SuppressWarnings("all")
    public static void updateTickQueue(List<IScheduledTickHandler> ticks, Side side) {
        synchronized (ticks) {
            ticks.clear();
            long tick = getCounter(side).incrementAndGet();
            PriorityQueue<TickQueueElement> tickHandlers = getQueue(side);

            while (true) {
                if (tickHandlers.size()==0 || !tickHandlers.peek().scheduledNow(tick)) {
                    break;
                }
                TickRegistry.TickQueueElement tickQueueElement  = tickHandlers.poll();
                if (tickQueueElement != null) {
                    tickQueueElement.update(tick);
                    tickHandlers.offer(tickQueueElement);
                    ticks.add(tickQueueElement.ticker);
                }
            }
        }
    }

}

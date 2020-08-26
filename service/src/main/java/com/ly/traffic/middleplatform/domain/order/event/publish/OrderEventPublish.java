package com.ly.traffic.middleplatform.domain.order.event.publish;


import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.ly.traffic.middleplatform.demo.MainProcessorListenerExample;
import com.ly.traffic.middleplatform.domain.order.event.OrderEvent;
import com.ly.traffic.middleplatform.test.simulator.SecondProcessorListener;
import com.ly.traffic.middleplatform.test.simulator.TicketServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Executors;

/**
 * 订单事件发送MQ
 *
 * @author liugw
 * @date 2020/08/21
 */
@Service
public class OrderEventPublish {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventPublish.class);

    private AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));

    public OrderEventPublish() {
        this.eventBus.register(new MainProcessorListenerExample());
        this.eventBus.register(new TicketServiceListener());
        this.eventBus.register(new SecondProcessorListener());
    }

    public void publish(OrderEvent orderEvent) {
//        eventBus.post(orderEvent);
        eventBus.post(JSON.toJSONString(orderEvent));
    }

}

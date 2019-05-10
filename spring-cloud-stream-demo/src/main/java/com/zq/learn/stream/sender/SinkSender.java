package com.zq.learn.stream.sender;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;

/**
 * @author qun.zheng
 * @description: TODO
 * @date 2019/4/29下午3:32
 */
public interface SinkSender {

    @Output(Sink.INPUT)
    MessageChannel output();
}

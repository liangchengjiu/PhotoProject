package com.dawson.common.retry.sender;


import com.dawson.common.vo.BizLogVo;

public interface RetryLogMessageSender {

    void send(BizLogVo vo);
}

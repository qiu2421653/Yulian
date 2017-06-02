package com.naga.common.handler;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.naga.common.util.SpringContextUtil;

/**
 * WebSerivce日志管理类
 */
public class WsLogHandler extends AbstractHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(WsLogHandler.class);

    /**
     * 把WEPService访问的请求信息和响应信息输出到日志中
     * @param msgContext 消息内容
     */
    public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
        // 向日志文件中输出请求和响应SOAP消息
        logger.info(SpringContextUtil.getMessage("msg.common.10031", msgContext.getTo().getAddress(),
                msgContext.getEnvelope().toString()));
        return InvocationResponse.CONTINUE;
    }
}

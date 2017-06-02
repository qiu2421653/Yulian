package com.naga.common.module;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisDescription;
import org.apache.axis2.description.AxisModule;
import org.apache.axis2.modules.Module;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

/**
 * WebService日志输出用Module类
 */
public class WsLogModule implements Module {
    
    /**
     * 初期化处理
     * @param configContext ConfigurationContext
     * @param module axis模块
     * @throws AxisFault
     */
    public void init(ConfigurationContext configContext, AxisModule module) throws AxisFault {
    }
    /**
     * engageNotify
     * @param configContext ConfigurationContext
     * @param module axis模块
     * @throws AxisFault
     */
    public void engageNotify(AxisDescription axisDescription) throws AxisFault {
    }
    /**
     * 结束处理
     * @param configurationContext ConfigurationContext
     * @throws AxisFault
     */
    public void shutdown(ConfigurationContext configurationContext) throws AxisFault {
    }
    /**
     * 提供策略
     * @param policy Policy
     * @param axisDescription AxisDescription
     * @throws AxisFault
     */
    public void applyPolicy(Policy policy, AxisDescription axisDescription) throws AxisFault {
    }
    /**
     * 是否支持断言
     * @param assertion Assertion
     * @return true:支持 false:不支持
     */
    public boolean canSupportAssertion(Assertion assertion) {
        return true;
    }
}

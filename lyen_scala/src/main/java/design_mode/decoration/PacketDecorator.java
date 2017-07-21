package design_mode.decoration;

import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;

/**
 * Created by lyen on 16-9-14.
 * 装饰者，只负责做增强处理
 * 维护核心组件component对象 ，他负责告诉子类，其核心业务逻辑应该全权委托component完成
 */
public abstract class PacketDecorator implements IPacketCreator {
    IPacketCreator component;

    public PacketDecorator(IPacketCreator component) {
        this.component = component;
    }
}

package design_mode.decoration;

/**
 * Created by lyen on 16-9-14.
 *       被装饰者
 *       具体组件，构造要发布的核心内容
 *       但是不负责构造一个格式工整，可直接发布的数据格式
 */
public class PacketBodyCreator implements IPacketCreator {

    public String handleContent() {
        return "  Content of Packet\n";//构造核心数据，但不包括格式
    }
}

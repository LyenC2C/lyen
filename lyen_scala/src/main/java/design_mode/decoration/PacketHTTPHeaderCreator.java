package design_mode.decoration;

import javax.swing.plaf.PanelUI;

/**
 * Created by lyen on 16-9-14.
 * 负责给给定的内容加上HTTP头部
 */
public class PacketHTTPHeaderCreator extends PacketDecorator {
    public PacketHTTPHeaderCreator(IPacketCreator component) {
        super(component);
    }

    public String handleContent() {
        StringBuffer sb = new StringBuffer();
        sb.append("Cache-Control:no-cache\n");
        sb.append("Date:Mon,31Dec201204:25GMT\n");
        sb.append(super.component.handleContent());
        return sb.toString();
    }
}

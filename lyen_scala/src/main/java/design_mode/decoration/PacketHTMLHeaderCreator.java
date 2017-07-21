package design_mode.decoration;

/**
 * Created by lyen on 16-9-14.
 * 负责将给定的内容格式化为html
 */
public class PacketHTMLHeaderCreator extends PacketDecorator {

    public PacketHTMLHeaderCreator(IPacketCreator component) {
        super(component);
    }

    public String handleContent() {  //将给定数据封装成HTML
        StringBuffer sb = new StringBuffer();
        sb.append("<html>\n");
        sb.append("  <body>\n");
        sb.append(super.component.handleContent());
        sb.append("  <body>\n");
        sb.append("<html>");
        return sb.toString();
    }
}

package chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by lyen on 16-11-11.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup cgroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        cgroup.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "in" + "\n");
        cgroup.add(incoming);
        System.out.println(cgroup.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        cgroup.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "out" + "\n");
        cgroup.remove(incoming);
        System.out.println(cgroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : cgroup) {
            if (channel != incoming) {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg + "\n");
            } else {
                channel.writeAndFlush("[you]" + msg + "\n");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}

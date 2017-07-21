package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyen on 16-11-10.
 */
public class SimpleMessageHandler extends ChannelInboundHandlerAdapter {
    private List<MyClient> clients = new ArrayList<MyClient>();
    public SimpleMessageHandler(List<MyClient> clients){
        this.clients = clients;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("message from telnet:" + msg);
        for (MyClient client: clients){
            Channel ch = client.getChannel();
            if(ch != null){
                ch.writeAndFlush(safeDuplicate(msg));
            }
        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }



    private static Object safeDuplicate(Object message) {
        if (message instanceof ByteBuf) {
            return ((ByteBuf) message).retainedDuplicate();
        } else if (message instanceof ByteBufHolder) {
            return ((ByteBufHolder) message).retainedDuplicate();
        } else {
            return ReferenceCountUtil.retain(message);
        }
    }

}

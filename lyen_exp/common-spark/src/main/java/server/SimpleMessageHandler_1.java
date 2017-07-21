package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by lyen on 16-11-10.
 */
public class SimpleMessageHandler_1 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("message from client:" + msg);
    }

}

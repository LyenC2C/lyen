package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by lyen on 16-11-10.
 */
public class SimpleMessageHandler_2 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("message from client:" + msg);
    }

}

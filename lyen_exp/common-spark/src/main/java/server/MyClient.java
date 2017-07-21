package server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.TimeUnit;

/**
 * Created by lyen on 16-11-10.
 */
public class MyClient {

    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();

    public MyClient(String host, int port) {

        try {
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline ch = socketChannel.pipeline();
                    ch.addLast("encode", new StringEncoder());
                    ch.addLast(new SimpleHandler());
                }
            });
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.remoteAddress(host, port);
            doConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        try {
            Channel ch = b.connect().sync().channel();
            if (ch != null) {
                return ch;
            } else {
                doConnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doConnect() {
        ChannelFuture future = b.connect();
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) future.channel().eventLoop().schedule(new Runnable() {
                    public void run() {
                        System.out.println("no flume server open");
                        doConnect();
                    }
                }, 1, TimeUnit.SECONDS);
            }
        });
    }
}

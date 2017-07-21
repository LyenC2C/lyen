package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by lyen on 16-11-10.
 */
public class MyServer {


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final List<MyClient> clients = new ArrayList<MyClient>();
        clients.add(new MyClient("127.0.0.1",7879));
        clients.add(new MyClient("127.0.0.1",7880));
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup);
            sb.channel(NioServerSocketChannel.class);
            sb.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline ch = socketChannel.pipeline();
                    ch.addLast("decode",new StringDecoder());
                    ch.addLast(new SimpleMessageHandler(clients));
                }
            });
            sb.bind(7878).sync().channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


}
package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by lyen on 16-11-10.
 */
public class MyServer_2 {


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup);
            sb.channel(NioServerSocketChannel.class);
            sb.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline ch = socketChannel.pipeline();
                    ch.addLast("decode",new StringDecoder());
                    ch.addLast(new SimpleMessageHandler_2());
                }
            });
            sb.bind(7880).sync().channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


}
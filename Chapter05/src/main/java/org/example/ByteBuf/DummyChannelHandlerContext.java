package org.example.ByteBuf;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import io.netty.util.concurrent.EventExecutor;

/**
 * Created by kerr.
 */
public class DummyChannelHandlerContext extends AbstractChannelPoolHandler {

    @Override
    public void channelCreated(Channel ch) throws Exception {
        ch.pipeline().addLast(

        )
    }
}
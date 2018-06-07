package cn.guokai.studynio.myTcp;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
/**
 * Created by guokai on 2018/6/4.
 */
public class Service {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(10003));

        Runnable runnable=()->MySelectUtils.selector(serverSocketChannel,SelectionKey.OP_ACCEPT);

        new Thread(runnable).start();

        System.out.println("服务器准备就绪 进入select进行调度");

    }

}


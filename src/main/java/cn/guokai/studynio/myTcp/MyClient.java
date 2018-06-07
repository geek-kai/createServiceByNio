package cn.guokai.studynio.myTcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by guokai on 2018/6/4.
 */
public class MyClient {

    public static void main(String[] args) throws IOException {
        Runnable runnable = () -> clientExecute();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();



    }

    public static void clientExecute() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            boolean localhost = socketChannel.connect(new InetSocketAddress("localhost", 10003));
            System.out.println(localhost);
            String newData = "New String to write to file..." + System.currentTimeMillis();
            int i = 0;
            ByteBuffer allocate = ByteBuffer.allocate(48);
            //模拟分批次输出 发现会造成数据丢失
            while (i < 10) {
                try {
                    //模拟网络延迟
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                allocate.put(newData.getBytes("utf-8"));
                allocate.flip();
                while (allocate.hasRemaining()) {
                    socketChannel.write(allocate);
                }
                allocate.position(0);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                socketChannel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

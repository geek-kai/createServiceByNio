package cn.guokai.studynio.myTcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

/**
 * Created by guokai on 2018/6/4.
 */
public class MySelectUtils {

    private static Selector selector = null;

    static {
        try {
            if (selector == null)
                selector = Selector.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MySelectUtils() throws IOException {
    }


    public static void executeKey(SelectionKey key)  {
        try {
            if (key.isAcceptable()) {
                System.out.println(Thread.currentThread().getName()+"接收就绪");
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = channel.accept();
                //去除阻塞
                socketChannel.configureBlocking(false);
                socketChannel.register(selector,SelectionKey.OP_READ);
            } else if (key.isConnectable()) {
                System.out.println("连接就绪");
            } else if (key.isReadable()) {
                SocketChannel channel = (SocketChannel)key.channel();
                ByteBuffer allocate = ByteBuffer.allocate(1024);
                int line=0;
                //只要不是-1证明还在读数据
                while((line=channel.read(allocate))!=-1){

                }
                allocate.flip();
                System.out.println(Thread.currentThread().getName()+"读到了:"+allocate.limit());
//                while (allocate.hasRemaining()){
//                    System.out.print((char) allocate.get());
//                }
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                channel.close();
            } else if (key.isWritable()) {
                System.out.println("写就绪");
                key.channel().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * lister 监听事件
     *
     * @param selectableChannel
     * @param lister
     */
    public static void selector(SelectableChannel selectableChannel, Integer lister) {

        while (true) {
            int readyChannels=0;
            try {
                selectableChannel.configureBlocking(false);
                selectableChannel.register(selector, lister);
                 readyChannels = selector.select();
            } catch (Exception e) {
                e.printStackTrace();
            }
                if (readyChannels == 0) continue;
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                //  selectedKeys 不会自动处理已经处理过的 监听事件 网上给的很多方法都是用remove移除 因为 流只能用一次尝试下这样会不会移除
                //用stream会出现并发修改问题
            /**
             * stream的底层应该是依赖于iterator 所有会出现并发修改问题
             *  parallelStream能成功的原因应该是 在remove之前 已经遍历完了 所以没有报错
             */
                selectedKeys.parallelStream().forEach(key -> {
                    MySelectUtils.executeKey(key);
                    //移除它 否则下次不会监听到
                    System.out.println(Thread.currentThread().getName()+"准备移除"+key.channel().toString()+"hash"+key.channel().hashCode());
                    System.out.println(selectedKeys.size());
                    System.out.println(selectedKeys.contains(key));
                        selectedKeys.remove(key);
                    System.out.println(Thread.currentThread().getName()+"移除"+key.channel().toString()+"hash"+key.channel().hashCode()+"成功");


                });
                //不采用流的写法 注意用完的key要移除
//                for (SelectionKey selectedKey : selectedKeys) {
//                    if (selectedKey.isAcceptable()) {
//                        System.out.println("接收就绪");
//                        ServerSocketChannel channel = (ServerSocketChannel) selectedKey.channel();
//                        SocketChannel socketChannel = channel.accept();
////                        selectedKeys.remove(selectedKey);
//                    }
//                }
            }


    }

}

package cn.guokai.studynio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by guokai on 2018/6/4.
 */
public class Demo02 {

    @Test
    public void test() throws IOException {
        Selector selector = Selector.open();


        System.out.println(SelectionKey.OP_READ);

        System.out.println(SelectionKey.OP_WRITE);

        System.out.println((SelectionKey.OP_CONNECT | SelectionKey.OP_CONNECT));

        int opConnect = SelectionKey.OP_CONNECT;

        int a = SelectionKey.OP_CONNECT & SelectionKey.OP_CONNECT;

        System.out.println(a == opConnect);

    }

    @Test
    public void test3() throws IOException {
        String newData = "New String to write to file..." + System.currentTimeMillis();
        String toPath = "E://ceshi/to.txt";
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(toPath, "rw");
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.put(newData.getBytes());
        buf.flip();
        FileChannel channel = randomAccessFile2.getChannel();
        while (buf.hasRemaining()){
            channel.write(buf);
        }
        //通道的数据强制写出的磁盘中 用true会把元数据也写入 如权限信息等
        channel.force(true);

    }
    @Test
    public void test2(){
        Set<String>set=new HashSet<>();
        set.add("123");
        set.add("222");
        set.add("333");
        Iterator<String> iterator = set.iterator();
//        while (iterator.hasNext()){
//            String next = iterator.next();
//
//            if(next.equals("333"))
//                set.remove(next);
//        }
        set.parallelStream().forEach(s -> {
            if(s.equals("333"))
                set.remove(s);
        });
    }
}
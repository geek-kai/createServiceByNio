package cn.guokai.studynio;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by guokai on 2018/5/29.
 */
public class Demo {

    @Test
    public void test() throws IOException {
        String localPath = "E://ceshi/cehngyu.txt";
        RandomAccessFile randomAccessFile = new RandomAccessFile(localPath, "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.putInt(100);
        int number = channel.read(byteBuffer);
        while (number != -1) {
            System.out.printf("readNumber:%d", number);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println((char) byteBuffer.get());
            }
            byteBuffer.clear();
            number = channel.read(byteBuffer);
        }
        channel.close();
    }

    @Test
    public void test2() throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.putInt(100);
        byteBuffer.flip();
        byteBuffer.clear();
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }
    }

    @Test
    public void test3() throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.putInt(100);
        byteBuffer.flip();
        System.out.println("0" + byteBuffer.get(0));
        System.out.println("0" + byteBuffer.get(3));
        byteBuffer.compact();
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }
    }

    @Test
    public void test4() throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.putInt(100);
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            byteBuffer.get();
            if (byteBuffer.position() == 3) {
                System.out.println(byteBuffer.position());
                byteBuffer.mark();
            }
        }
        Buffer reset = byteBuffer.reset();
        System.out.println(reset);

    }

    @Test
    public void test5() throws IOException {
        String fromPath = "E://ceshi/from.txt";
        RandomAccessFile randomAccessFile = new RandomAccessFile(fromPath, "rw");
        String toPath = "E://ceshi/to.txt";
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(toPath, "rw");

        FileChannel fromchannel = randomAccessFile.getChannel();

        FileChannel tochannel = randomAccessFile2.getChannel();

        tochannel.transferFrom(fromchannel, 0, fromchannel.size());



    }
}


package com.cn.summary.util;

import java.io.*;

/**
 * Created on 2019/11/1.
 *
 * @author hhx
 */
public class FileUtils {
    public static void FileOpreate(String source,String target) throws IOException {
        //读取文件(字节流)
        InputStream in = new FileInputStream(source);
        //写入相应的文件
        OutputStream out = new FileOutputStream(target);
        //读取数据
        //一次性取多少字节
        byte[] bytes = new byte[2048];
        //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
        int n = -1;
        //循环取出数据
        while ((n = in.read(bytes,0,bytes.length)) != -1) {
            //转换成字符串
            String str = new String(bytes,0,n,"UTF-8");
            //写入相关文件
            out.write(bytes, 0, n);
        }
        //关闭流
        in.close();
        out.close();
    }


    public static void main(String[] args) {
        try {
            FileOpreate("/Users/hhx/tempfile/test.txt","/Users/hhx/tempfile/result.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

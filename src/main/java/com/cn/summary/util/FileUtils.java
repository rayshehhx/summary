package com.cn.summary.util;

import java.io.*;

/**
 * Created on 2019/11/1.
 */
public class FileUtils {

    public static void FileOpreate(String source, String target) throws IOException {
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
        while ((n = in.read(bytes, 0, bytes.length)) != -1) {
            //转换成字符串
            String str = new String(bytes, 0, n, "UTF-8");
            //写入相关文件
            out.write(bytes, 0, n);
        }
        //关闭流
        in.close();
        out.close();
    }


    /**
     * 按行读取文件
     * 通过OutputStreamWriter写文件
     *
     * @param source 源文件
     * @param target 生成的目标文件
     */
    public static void ReadFileByLineAndWriteFileByOutPutStream(String source, String target) {
        File file = new File(source);
        InputStream is = null;
        Reader reader = null;

        File outFile = new File(target);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        BufferedReader bufferedReader = null;
        try {
            is = new FileInputStream(file);
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);


            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            fos = new FileOutputStream(outFile);
            osw = new OutputStreamWriter(fos);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                osw.write(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedReader)
                    bufferedReader.close();
                if (null != reader)
                    reader.close();
                if (null != is)
                    is.close();
                if (null != osw) {
                    try {
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != fos) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 按字符读取文件
     * 通过FileWriter写文件
     *
     * @param source 源文件
     * @param target 生成的目标文件
     */
    public static void ReadFileByCharAndWriteByFileWriter(String source, String target) {
        File file = new File(source);
        InputStream is = null;
        Reader isr = null;

        File outFile = new File(target);
        FileWriter fw = null;
        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            fw = new FileWriter(outFile);
            int index = 0;
            while (-1 != (index = isr.read())) {
                fw.write((char) index);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != isr) {
                    isr.close();
                }
                if (null != fw) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String source = "";
        String target = "";
        ReadFileByCharAndWriteByFileWriter(source, target);
    }
}

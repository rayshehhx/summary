package com.cn.summary.util;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelGet {
    /**

     1、根据小说存放位置创建file对象

     2、根据网页结构编写正则，创建pattern对象

     3、编写循环，创建向所有小说章节页面发起网络请求的url对象

     4、网络流BufferReader

     5、创建输入流

     6、循环读取请求得到的内容，使用正则匹配其中的内容

     7、将读取到的内容写入本地文件，知道循环结束

     8、注意代码中的异常处理

     * @param args

     */
    public static void main(String[] args) {
        String baseUrl = "https://www.qkshu8.com/book/wuxin2jia/";
        String nextUrl = "10010.html";
        String destFilePath = "/Users/hhx/novel/无心二嫁.txt";
        System.out.println("开始爬取数据...");
        long startTime = System.currentTimeMillis();
        getNovel(baseUrl, nextUrl, destFilePath);
        long endTime = System.currentTimeMillis();
        System.out.println("用时 " + (endTime - startTime) / 1000 + "秒...");
    }

    public static void getNovel(String baseUrl, String nextUrl, String destFilePath) {
        try {
            File destFile = new File(destFilePath);
            // 目标文件存在则删除
            if (!destFile.exists()) {
                destFile.delete();
            }
            destFile.createNewFile();

            FileWriter fw = new FileWriter(destFile);
            String realUrl, resultContent;
            StringBuffer sb = new StringBuffer();
            BufferedReader br = null;
            // 正文正则匹配表达式
            Pattern contentPat = Pattern.compile("<div class=\"content\">(.+?)</div>");
            // 标题正则表达式
            Pattern titlePat = Pattern.compile("<title>(.+?)_无心二嫁_去看书网</title>\n");
            //下一章正则表达式
            Pattern nextChapterPat = Pattern.compile("<div id=\"view-nav-right\">\n" +
                    "                        <a href=\"https://www.qkshu8.com/book/wuxin2jia/\">&nbsp;&nbsp;主目录&nbsp;&nbsp;</a>\n" +
                    "                        <a href=\"(.+?)\" title=\"(.+?)\">→ 下一章</a>\n" +
                    "                    </div>");
            Pattern next2ChapterPat = Pattern.compile("<div id=\"view-nav-right\">\n" +
                    "                        <a href=\"(.+?)\" title=\"81第79章 心的尝试\">上一章  ←</a>\n" +
                    "                        <a href=\"https://www.qkshu8.com/book/wuxin2jia/\">&nbsp;&nbsp;主目录&nbsp;&nbsp;</a>\n" +
                    "                        <a href=\"(.+?)\" title=\"83第81章 文皇寿宴\">→ 下一章</a>\n" +
                    "                    </div>");


            Matcher matcher;
            // 下一张的url以 / 开头则是最新章节
            for(int i=0;i<100;i++){
                System.out.println("nextUrl=="+nextUrl);
                realUrl = baseUrl + nextUrl;
                // 获取目标url的response
                br = new BufferedReader(new InputStreamReader(new URL(realUrl).openStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                // 替换空格和换行符
                resultContent = sb.toString().replaceAll("<p>", "")
                        .replaceAll("</p>", "")
                        .replaceAll("<div class=\"tui\">(.+?)</div>", "");
                // 换行
                fw.write("\r\n\r\n");
                // 匹配标题
                matcher = titlePat.matcher(resultContent);
                if (matcher.find()) {
                    fw.write(matcher.group(1));
                }
                fw.write("\r\n");
                // 匹配正文
                matcher = contentPat.matcher(resultContent);
                if (matcher.find()) {
                    fw.write(matcher.group(1));
                }

                // 匹配下一章url
                matcher = nextChapterPat.matcher(resultContent);
                if (matcher.find()) {
                    nextUrl = matcher.group(1);
                    System.out.println("getNextChapterPat:"+nextUrl);
                }

                // 匹配下一章url
                matcher = next2ChapterPat.matcher(resultContent);
                if (matcher.find()) {
                    nextUrl = matcher.group(2);
                    System.out.println("getNext2ChapterPat:"+nextUrl);
                }

                // 清空
                sb.delete(0, sb.length());
            }
            if (br != null) {
                br.close();
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

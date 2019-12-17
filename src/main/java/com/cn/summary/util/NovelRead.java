package com.cn.summary.util;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelRead {
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
        String baseUrl = "https://wab.f96.net/150/150331/";
        String nextUrl = "45561293.html";
        String destFilePath = "/Users/hhx/novel/test.txt";
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
            Pattern contentPat = Pattern.compile("<div id=\"chaptercontent\" class=\"Readarea ReadAjax_content\">(.+?)</div>");
            // 标题正则表达式
            Pattern titlePat = Pattern.compile("<title>(.+?)</title>");
            // 下一章正则表达式
            Pattern nextPagePat = Pattern.compile("<a href=\"/150/150331/\" id=\"pt_mulu\" class=\"Readpage_up\">目录</a><a href=\"/150/150331/(.+?)\" id=\"pt_next\" class=\"(.+?)\">下一页</a></p>");
            Pattern nextChapterPat = Pattern.compile("<a href=\"/150/150331/\" id=\"pt_mulu\" class=\"Readpage_up\">目录</a><a href=\"/150/150331/(.+?)\" id=\"pt_next\" class=\"(.+?)\">下一章</a>");
            Matcher matcher;
            // 下一张的url以 / 开头则是最新章节
            for(int i=0;i<220;i++){
                System.out.println("nextUrl=="+nextUrl);
                realUrl = baseUrl + nextUrl;
                // 获取目标url的response
                br = new BufferedReader(new InputStreamReader(new URL(realUrl).openStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                // 替换空格和换行符
                resultContent = sb.toString().replaceAll("&nbsp;", "")
                        .replaceAll("<br/>", "")
                        .replaceAll("&amp;amp;nbsp", "")
                        .replaceAll("本章未完，点击下一页继续阅读&amp;lt;/<br/><br/>\n" +
                                "    &nbsp;&nbsp;&nbsp;&nbsp;#p#副标题#e#【12看书&nbsp;】一秒记住，更新快，无弹窗，免费读！", "")
                ;
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
                // 匹配下一页url
                matcher = nextPagePat.matcher(resultContent);
                if (matcher.find()) {
                    nextUrl = matcher.group(1);
                    System.out.println("getNextPageUrl:"+nextUrl);
                }
                //匹配下一章
                matcher = nextChapterPat.matcher(resultContent);
                if (matcher.find()) {
                    nextUrl = matcher.group(1);
                    System.out.println("getNextChapterPat:"+nextUrl);
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

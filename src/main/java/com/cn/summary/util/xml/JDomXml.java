package com.cn.summary.util.xml;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
/**
 * Created on 2019/11/5.
 */
public class JDomXml {
    /**
     * 生成xml方法
     */
    public static void createXml(){
        try {
            // 1、生成一个根节点
            Element rss = new Element("rss");
            // 2、为节点添加属性
            rss.setAttribute("version", "2.0");
            // 3、生成一个document对象
            Document document = new Document(rss);

            Element channel = new Element("channel");
            rss.addContent(channel);
            Element title = new Element("title");
            title.setText("国内最新新闻");
            channel.addContent(title);

            Format format = Format.getCompactFormat();
            // 设置换行Tab或空格
            format.setIndent(" ");
            format.setEncoding("UTF-8");

            // 4、创建XMLOutputter的对象
            XMLOutputter outputer = new XMLOutputter(format);
            // 5、利用outputer将document转换成xml文档
            File file = new File("/Users/hhx/tempfile/rssNew.xml");
            outputer.output(document, new FileOutputStream(file));

            System.out.println("生成rssNew.xml成功");
        } catch (Exception e) {
            System.out.println("生成rssNew.xml失败");
        }
    }

    public static void main(String[] args) {
        createXml();
    }
}

package com.foolself.demo.utils;

import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author http://foolself.github.io
 * @date 2018/10/25 21:34
 */
public class MyUtils {

    public static final String[] PIC_SUFFIX = {"jpg", "JPG", "png", "PNG", "gif", "GIF", "tif", "TIF"};

    public static Set<String> getTags(List<Article> articleList) {
        Set<String> tagSet = new HashSet<String>();
        for (Article article : articleList) {
            for (Tag tag : article.getTagList()) {
                tagSet.add(tag.getName());
            }
        }
        return tagSet;
    }

    public static String getFileSuffix(String filename) {
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        return suffix;
    }

    public static Boolean isPicture(String filename){
        return Arrays.asList(PIC_SUFFIX).contains(getFileSuffix(filename));
    }

    public static String getUnityFileName(String filename) {
        return String.valueOf((new Date()).getTime()) + "." + getFileSuffix(filename);
    }

    public static Date getDate(String d) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(d);
    }

    public static void main(String[] args) {
        String filename = "34rklnv.PNG";
        System.out.println(isPicture(filename));
        System.out.println(getUnityFileName(filename));
    }
}

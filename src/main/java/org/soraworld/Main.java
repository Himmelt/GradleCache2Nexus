package org.soraworld;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        Pattern pattern = Pattern.compile("root_new/[a-z0-9.-]{1,100}/");
        try {
            runtime.exec("cmd /c dir /b/a-d/s > list.txt");
            Thread.sleep(100);
            File list = new File("list.txt");
            File root = new File("root");
            if (list.exists() && list.isFile() && root.exists() && root.isDirectory()) {
                List<String> names = FileUtils.readLines(list, Charset.defaultCharset());
                for (String src : names) {
                    src = src.replaceAll("\\\\", "/");
                    if (src.contains("/root/")) {
                        String des = src.replaceAll("root", "root_new").replaceAll("[a-f0-9]{35,45}/", "");
                        Matcher matcher = pattern.matcher(des);
                        if (matcher.find()) {
                            String temp = matcher.group().replaceAll("[.]", "/");
                            des = des.replaceAll("root_new/[a-z0-9.-]{1,100}/", temp);
                        }
                        File src_f = new File(src);
                        File des_f = new File(des);
                        FileUtils.copyFile(src_f, des_f);
                        System.out.println(src + " --> " + des);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

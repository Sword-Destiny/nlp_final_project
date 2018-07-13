package com.sworddestiny.nlp.train;

import java.io.*;
import java.util.ArrayList;

/**
 * 将国家语委得到的5万句子data/origin.txt划分为三个文件,
 * 一个训练data/train.txt,
 * 一个测试data/test.txt,
 * 一个标准答案data/result.txt
 */
public class Cut {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>(500000);
        File file = new File("data/origin.txt");
        String line = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            while ((line = br.readLine()) != null) {
                line = line.replace(" /w", "/w").replace(" /x", "/x");
                arrayList.add(line);
            }
            int len = arrayList.size();
            int halfLen = len / 2;
            FileOutputStream fos = new FileOutputStream(new File("data/train.txt"));
            for (int i = 0; i < halfLen; i++) {
                fos.write((arrayList.get(i) + "\r\n").getBytes("UTF-8"));
            }
            fos = new FileOutputStream(new File("data/test.txt"));
            for (int i = halfLen; i < len; i++) {
                String output = "";
                String[] separates = arrayList.get(i).split("\\s+");
                for (int j = 1; j < separates.length; j++) {
                    String[] words = separates[j].split("/");
                    output += words[0];
                }
                output += "\r\n";
                fos.write(output.getBytes("UTF-8"));
            }
            fos = new FileOutputStream(new File("data/result.txt"));
            for (int i = halfLen; i < len; i++) {
                String output = "";
                String[] separates = arrayList.get(i).split("\\s+");
                for (int j = 1; j < separates.length - 1; j++) {
                    String[] words = separates[j].split("/");
                    output += words[0] + "/";
                }
                String[] words = separates[separates.length - 1].split("/");
                output += words[0];
                output += "\r\n";
                fos.write(output.getBytes("UTF-8"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(line);
        }
    }
}

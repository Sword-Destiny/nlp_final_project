package com.sworddestiny.nlp.test;

import java.io.*;

/**
 * 计算正确率
 */
public class CalculateCorrect {
    public static void main(String[] args) {
        calculateCorrect("data/output.txt", "data/result.txt");
    }

    public static void calculateCorrect(String output, String result) {
        long correct = 0;
        long wrong = 0;
        String oline = null, rline = null;
        try (BufferedReader obr = new BufferedReader(new InputStreamReader(new FileInputStream(output), "UTF-8"));
             BufferedReader rbr = new BufferedReader(new InputStreamReader(new FileInputStream(result), "UTF-8"))) {
            while ((oline = obr.readLine()) != null) {
                rline = rbr.readLine();
                boolean[] rb = new boolean[rline.length()];
                boolean[] ob = new boolean[oline.length()];
                int oindex = 0;
                int rindex = 0;
                for (int i = 0; i < oline.length() - 1; i++) {
                    if (oline.charAt(i) != '/' && oline.charAt(i + 1) == '/') {
                        ob[oindex++] = true;
                    }
                    if (oline.charAt(i) != '/' && oline.charAt(i + 1) != '/') {
                        ob[oindex++] = false;
                    }
                }
                for (int i = 0; i < rline.length() - 1; i++) {
                    if (rline.charAt(i) != '/' && rline.charAt(i + 1) == '/') {
                        rb[rindex++] = true;
                    }
                    if (rline.charAt(i) != '/' && rline.charAt(i + 1) != '/') {
                        rb[rindex++] = false;
                    }
                }
                if (rindex != oindex) {
                    throw new Exception("error");
                }
                for (int i = 0; i < rindex; i++) {
                    if (rb[i] == ob[i]) {
                        correct++;
                    } else {
                        wrong++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("正确率:" + (double) correct / (correct + wrong));
    }
}

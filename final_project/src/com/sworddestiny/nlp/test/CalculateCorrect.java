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
        try (BufferedReader obr = new BufferedReader(new FileReader(output));
             BufferedReader rbr = new BufferedReader(new FileReader(result))) {
            while ((oline = obr.readLine()) != null) {
                rline = rbr.readLine();
                int oindex = 0;
                int rindex = 0;
                while (oindex < oline.length() && rindex < rline.length()) {
                    char o = oline.charAt(oindex);
                    char r = rline.charAt(rindex);
                    if (o == '/' && r == '/') {
                        correct++;
                        oindex++;
                        rindex++;
                    } else if (o != '/' && r != '/') {
                        oindex++;
                        rindex++;
                    } else if (o != '/' && r == '/') {
                        wrong++;
                        rindex++;
                    } else {
                        wrong++;
                        oindex++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("正确率:" + (double) correct / (correct + wrong));
    }
}

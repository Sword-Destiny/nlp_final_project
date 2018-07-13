package com.sworddestiny.nlp.test;

import com.sworddestiny.nlp.train.Statistics;

import java.io.*;

/**
 * 实现维特比算法对测试文件进行分词
 */
public class Divide {
    public static void main(String[] args) {
        devide_file("data/test.txt", "data/output.txt");
    }

    private static Statistics statistics;
    public static final int status = 4;

    public static final int b = 0, e = 1, m = 2, s = 3;

    static {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("data/train.statistics")))) {
            statistics = (Statistics) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 维特比算法分词
     *
     * @param str 句子
     * @return 分词结果
     */
    public static String viterbi(String str) {
        int len = str.length();
        if (len == 0) {
            return "";
        }
        double[][] weight = new double[status][len];
        int[][] path = new int[status][len];

        initial(weight, path, str.charAt(0), 0);
        cycle(weight, path, str, len);

        int[] status = getStatus(weight, path, str, len);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len - 1; i++) {
            sb.append(str.charAt(i));
            if (status[i] == e || status[i] == s) {
                sb.append('/');
            }
        }
        sb.append(str.charAt(len - 1));
        return sb.toString();
    }

    /**
     * 维特比算法,路径回溯
     *
     * @param w   权重
     * @param p   路径
     * @param str 句子
     * @param len 句子长度
     * @return 回溯结果, 状态序列
     */
    public static int[] getStatus(double[][] w, int[][] p, String str, int len) {
        int res[] = new int[len];
        int index = len - 1;
        int before_status;
        if (w[e][index] > w[s][index]) {
            res[index] = e;
            before_status = p[e][index];
        } else {
            res[index] = s;
            before_status = p[s][index];
        }
        res[index - 1] = before_status;
        while (index > 1) {
            index--;
            switch (before_status) {
                case b:
                    if (w[s][index] > w[e][index]) {
                        before_status = p[s][index];
                    } else {
                        before_status = p[e][index];
                    }
                    break;
                case e:
                    if (w[b][index] > w[m][index]) {
                        before_status = p[b][index];
                    } else {
                        before_status = p[m][index];
                    }
                    break;
                case m:
                    if (w[b][index] > w[m][index]) {
                        before_status = p[b][index];
                    } else {
                        before_status = p[m][index];
                    }
                    break;
                case s:
                    if (w[e][index] > w[s][index]) {
                        before_status = p[e][index];
                    } else {
                        before_status = p[s][index];
                    }
                    break;
            }
            res[index - 1] = before_status;
        }
        return res;
    }

    /**
     * 算法递归
     *
     * @param w   权重
     * @param p   路径
     * @param str 句子
     * @param len 句子长度
     */
    public static void cycle(double[][] w, int[][] p, String str, int len) {
        for (int i = 1; i < len; i++) {
            // 遍历可能的状态
            for (int j = 0; j < 4; j++) {
                w[j][i] = Double.NEGATIVE_INFINITY;
                p[j][i] = -1;
                //遍历前一个字可能的状态
                for (int k = 0; k < 4; k++) {
                    Double d = statistics.pMatrix[j].get(str.charAt(i));
                    double tmp = w[k][i - 1] + statistics.transformMatrix[k][j] + (d == null ? -100 : d);
                    // 找出最大的weight[j][i]值
                    if (tmp > w[j][i]) {
                        w[j][i] = tmp;
                        p[j][i] = k;
                    }
                }
            }
        }
    }

    /**
     * 初始化两个矩阵
     *
     * @param w 权重
     * @param p 路径
     * @param c 句子第一个字符
     * @param t 0
     */
    public static void initial(double[][] w, int[][] p, char c, int t) {
        for (int i = 0; i < status; i++) {
            Double d = statistics.pMatrix[i].get(c);
            w[i][t] = (d == null ? -100 : d);
            p[i][t] = 0;
        }
    }

    public static void devide_file(String input, String output) {
        String line = null;
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            FileOutputStream fos = new FileOutputStream(new File(output));
            while ((line = br.readLine()) != null) {
                String divide_line = viterbi(line) + "\r\n";
                fos.write(divide_line.getBytes());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}

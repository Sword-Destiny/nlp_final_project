package com.sworddestiny.nlp.train;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计
 */
public class Statistics implements Serializable {
    public double[] initialMatrix = new double[4]; // b,e,m,s

    public double[][] transformMatrix = new double[4][4];

    public HashMap<Character, Double> pMatrix[] = new HashMap[4];

    public Statistics() {
        for (int i = 0; i < 4; i++) {
            pMatrix[i] = new HashMap<>();
        }
    }

    public void statistics() {
        int b = 0, e = 1, m = 2, s = 3;
        long[] initialCount = new long[4]; // b,e,m,s
        long initialSum = 0;
        long[][] transformCount = new long[4][4];
        long[] transformSum = new long[4];
        HashMap<Character, Long> pCount[] = new HashMap[4];
        for (int i = 0; i < 4; i++) {
            pCount[i] = new HashMap<>();
        }
        long[] pSum = new long[4];
        File file = new File("train/train_data.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] separates = line.split("\\s+");
                for (int i = 1; i < separates.length; i++) {
                    String word = separates[i].split("/")[0];
                    if (i == 1) {
                        if (word.length() == 1) {
                            initialSum++;
                            initialCount[s]++;
                        } else {
                            initialSum++;
                            initialCount[b]++;
                        }
                    }
                    if (word.length() == 1) {
                        char c = word.charAt(0);
                        Long count = pCount[s].get(c);
                        if (count == null) {
                            pCount[s].put(c, 1L);
                        } else {
                            pCount[s].put(c, count + 1);
                        }
                        pSum[s]++;
                        if (i < separates.length - 1) {
                            String nextWord = separates[i + 1].split("/")[0];
                            transformSum[s]++;
                            if (nextWord.length() == 1) {
                                transformCount[s][s]++;
                            } else {
                                transformCount[s][b]++;
                            }
                        }
                    } else if (word.length() == 2) {
                        char c0 = word.charAt(0);
                        Long count = pCount[b].get(c0);
                        if (count == null) {
                            pCount[b].put(c0, 1L);
                        } else {
                            pCount[b].put(c0, count + 1);
                        }
                        pSum[b]++;
                        transformSum[b]++;
                        transformCount[b][e]++;

                        char c1 = word.charAt(1);
                        count = pCount[e].get(c1);
                        if (count == null) {
                            pCount[e].put(c1, 1L);
                        } else {
                            pCount[e].put(c1, count + 1);
                        }
                        pSum[e]++;
                        if (i < separates.length - 1) {
                            String nextWord = separates[i + 1].split("/")[0];
                            transformSum[e]++;
                            if (nextWord.length() == 1) {
                                transformCount[e][s]++;
                            } else {
                                transformCount[e][b]++;
                            }
                        }
                    } else {
                        int len = word.length();
                        char c0 = word.charAt(0);
                        Long count = pCount[b].get(c0);
                        if (count == null) {
                            pCount[b].put(c0, 1L);
                        } else {
                            pCount[b].put(c0, count + 1);
                        }
                        pSum[b]++;
                        transformSum[b]++;
                        transformCount[b][m]++;

                        for (int index = 1; index < len - 2; index++) {
                            char c = word.charAt(index);
                            count = pCount[m].get(c);
                            if (count == null) {
                                pCount[m].put(c, 1L);
                            } else {
                                pCount[m].put(c, count + 1);
                            }
                            pSum[m]++;
                            transformSum[m]++;
                            transformCount[m][m]++;
                        }

                        char c1 = word.charAt(len - 2);
                        count = pCount[m].get(c1);
                        if (count == null) {
                            pCount[m].put(c1, 1L);
                        } else {
                            pCount[m].put(c1, count + 1);
                        }
                        pSum[m]++;
                        transformSum[m]++;
                        transformCount[m][e]++;

                        char c2 = word.charAt(len - 1);
                        count = pCount[e].get(c2);
                        if (count == null) {
                            pCount[e].put(c2, 1L);
                        } else {
                            pCount[e].put(c2, count + 1);
                        }
                        pSum[e]++;
                        if (i < separates.length - 1) {
                            String nextWord = separates[i + 1].split("/")[0];
                            transformSum[e]++;
                            if (nextWord.length() == 1) {
                                transformCount[e][s]++;
                            } else {
                                transformCount[e][b]++;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 4; i++) {
                initialMatrix[i] = Math.log(((double) initialCount[i]) / initialSum);
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    transformMatrix[i][j] = Math.log(((double) transformCount[i][j]) / transformSum[i]);
                }
            }

            for (int i = 0; i < 4; i++) {
                for (Map.Entry<Character, Long> entry : pCount[i].entrySet()) {
                    pMatrix[i].put(entry.getKey(), Math.log(((double) entry.getValue()) / pSum[i]));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Statistics s = new Statistics();
        s.statistics();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("train/result.statistics")))) {
            oos.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

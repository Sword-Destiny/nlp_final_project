package com.sworddestiny.nlp;

import com.sworddestiny.nlp.test.CalculateCorrect;
import com.sworddestiny.nlp.test.Divide;
import com.sworddestiny.nlp.train.Cut;
import com.sworddestiny.nlp.train.Statistics;

public class Main {
    public static void main(String[] args){
        System.out.println("示例:");
        System.out.println(Divide.viterbi("你知道微软做出了哪些努力吗"));
        System.out.println(Divide.viterbi("人们越来越高寿，微软希望能帮助医生完成一些繁重的工作"));
        System.out.println(Divide.viterbi("银尘怎么样了依旧未知数"));
        System.out.println(Divide.viterbi("法国和克罗地亚是冠军"));
        System.out.println(Divide.viterbi("我们今天不看球，兄弟，别问我哦"));

        System.out.println("\n分割文件中......");
        Cut.main(null);
        System.out.println("\n分割完成");

        System.out.println("\n统计训练数据中......");
        Statistics.main(null);
        System.out.println("\n统计训练数据完成");

        System.out.println("\n对测试数据分词中......");
        Divide.main(null);
        System.out.println("\n测试数据分词完成");

        System.out.println("\n计算正确率......");
        CalculateCorrect.main(null);
        System.out.println("\n正确率计算完成");
    }
}

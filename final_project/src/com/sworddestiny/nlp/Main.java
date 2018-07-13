package com.sworddestiny.nlp;

import com.sworddestiny.nlp.test.CalculateCorrect;
import com.sworddestiny.nlp.test.Divide;
import com.sworddestiny.nlp.train.Cut;
import com.sworddestiny.nlp.train.Statistics;

public class Main {
    public static void main(String[] args){
        System.out.println("示例:");
        System.out.println(Divide.viterbi("我们毕业于中国科学院"));
        System.out.println(Divide.viterbi("我和你在一起"));
        System.out.println(Divide.viterbi("你让我很不爽"));
        System.out.println(Divide.viterbi("我们，,在一个自然语言处理小组"));
        System.out.println(Divide.viterbi("我们在一个自然语言处理小组"));

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

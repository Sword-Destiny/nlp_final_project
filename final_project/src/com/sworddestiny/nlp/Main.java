package com.sworddestiny.nlp;

public class Main {
    public static void main(String[] args){
        if(args.length>0) {
            String input_file = args[0];
            System.out.println(input_file);
        }else {
            Divide.main(null);
        }
    }
}

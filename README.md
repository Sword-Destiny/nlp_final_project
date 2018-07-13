# nlp_final_project
nlp final project

分词作业说明:

项目逻辑:

主程序为com.sworddestiny.nlp.Main
程序先将国家语委得到的5万句子data/origin.txt划分为三个文件,
 * 一个训练data/train.txt,
 * 一个测试data/test.txt,
 * 一个标准答案data/result.txt
 
 然后对训练文件data/train.txt进行统计,得到统计结果train.statistics
 
 然后实现维特比算法对测试文件进行分词,输入为data/test.txt,输出为data/output.txt
 
 最后对输出data/output.txt和标准答案data/result.txt进行对比,计算正确率
 
 成员工作分工:
 
 袁  浩 201728015059049 负责划分训练数据和维特比分词算法实现以及统计正确率,书写文档
 邹羽婷 2017E8018661072 负责搜集语料库
 卞娅靖 2017E8018661020 负责统计词频等部分,使用python写的代码,后来为了与我的代码融合,由我修改为java代码
 
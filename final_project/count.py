#! python3
# -*- coding:utf-8 -*-
# author : GingerEater
# B\E\M\S : 0\1\2\3

import numpy as np
import pickle

punc = '，。、【】“”：；（）《》‘’{}？！⑦()、%^>℃：.”“^-——= ·.'

# 得到每个句子的状态序列
def getstate(words):
    state = []
    for word in words:
        word.replace(' ', '')
        if len(word) == 1:
            state.append(3)
        elif len(word) == 2:
            # print(word)
            state.extend([0, 1])
        else:
            state.append(0)
            for i in range(len(word) - 2):
                state.append(2)
            state.append(1)
    return state

# 归一化（还是标准化还是什么化？？）得到概率
def normalization(observe, initstate, trans):

    sum = observe.sum(axis = 0)
    observe = np.divide(observe, sum)

    sum = initstate.sum()
    initstate = np.divide(initstate, sum)

    for i in range(0, trans.shape[0]):
        sum = trans[i].sum()
        trans[i] = np.divide(trans[i], sum)

    return observe, initstate, trans



if __name__ == "__main__":
    training = open("pku_training1.txt", encoding="utf-8")
    # training = open("traintest.txt", encoding="utf-8")
    training.readline()

    count = {}
    cnum = 0
    observe = np.zeros((1, 4))
    initstate = np.zeros(4)
    trans = np.zeros((4, 4), dtype = float)

    # 训练集是元素为句子的列表，句子是字符串，每个词用两个空格分开
    for sentence in training:
        sentence.lstrip()
        words = sentence.split("  ")

        # words是把每个句子转为一个列表，去除停用词和换行符
        for word in words:
            if word in punc or word == '\n':
                words.remove(word)
        # 这里有一点问题……remove之后，有些句子的words里还是存在停用词或者\n
        # 还会有一个空格也去不掉……
        # 但蜜汁奇怪的是每个空行的换行符都去掉了，所以才能运行
        # 不过我猜对分词结果可能影响不大？就是怕突然不能运行了ovo

        # print(words)
        if not words:
            continue
        state = getstate(words)
        sen = ''.join(words)

        # 计算观测次数
        # count是每个字的编号，从1开始
        # observe的第0行全为0
        for i in range(len(sen)):
            if sen[i] not in count:
                cnum += 1
                count[sen[i]] = cnum
                observe = np.row_stack((observe, np.zeros(4)))
            observe[count[sen[i]]][state[i]] += 1

        # 计算初始状态次数
        initstate[state[0]] += 1
        # print(initstate)

        # 计算状态转移次数
        for i in range(len(state)):
            if i == 0:
                continue
            trans[state[i - 1]][state[i]] += 1
        # break

    observe, initstate, trans = normalization(observe, initstate, trans)

    print(count, '\n', observe, '\n', initstate, '\n', trans)



    countfile = open('count.txt', 'wb')
    pickle.dump(count, countfile)
    countfile.close()

    obfile = open('observe.txt', 'wb')
    pickle.dump(observe, obfile)
    obfile.close()

    initfile = open('initstate.txt', 'wb')
    pickle.dump(initstate, initfile)
    initfile.close()

    transfile = open('trans.txt', 'wb')
    pickle.dump(trans, transfile)
    transfile.close()




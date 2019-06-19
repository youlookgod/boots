package com.training.arithmetic.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

/**
 * 开源的中文分词工具
 * george 2019/5/31 16:23
 */
public class HanlpWordAnalysis {
    public static void main(String[] args) {
        String text = "明天晚上8点半提醒我吃饭";
        List<Term> termList = HanLP.segment(text);
        for (Term term : termList) {
            System.out.println(term.nature);
            System.out.println(term.word);
            System.out.println(term.getFrequency());
        }
    }

}

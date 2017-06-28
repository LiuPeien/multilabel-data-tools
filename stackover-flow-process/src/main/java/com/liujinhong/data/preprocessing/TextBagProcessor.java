package com.liujinhong.data.preprocessing;

/**
 * Created by Liu Jinhong on 2016/9/30.
 */
public class TextBagProcessor {
    public static void main(String arg[]){
        String slectedQuery = "text/标签选择/SlectedQuery.csv";
        String newQuery = "text/构造词袋/NewQuery.csv";
        String allBags = "text/构造词袋/AllBag.csv";

        //把body和title部分合并
        WordBagConstructor.combineBodyTitle(slectedQuery, newQuery);
        //对body和title合并后的数据集构造词袋
        WordBagConstructor.constructor(newQuery, allBags);

        //根据词频初步进行特征筛选
        String filtedBags = "text/构造词袋/FiltedAllBag.csv";
        int wordfreq = 50, textfreq = 20;
        WordFilteProcessor.preFilter(allBags, filtedBags, wordfreq, textfreq);

        //根据选出的特征词对文档进行过滤
        String filterpath = "text/构造词袋/FilterWordText.csv";
        TextWordFilteProcessor.WordFilter(filtedBags, newQuery, filterpath);
    }
}

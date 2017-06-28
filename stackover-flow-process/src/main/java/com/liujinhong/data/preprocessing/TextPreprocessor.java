package com.liujinhong.data.preprocessing;

/**
 * Created by Liu Jinhong on 2016/9/20.
 * 对stackover flow数据进行预处理
 * 1. 分词
 * 2. 对标签进行统计
 * 3. 选择出现频率较高的标签
 */
public class TextPreprocessor {
    public static void main(String arg[]){

        //对文档集合进行分词处理
        String rawDataPath = "text/文本分词/QueryResults.csv";
        String segmentPath = "text/文本分词/Query.csv";
        TextSegmentProcessor.singleText(rawDataPath, segmentPath);

        //统计文档中标签的数目
        String allTags = "text/文本分词/AllTags.csv";
        TagsCountProcessor.selectTags(segmentPath, allTags, 1);


        //根据选中的标签对文档进行筛选
        String selectedTags = "text/标签选择/tags.csv";
        String tagFiltText = "text/标签选择/ SlectedQuery.csv";
        TextSelectProcessor.selectText(selectedTags, segmentPath, tagFiltText);
    }
}

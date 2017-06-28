package com.liujinhong.data.preprocessing;

import com.liujinhong.data.common.OperateCsvData;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;


import java.io.*;


/**
 * Created by Liu Jinhong on 2016/9/9.
 * 对csv文件中的文档进行分词，包括内容分词和标签切分
 */
public class TextSegmentProcessor {
    //字符串分词
    public static String displayToken(String str, StopAnalyzer analyzer){
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            //将一个字符串创建成Token流
            TokenStream stream  = analyzer.tokenStream("", new StringReader(str));
            stream = new PorterStemFilter(stream);
            stream = new StopFilter(stream, analyzer.getStopwordSet());
            stream = new LengthFilter(stream,1,15);
            //保存相应词汇
            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);

            stream.reset();
            while(stream.incrementToken()){
                stringBuffer.append(cta+" ");
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString().trim();
    }
    //文件分词
    public static void displayToken(File file,Analyzer analyzer){
        try{
            FileReader reader = new FileReader(file);
            TokenStream tokenStream = analyzer.tokenStream("", reader);
            tokenStream = new PorterStemFilter(tokenStream);

            tokenStream.reset();
            while (tokenStream.incrementToken())
            {
                CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
                System.out.println(termAttribute.toString());

            }
            tokenStream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static String tagSplit(String str){
        String[] tags = str.split("(><|<|>)");
        String result = "";
        for (int i = 0; i < tags.length; i++){
            if (!tags[i].equals("")){
                result = result + tags[i] + " ";
            }
        }
        return result;
    }


    public static void singleText(String inpath, String outpath){
        try{
            //加载停用词表
            FileReader stopWords = new FileReader("stopWords.txt");
            StopAnalyzer analyzer = new StopAnalyzer(stopWords);
            String[][] data = OperateCsvData.readerCsv(inpath);

            for (int i = 0; i < data.length; i++){
                data[i][1] = TextSegmentProcessor.displayToken(data[i][1], analyzer);
                data[i][2] = TextSegmentProcessor.displayToken(data[i][2], analyzer);
                data[i][3] = TextSegmentProcessor.tagSplit(data[i][3]);
            }

            OperateCsvData.writerCsv(outpath, data);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

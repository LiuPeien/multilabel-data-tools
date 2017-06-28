package com.liujinhong.data.preprocessing;

import com.liujinhong.data.common.OperateCsvData;

import java.util.*;

/**
 * Created by Liu Jinhong on 2016/9/10.
 * 根据初步筛选的特征对文档中的词进行过滤
 */
public class TextWordFilteProcessor {
    public static void WordFilter(String bagpath, String inpath, String outpath){
        try{
            String[][] allbag = OperateCsvData.readerCsv(bagpath);
            String[][] text = OperateCsvData.readerCsv(inpath);
            List<String[]> list = new ArrayList<String[]>();
            Set<String> features = new HashSet<String>();

            for (int i = 0; i < allbag.length; i++)
                features.add(allbag[i][0]);

            //对每一篇文档中的词进行过滤
            for (int i = 0; i < text.length; i++){
                String[] body = text[i][1].split(" ");
                StringBuilder newbody = new StringBuilder("");

                int count = 0;
                for (String s : body){
                    if (features.contains(s)) {
                        newbody.append(s+" ");
                        count ++;
                    }
                }

                //把太短的文档过滤掉
                if (count >= 50){
                    text[i][1] = newbody.toString();
                    list.add(text[i]);
                }
            }

            String[][] datas = new String[list.size()][];
            for (int i = 0; i < list.size(); i++) {
                datas[i] = list.get(i);
            }

            OperateCsvData.writerCsv(outpath, datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

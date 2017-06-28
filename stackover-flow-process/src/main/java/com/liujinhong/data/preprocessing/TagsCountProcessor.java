package com.liujinhong.data.preprocessing;

import com.liujinhong.data.common.OperateCsvData;

import java.util.*;

/**
 * Created by Liu Jinhong on 2016/9/10.
 * 筛选数据集中出现频次比较高的
 */
public class TagsCountProcessor {
    public static void selectTags(String docfile, String tagfile, int tagfreq){
        try{
            Map<String, Integer> tagmap = new HashMap<String, Integer>(0);
            String[][] data = OperateCsvData.readerCsv(docfile);

            for (int i = 0; i < data.length; i++){
                String tags[] = data[i][3].split(" ");
                for (String s : tags){
                    if (!s.equals("")){
                        if(tagmap.containsKey(s)){
                            int cur = tagmap.get(s);
                            tagmap.put(s, cur+1);
                        }
                        else{
                            tagmap.put(s, 1);
                        }
                    }
                }
            }

            Iterator<Map.Entry<String, Integer>> it = tagmap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, Integer> entry=it.next();
                int num = entry.getValue();
                if (num < tagfreq){
                    it.remove();
                }
            }

            OperateCsvData.writerCsv(tagfile,tagmap);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

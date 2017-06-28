package com.liujinhong.data.preprocessing;

import com.liujinhong.data.common.OperateCsvData;
import com.sun.deploy.util.StringUtils;

import java.util.*;

/**
 * Created by Liu Jinhong on 2016/9/10.
 * 筛选包含tag集合中的文本集合
 */
public class TextSelectProcessor {
    private static Set<String> tags;

    public static void loadTags(String tagspath){
        TextSelectProcessor.tags = new HashSet<String>(0);
        try{
            String[][] tag = OperateCsvData.readerCsv(tagspath);
            for (int i = 0; i < tag.length; i++){
                tags.add(tag[i][0]);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void selectText(String tagfile, String textfile, String filtedfile){
        try{
            TextSelectProcessor.loadTags(tagfile);
            String[][] data = OperateCsvData.readerCsv(textfile);
            List<String[]> list = new ArrayList<String[]>();

            for (int i = 0; i < data.length; i++){
                String tags[] = data[i][3].split(" ");
                Set<String> newtags = new HashSet<String>(Arrays.asList(tags));
                Iterator<String> it = newtags.iterator();
                while (it.hasNext()) {
                    String str = it.next();
                    if (!TextSelectProcessor.tags.contains(str)) {
                        it.remove();
                    }
                }
                if(newtags.size() > 0){
                    String s = StringUtils.join(newtags," ");
                    data[i][3] = s;
                    list.add(data[i]);
                }
            }

            String[][] datas = new String[list.size()][];
            for (int i = 0; i < list.size(); i++) {
                datas[i] = list.get(i);
            }

            OperateCsvData.writerCsv(filtedfile, datas);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

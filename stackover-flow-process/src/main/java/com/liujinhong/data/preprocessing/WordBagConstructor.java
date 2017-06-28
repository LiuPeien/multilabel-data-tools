package com.liujinhong.data.preprocessing;


import com.liujinhong.data.common.OperateCsvData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Liu Jinhong on 2016/9/10.
 * 构造词袋
 */
public class WordBagConstructor {
    private  static Map<String,Integer> wordfreq;
    private  static Map<String,Integer> wordtextfreq;

    //合并body和title
    public static void combineBodyTitle(String inpath, String outpath){
        try{
            String[][] data = OperateCsvData.readerCsv(inpath);
            String[][] newdata = new String[data.length][3];
            for (int i = 0; i < data.length; i++){
                String text = data[i][1] + " " + data[i][2];
                newdata[i][0] = data[i][0];
                newdata[i][1] = text;
                newdata[i][2] = data[i][3];
            }
            OperateCsvData.writerCsv(outpath, newdata);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void constructor(String inpath, String outpath){
        wordfreq = new HashMap<String,Integer>();
        wordtextfreq = new HashMap<String,Integer>();
        try{
            String[][] data = OperateCsvData.readerCsv(inpath);
            for (int i = 0; i < data.length; i++){
                Set<String> atextbag = new HashSet<String>(0);
                String text = data[i][1];
                String[] sptext = text.split(" ");
                for (int j = 0; j < sptext.length; j++){
                    if(!sptext[j].equals("")){
                        if (wordfreq.containsKey(sptext[j])){
                            int cur = wordfreq.get(sptext[j]);
                            wordfreq.put(sptext[j], cur+1);
                        }
                        else{
                            wordfreq.put(sptext[j], 1);
                        }
                        if (atextbag.add(sptext[j]) == true){
                            if (wordtextfreq.containsKey(sptext[j])){
                                int cur = wordtextfreq.get(sptext[j]);
                                wordtextfreq.put(sptext[j], cur+1);
                            }
                            else{
                                wordtextfreq.put(sptext[j], 1);
                            }
                        }
                    }
                }
            }
            String[][] allwordbag = new String[wordfreq.size()][3];
            int k = 0;
            for (String s : wordfreq.keySet()){
                allwordbag[k][0] = s;
                allwordbag[k][1] = wordfreq.get(s).toString();
                allwordbag[k][2] = wordtextfreq.get(s).toString();
                k++;
            }
            OperateCsvData.writerCsv(outpath, allwordbag);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

package com.liujinhong.data.preprocessing;

import com.liujinhong.data.common.OperateCsvData;

import java.util.*;

/**
 * Created by Liu Jinhong on 2016/9/10.
 * 根据词频对特征词进行初步筛选
 */
public class WordFilteProcessor {
    public static void preFilter(String infile, String outfile, int wordfreq, int textfreq){
        try{
            String[][] data = OperateCsvData.readerCsv(infile);
            List<String[]> list = new ArrayList<String[]>();
            for (int i = 0; i < data.length; i++){
                if (Integer.valueOf(data[i][1]) > wordfreq && Integer.valueOf(data[i][2]) > textfreq){
                    list.add(data[i]);
                }
            }
            String[][] datas = new String[list.size()][];
            for (int i = 0; i < list.size(); i++) {
                datas[i] = list.get(i);
            }
            OperateCsvData.writerCsv(outfile,datas);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

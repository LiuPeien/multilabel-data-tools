package com.liujinhong.data.common;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Liu Jinhong on 2016/9/10.
 */
public class OperateCsvData {
    public static String[][] readerCsv(String csvFilePath) throws Exception {
        CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("utf-8"));

        List<String[]> list = new ArrayList<String[]>();
        while (reader.readRecord()){
            String item[] = reader.getValues();
            list.add(item);
        }

        String[][] datas = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            datas[i] = list.get(i);
        }
        return datas;
    }

    public static Set<String> readerCsv(String csvFilePath, int num) throws Exception {
        CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("utf-8"));

        Set<String> dataset = new HashSet<String>(0);
        while (reader.readRecord()){
            String item[] = reader.getValues();
            dataset.add(item[0]);
        }
        return dataset;
    }

    public static void writerCsv(String csvFilePath, String[][] data) {

        CsvWriter writer = null;
        try {
            writer = new CsvWriter(csvFilePath, ',', Charset.forName("utf-8"));

            for (int i = 0; i < data.length; i++) {
                writer.writeRecord(data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    public static void writerCsv(String csvFilePath, Map<String,Integer> data){
        CsvWriter writer = null;
        try {
            writer = new CsvWriter(csvFilePath, ',', Charset.forName("utf-8"));// shift_jis日语字体,utf-8

            for (String s : data.keySet()) {
                String[] line = new String[2];
                line[0] = s;
                line[1] = data.get(s).toString();
                writer.writeRecord(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}

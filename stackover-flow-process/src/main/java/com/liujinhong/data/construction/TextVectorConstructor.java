package com.liujinhong.data.construction;

import com.liujinhong.data.common.OperateCsvData;

import java.util.*;

/**
 * Created by Liu Jinhong on 2016/9/10.
 */
public class TextVectorConstructor {
    /**
     * 构造TFIDF向量
     * @param bagpath
     * @param textpath
     * @param tagpath
     * @param outpath
     */
    public static void TfIdfProcessor(String bagpath, String textpath, String tagpath, String outpath){
        try{
            String[][] allbag = OperateCsvData.readerCsv(bagpath);
            String[][] text = OperateCsvData.readerCsv(textpath);
            String[][] tags = OperateCsvData.readerCsv(tagpath);

            int featureNum = allbag.length;
            int tagNum = tags.length;
            String[][] textvector = new String[text.length][featureNum+tagNum];
            Map<String, Float> wordTextFreq = new HashMap<String, Float>();

            for(int i = 0; i < allbag.length; i++){
                float freq = Float.valueOf(allbag[i][2]);
                wordTextFreq.put(allbag[i][0], freq);
            }

            for (int i = 0; i < text.length; i++){
                String[] words = text[i][1].split(" ");
                String[] tag = text[i][2].split(" ");
                Map<String, Float> allset = new HashMap<String, Float>();
                Set<String> tagset = new HashSet<String>(Arrays.asList(tag));

                for (int j = 0; j < words.length; j++){
                    if (allset.containsKey(words[j])){
                        float cur = allset.get(words[j]);
                        allset.put(words[j], cur+1);
                    }
                    else{
                        allset.put(words[j], (float)1);
                    }
                }

                for (int n = 0; n < allbag.length; n++){
                    String s = allbag[n][0];
                    if (allset.containsKey(s)){
                        float tf = allset.get(s) / words.length;
                        float idf = (float) Math.log(text.length / wordTextFreq.get(s));
                        float tfidf = tf * idf;
                        String result = String.format("%.6f", tfidf);
                        textvector[i][n] = result;
                    }
                    else{
                        textvector[i][n] = "0.0";
                    }
                }

                for (int m = 0; m < tags.length; m++){
                    if (tagset.contains(tags[m][0])){
                        textvector[i][m+featureNum] = "1";
                    }
                    else{
                        textvector[i][m+featureNum] = "0";
                    }
                }
            }

            OperateCsvData.writerCsv(outpath, textvector);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param tagpath
     * @param bagpath
     * @param textpath
     * @param outpath1
     * @param outpath2
     */
    public static void WordNumProcessor(String tagpath,String bagpath, String textpath, String outpath1, String outpath2){
        try{
            String[][] allbag = OperateCsvData.readerCsv(bagpath);
            String[][] text = OperateCsvData.readerCsv(textpath);
            String[][] tags = OperateCsvData.readerCsv(tagpath);

            int featureNum = allbag.length;
            int tagNum = tags.length;
            String[][] textvector = new String[text.length][featureNum];
            String[][] tagvector = new String[text.length][tagNum];

            for (int i = 0; i < text.length; i++){
                String[] words = text[i][1].split(" ");
                String[] tag = text[i][2].split(" ");
                Map<String, Integer> allset = new HashMap<String, Integer>();
                Set<String> tagset = new HashSet<String>(Arrays.asList(tag));

                for (int j = 0; j < words.length; j++){
                    if (allset.containsKey(words[j])){
                        int cur = allset.get(words[j]);
                        allset.put(words[j], cur+1);
                    }
                    else{
                        allset.put(words[j], 1);
                    }
                }

                for (int n = 0; n < allbag.length; n++){
                    if (allset.containsKey(allbag[n][0])){
                        textvector[i][n] = allset.get(allbag[n][0]).toString();
                    }
                    else{
                        textvector[i][n] = "0";
                    }
                }

                for (int m = 0; m < tags.length; m++){
                    if (tagset.contains(tags[m][0])){
                        tagvector[i][m] = "1";
                    }
                    else{
                        tagvector[i][m] = "0";
                    }
                }
            }

            OperateCsvData.writerCsv(outpath1, textvector);
            OperateCsvData.writerCsv(outpath2, tagvector);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param tagpath
     * @param bagpath
     * @param textpath
     * @param outpath
     */
    public static void ZeroOneProcessor(String tagpath,String bagpath, String textpath, String outpath){
        try{
            String[][] allbag = OperateCsvData.readerCsv(bagpath);
            String[][] text = OperateCsvData.readerCsv(textpath);
            String[][] tags = OperateCsvData.readerCsv(tagpath);

            String[][] textvector = new String[text.length][2];

            for (int i = 0; i < text.length; i++){
                String[] words = text[i][1].split(" ");
                String[] tag = text[i][2].split(" ");
                Set<String> allset = new HashSet<String>(Arrays.asList(words));
                Set<String> tagset = new HashSet<String>(Arrays.asList(tag));
                StringBuffer vector = new StringBuffer("");
                StringBuffer tagvector = new StringBuffer("");

                for (int n = 0; n < allbag.length; n++){
                    if (allset.contains(allbag[n][0])){
                        vector.append("1 ");
                    }
                    else{
                        vector.append("0 ");
                    }
                }
                for (int m = 0; m < tags.length; m++){
                    if (tagset.contains(tags[m][0])){
                        tagvector.append("1 ");
                    }
                    else{
                        tagvector.append("0 ");
                    }
                }

                textvector[i][0] = vector.toString();
                textvector[i][1] = tagvector.toString();
            }

            OperateCsvData.writerCsv(outpath, textvector);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 对数据的特征和标签进行划分
     * @param tfidfpath
     * @param datapath
     * @param targetpath
     * @param featurenum
     * @param tagnum
     */
    public static void DataTargetSplit(String tfidfpath, String datapath, String targetpath, int featurenum, int tagnum){
        try{
            String[][] tfidf = OperateCsvData.readerCsv(tfidfpath);
            String[][] data = new String[tfidf.length][featurenum];
            String[][] target = new String[tfidf.length][tagnum];

            for (int i = 0; i < tfidf.length; i++){
                for (int j = 0; j < featurenum; j++){
                    data[i][j] = tfidf[i][j];
                }
            }

            for (int i = 0; i < tfidf.length; i++){
                for (int j = 0; j < tagnum; j++){
                    target[i][j] = tfidf[i][j+featurenum];
                }
            }
            OperateCsvData.writerCsv(datapath, data);
            OperateCsvData.writerCsv(targetpath, target);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String tagpath = "text\\tags.csv";
        String bagpath = "text\\FiltedAllBag.csv";
        String inpath = "text\\FilterWordText.csv";
        String outpath = "text\\TextWordVector.csv";
        TfIdfProcessor(tagpath,bagpath,inpath,outpath);
    }
}

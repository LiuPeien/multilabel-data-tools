package com.liujinhong.data.common;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Liu Jinhong on 2016/10/9.
 */
public class TransformDataToLibsvm {
    public static void LibsvmProcessor(String infile, String outfile, int labelNum){
        try{
            File file = new File(outfile);
            FileOutputStream out=new FileOutputStream(file,false);

            String[][] vector = OperateCsvData.readerCsv(infile);

            int docNum = vector.length;
            int featureNum = vector[0].length - labelNum;

            for (int i = 0; i < docNum; i++){
                StringBuilder br = new StringBuilder();
                for (int j = 0; j < labelNum; j++){
                    if (vector[i][j+featureNum].equals("1")){
                        br.append(j+",");
                    }
                }
                br.deleteCharAt(br.length()-1);

                for (int j = 0; j < featureNum; j++){
                    if (!vector[i][j].equals("0.0")){
                        br.append(" "+j+":"+vector[i][j]);
                    }
                }
                out.write((br.toString()+'\n').getBytes("UTF-8"));
            }
            out.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String arg[]){
        String trainTfidfFile = "text\\抽样数据\\SampleText.csv";
        String trainLibsvmFile = "text\\SampleTextLibsvm.svm";
        LibsvmProcessor(trainTfidfFile, trainLibsvmFile, 10);
    }
}

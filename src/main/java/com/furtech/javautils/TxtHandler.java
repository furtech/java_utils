package com.furtech.javautils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @des 输入一段文本，读取文本中的数据，并把它组装成我们需要的格式。简单又方便
 *
 * @author 719383495@qq.com | 719383495qq@gmail.com | 有问题可以邮箱或者github联系我
 * @date 2019/8/3 11:56
 */
public class TxtHandler {

    private static final Logger logger = LoggerFactory.getLogger(TxtHandler.class);

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Desktop\\demo.txt";
        readText(filePath);
    }

    public static void readText(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            try {
                String txt = " ";
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(is);
                while ((txt=br.readLine())!=null) {
                    String[] ss = txt.split(":");
                    System.out.println("insert into t_demo(name, id) values (" + ss[0] + "," + ss[1] + ")");
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}


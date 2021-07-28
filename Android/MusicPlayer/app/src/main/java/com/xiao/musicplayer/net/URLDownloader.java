package com.xiao.musicplayer.net;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLDownloader extends Thread {

    private String url;
    private String filename;
    @Override
    public void run() {
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            //设置连接超时时间
            connection.setConnectTimeout(10000);
            //请求方式
            connection.setRequestMethod("GET");
            //从网络连接读取东西，默认是true，可以不设置
            connection.setDoInput(true);
            if (connection.getResponseCode() != 404){
                InputStream in = connection.getInputStream();
                File myfile = new File(filename);
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(myfile));
                byte[] buff = new byte[1024];
                int read_size;
                while((read_size = in.read(buff))>0){
                    out.write(buff,0,read_size);
                }
                in.close();
                out.flush();
                out.close();
                connection.disconnect();
                System.out.println("下载成功 "+myfile.getName());
            }else {
                System.out.println("连接失败！请检查url是否有问题");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public URLDownloader(String url,String filename) {
        super();
        this.url = url;
        this.filename = filename;
    }
}

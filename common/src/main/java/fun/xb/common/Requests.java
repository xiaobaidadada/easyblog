package fun.xb.common;


import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Requests {
    public static Map get(String url) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        Map map=new HashMap();//返回的map
        try {
            // 创建远程url连接对象
            URL httpurl = new URL(url);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) httpurl.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();

            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }


            map.put("code",connection.getResponseCode());
            map.put("headers",connection.getHeaderFields());
            map.put("body",result);
            map.put("url",connection.getURL());
            map.put("method",connection.getRequestMethod());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return map;
    }

    /**
     * 默认使用表单提交的方式
     * @param url
     * @param strData 是&分隔符形式的表单
     * @param header 请求头可以为null
     * @return
     */
    public static Map post(String url, String strData, Map<String,String > header) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        Map map=new HashMap();//返回的map
        try {
            URL httpurl = new URL(url);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) httpurl.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            setHeader(connection,header);//自定义设置请求头


            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(strData.getBytes());
            // 通过连接对象获取一个输入流，向远程读取


                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
                map.put("code",connection.getResponseCode());
                map.put("headers",connection.getHeaderFields());
                map.put("body",result);
                map.put("url",connection.getURL());
                map.put("method",connection.getRequestMethod());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return map;
    }

    /**
     * 默认以json的形式进行请求，默认是applcation/json
     * @param url
     * @param jsonData 是一个用Map 和 List 形成的json
     * @param header  可以为null
     * @return
     */
    public static Map post(String url, Map<String,Object> jsonData, Map<String,String > header) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        Map map=new HashMap();//返回的map

        try {
            URL httpurl = new URL(url);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) httpurl.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/json");
            setHeader(connection,header);//自定义设置请求头

            String strData = null;
            if(connection.getRequestProperty("Content-Type").indexOf("application/x-www-form-urlencoded")!=-1){
                for (String k:
                     jsonData.keySet()) {
                    strData=strData+k+"="+jsonData.get(k)+"&";
                }
                
            } else if (connection.getRequestProperty("Content-Type").indexOf("application/json")!=-1) {
                strData= JSON.toJSONString(jsonData);
            }
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(strData.getBytes());
            // 通过连接对象获取一个输入流，向远程读取


                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
                map.put("code",connection.getResponseCode());
                map.put("headers",connection.getHeaderFields());
                map.put("body",result);
                map.put("url",connection.getURL());
                map.put("method",connection.getRequestMethod());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return map;
    }
    static void setHeader(HttpURLConnection connection,Map<String,String > header){
        if(header!=null){
            header.forEach((String k,String v)->{
                connection.setRequestProperty(k,v);
            });
        }

    }



}

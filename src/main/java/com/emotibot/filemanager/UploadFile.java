package com.emotibot.filemanager;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by hyman on 2018/8/13.
 */

public class UploadFile extends HttpServlet{

    public static final String C_UPLOAD_APKS = "/Users/hyman/Downloads/";
    private static final long serialVersionUID = 1L;


    public UploadFile() {
        super();
    }
    protected List<FileItem>  parseRequest(HttpServletRequest req) throws Throwable {

        FileUpload fu = new FileUpload(new DiskFileItemFactory());

        List<FileItem> l = fu.parseRequest(req);
        return l;
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

    HttpServletRequest context;
    HttpServletResponse response;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        context  = request;
        request.setCharacterEncoding("utf-8");
        try {
            this.response = response;
            saveFiles(parseRequest(request));
        } catch (Throwable e) {
            throw new IOException(e);
        }
    }

    protected boolean validateRequest(List<FileItem> l) throws Throwable {
        int uploadCount = 0;
        for (FileItem fi : l) {
            if (!fi.isFormField()) {
                uploadCount++;
            }
        }

        if (uploadCount != 1) {
            throw new Exception("upload object must be equal 1");
        }

        return true;
    }


    private void saveFiles(List<FileItem> l) throws Throwable {
        if (validateRequest(l)) {
            String objectName = null;

            Map<String, String> description = new HashMap<String, String>();
            FileItem file = null;
            for (FileItem fi : l) {
                String key = fi.getFieldName();
                if (fi.isFormField()) {
                    description.put(key, fi.getString("utf-8"));
                } else {
                    file = fi;
                }
            }

            objectName = string2MD5(String.valueOf(System.currentTimeMillis()))
                    + "_" + file.getName();


            File dir = new File(C_UPLOAD_APKS);
            if (file != null) {
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                if (dir.exists()) {
                    file.write(new File(dir.getAbsoluteFile() + "/" + objectName));
                }

                String url =

                        String.format("%s://%s:%d%s/download?obj=%s",
                                context.getScheme()
                                , context.getServerName()
                                , context.getServerPort()
                                , context.getContextPath()
                                , objectName);

                String html = String.format(
                        "<head><meta charset='gbk'><title>upload</title></head>" +
                                "<a href='%s'>%s</a>", url, url);

                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().print(html);
            }
        }
    }



    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toUpperCase();
    }


}

package com.emotibot.filemanager;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by hyman on 2018/8/13.
 */
public class DownloadFile extends HttpServlet {


    private static final long serialVersionUID = 1L;

    public DownloadFile() {
        super();
    }

    public void download(HttpServletRequest request, HttpServletResponse response, String objectName) throws IOException {

        if (objectName != null) {
            FileInputStream f = new FileInputStream(UploadFile.C_UPLOAD_APKS + "/" + objectName);

            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + objectName + "\"");
            response.setContentLength(f.available());

            ServletOutputStream os = response.getOutputStream();
            byte[] buff = new byte[Short.MAX_VALUE];
            while (f.available() > 0) {
                int realSize = f.read(buff , 0 , Short.MAX_VALUE);
                os.write(buff, 0, realSize);
            }

            f.close();
            os.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        //String objName =  request.getParameter("obj");
        String objName = new String(request.getParameter("obj").getBytes("iso-8859-1"), "utf-8");

        download(request, response, objName);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

}

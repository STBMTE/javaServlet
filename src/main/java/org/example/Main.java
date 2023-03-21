package org.example;

import org.example.service.FileService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(urlPatterns = "/")
public class Main extends HttpServlet {
    //private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    @Override
    public void init(ServletConfig var1) throws ServletException{
        super.init(var1);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = req.getParameter("path");
        if (path == null) {
            path = "C:\\AMD";
        }
        path = path.replaceAll("%20", " ");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        if (file.isDirectory()) {
            showFiles(req, file);

            req.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            req.setAttribute("path", path);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("explore.jsp");
            requestDispatcher.forward(req, resp);
        }
        else {
            downloadFile(resp, file);
        }
    }

    private void downloadFile(HttpServletResponse resp, File file) throws IOException {
        resp.setContentType("text/html");
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }

    private void showFiles(HttpServletRequest req, File file) {
        File[] files = file.listFiles();
        if (files != null) {
            req.setAttribute("files", getFiles(files));
            req.setAttribute("directories", getDirectories(files));
        }
    }
    private List<FileService> getFiles(File[] files){
        return Arrays.stream(files).filter(File::isFile).map(x -> new FileService(x,x.length())).collect(Collectors.toList());
    }
    private List<FileService> getDirectories(File[] files){
        List<FileService> result = new ArrayList<>();
        for (File file: files) {
            if (file.isDirectory()) {
                FileService fileService = new FileService(file, folderSize(file));
                result.add(fileService);
            }
        }
        return result;
    }
    private static long folderSize(File directory) {
        long length = 0;
        File[] files = directory.listFiles();
        if(files != null){
            for (File file : files) {
                if (file.isFile())
                    length += file.length();
                else
                    length += folderSize(file);
            }
        }
        return length;
    }
}
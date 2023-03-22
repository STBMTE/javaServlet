package org.example;

import org.example.model.FileModel;
import org.example.model.UserModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/")
public class Main extends HttpServlet {
    @Override
    public void init(ServletConfig var1) throws ServletException {
        super.init(var1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserModel user = localdatabase.userRepository.getUserFromCookie(req.getCookies());
        if (user != null) {
            String path = req.getParameter("path");
            if (path == null) {
                path = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), user.getLogin())
                        .getCanonicalPath();
            } else {
                try {
                    String path1 = new File(path).getCanonicalPath();
                    String path2 = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getCanonicalPath(),
                            user.getLogin()).getCanonicalPath();
                    if (!path1.startsWith(path2)) {
                        path = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), user.getLogin())
                                .getCanonicalPath();
                    }
                } catch (Exception ex) {
                    path = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), user.getLogin())
                            .getCanonicalPath();
                }
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
            } else {
                downloadFile(resp, file);
            }
        } else {
            resp.sendRedirect("./login");
        }
    }

    private void downloadFile(HttpServletResponse resp, File file) throws IOException {
        resp.setContentType("text/html");
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
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

    private List<FileModel> getFiles(File[] files) {
        return Arrays.stream(files).filter(File::isFile).map(x -> new FileModel(x, x.length())).collect(Collectors.toList());
    }

    private List<FileModel> getDirectories(File[] files) {
        return Arrays.stream(files).filter(File::isDirectory).map(x -> new FileModel(x, 0)).collect(Collectors.toList());
    }
}
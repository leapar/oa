package com.purvar.ito.oa.portal.controllers;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.FileTypesHelper;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.portal.security.PortalSessionProvider;
import com.haulmont.cuba.security.entity.User;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class PortalController {
    private final Logger log = LoggerFactory.getLogger(PortalController.class);

    @Inject
    protected DataService dataService;

    @Inject
    protected FileLoader fileLoader;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        if (PortalSessionProvider.getUserSession().isAuthenticated()) {
            LoadContext l = new LoadContext(User.class);
            l.setQueryString("select u from sec$User u");
            model.addAttribute("users", dataService.loadList(l));
        }
        return "index";
    }

    public static String getUUID(String uuid) {
        //String s = UUID.randomUUID().toString();
        // 去掉"-"符号
        return uuid.substring(0, 8) +"-"+ uuid.substring(8, 12) +"-"+ uuid.substring(12, 16) +"-"+ uuid.substring(16, 20) +"-"+ uuid.substring(20);
    }

    protected String getContentType(FileDescriptor fd) {
        if (StringUtils.isEmpty(fd.getExtension())) {
            return FileTypesHelper.DEFAULT_MIME_TYPE;
        }

        return FileTypesHelper.getMIMEType("." + fd.getExtension().toLowerCase());
    }

    @ResponseBody
    @RequestMapping(value = "/files/{fileId}", method = RequestMethod.GET)
    public byte[] getFile2(@PathVariable String fileId, Model model) throws IOException {
        UUID uuid = UUID.fromString(getUUID(fileId));
        FileDescriptor fd = dataService.load(LoadContext.create(FileDescriptor.class).setId(uuid));
        if (fd == null) {
            //log.warn("Unable to find file with id {}", fileId);
            //error(response);
            return null;
        }


        try (InputStream is = fileLoader.openStream(fd)) {
            return IOUtils.toByteArray(is);

        } catch (FileStorageException e) {
            log.error("Unable to load file from middleware", e);
            //error(response);
        }

        return null;
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ModelAndView getFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileId = request.getParameter("f");
        UUID uuid = UUID.fromString(getUUID(fileId));
        FileDescriptor fd = dataService.load(LoadContext.create(FileDescriptor.class).setId(uuid));
        if (fd == null) {
            log.warn("Unable to find file with id {}", fileId);
            error(response);
            return null;
        }


        String fileName;
        try {
            fileName = URLEncoder.encode(fd.getName(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
            error(response);
            return null;
        }

        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0);
        response.setHeader(HttpHeaders.CONTENT_TYPE, getContentType(fd));
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");

        boolean attach = Boolean.valueOf(request.getParameter("a"));
        response.setHeader("Content-Disposition", (attach ? "attachment" : "inline")
                + "; filename=" + fileName);

        downloadFromMiddlewareAndWriteResponse(fd, response);

        return null;
    }

    protected void downloadFromMiddlewareAndWriteResponse(FileDescriptor fd, HttpServletResponse response) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        try (InputStream is = fileLoader.openStream(fd)) {
            IOUtils.copy(is, os);
            os.flush();
        } catch (FileStorageException e) {
            log.error("Unable to load file from middleware", e);
            error(response);
        }
    }

    protected void error(HttpServletResponse response) throws IOException {
        if (!response.isCommitted())
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}

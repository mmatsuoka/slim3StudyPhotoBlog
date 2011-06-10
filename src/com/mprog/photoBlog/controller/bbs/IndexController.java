package com.mprog.photoBlog.controller.bbs;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.service.BlogService;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {

        UserService us = UserServiceFactory.getUserService();
        User user = us.getCurrentUser();
        sessionScope("authUser", user);

        BlogService service = new BlogService();
        List<Head> headList = service.getAll();
        requestScope("headList", headList);
        return forward("index.jsp");
    }
}

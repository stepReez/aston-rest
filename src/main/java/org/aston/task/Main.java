package org.aston.task;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.aston.task.servlet.UserServlet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {
    private static final int PORT = 8080;

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        tomcat.getConnector().setPort(PORT);

        Context context = tomcat.addContext("", null);

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setServletContext(context.getServletContext());
        applicationContext.scan("org.aston.task");
        applicationContext.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        Wrapper wrapper = Tomcat.addServlet(context, "dispatcher", dispatcherServlet);
        wrapper.addMapping("/");
        wrapper.setLoadOnStartup(1);

        tomcat.start();
    }
}
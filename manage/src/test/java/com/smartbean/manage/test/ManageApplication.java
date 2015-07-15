package com.smartbean.manage.test;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Created by kkk .
 */
public class ManageApplication {
    public static void main(String[] args) {
        Server jettyServer = new Server();
        SocketConnector connector = new SocketConnector();
        connector.setHeaderBufferSize(10240);
        connector.setPort(8080);
        Connector[] connectors = {connector};
        jettyServer.setConnectors(connectors);
        WebAppContext wah = new WebAppContext();
        wah.setContextPath("/yidu/manage");
        wah.setWar("src/main/webapp");
        jettyServer.setHandler(wah);
        try {
            jettyServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

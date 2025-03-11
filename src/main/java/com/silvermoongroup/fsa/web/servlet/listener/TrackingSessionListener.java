package com.silvermoongroup.fsa.web.servlet.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class TrackingSessionListener implements HttpSessionListener {

    public final static String NEW_SESSION_FLAG = "NEW_SESSION_FLAG";
    public final static String BOOLEAN_TRUE = "TRUE";
    public final static String BOOLEAN_FALSE = "FALSE";

    private static Logger log = LoggerFactory.getLogger(TrackingSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent pHttpSessionEvent) {
        TrackingSessionListener.log.debug("Session is being created!");
        pHttpSessionEvent.getSession().setAttribute(
                TrackingSessionListener.NEW_SESSION_FLAG,
                TrackingSessionListener.BOOLEAN_TRUE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent pHttpSessionEvent) {

    }
}

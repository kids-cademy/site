package com.kidscademy;

import js.annotation.Public;
import js.annotation.Service;

@Service
@Public
public interface HubService {
    void recordAuditEvent(String packageName, String macAddress, String event, String parameter1, String parameter2);

    void dumpStackTrace(String packageName, String macAddress, String stackTrace);

    /**
     * 
     * @param packageName
     * @param macAddress
     * @param projectName
     * @param vote +1 or -1; maybe allows for weight, e.g. +10 or -10?
     */
    void voteProject(String packageName, String macAddress, String projectName, int vote);
}

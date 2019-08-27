package com.kidscademy.tool;

public abstract class Task {
    abstract protected void exec() throws Exception;

    public void run() {
	try {
	    exec();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}

package org.jbltd.update;

public class UpdateProcess implements Runnable {

    private String appName, jarUrl;

    
    public UpdateProcess(String appName, String jarUrl) {

	this.appName = appName;
	this.jarUrl = jarUrl;

	run();

    }

    public void run() {

	try {

	    // automatically restart the app - OutdatedVersion
            final ProcessBuilder _processBuilder = new ProcessBuilder("py","UPDATE.py", jarUrl, appName+".jar");

            // continue using the same terminal session
            _processBuilder.inheritIO();
            _processBuilder.start();
            
            System.exit(0);

	} catch (Exception e) {

	}

    }

}

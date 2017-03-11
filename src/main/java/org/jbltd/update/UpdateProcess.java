package org.jbltd.update;

public class UpdateProcess implements Runnable {

    private UpdateCheck upcateCheck;
    private String appName, jarUrl;

    
    public UpdateProcess(UpdateCheck prior, String appName, String jarUrl) {

	this.upcateCheck = prior;
	this.appName = appName;
	this.jarUrl = jarUrl;

	run();

    }

    public void run() {

	try {

	   
            final ProcessBuilder _processBuilder = new ProcessBuilder("py","UPDATE.py", jarUrl, appName+".jar");

            
            _processBuilder.inheritIO();
            _processBuilder.start();
            
            upcateCheck.finished = true;
            
            System.exit(0);

	} catch (Exception e) {

	}

    }

}

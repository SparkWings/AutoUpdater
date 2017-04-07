package org.jbltd.update;

public class UpdateProcess implements Runnable {

    private UpdateCheck updateCheck;
    private String appName, jarUrl;

    public UpdateProcess(UpdateCheck prior, String appName, String jarUrl) {

	this.updateCheck = prior;
	this.appName = appName;
	this.jarUrl = jarUrl;

    }

    public void run() {

	try {

	    final ProcessBuilder _processBuilder = new ProcessBuilder("py", "UPDATE.py", jarUrl, appName);

	    _processBuilder.inheritIO();
	    _processBuilder.start();

	    updateCheck.finished = true;

	    System.exit(0);

	} catch (Exception e) {

	}

    }

}

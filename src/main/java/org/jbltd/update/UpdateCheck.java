package org.jbltd.update;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.util.Scanner;

public class UpdateCheck implements Runnable {

    private String url, jarUrl, appName;
    private double returnedVersion;
    private double currentVersion;

    public boolean outOfDate = false;
    public boolean finished = false;

    /** where this app was initially executed */
    protected static final String WORKING_DIRECTORY = Paths.get(".").toAbsolutePath().normalize().toString(); 

    public UpdateCheck(String appName, String updateUrl, String jarUrl, double currentVersion) {

	this.appName = appName;
	this.url = updateUrl;
	this.jarUrl = jarUrl;
	this.currentVersion = currentVersion;
    }

    public String getUpdateUrl() {
	return url;
    }

    public String getJarURL() {
	return jarUrl;
    }

    public String getApplicationName() {
	return appName;
    }

    public double getCurrentVersion() {
	return currentVersion;
    }

    public double getURLReturnedVersion() {
	return returnedVersion;
    }

    public boolean isOutOfDate() {
	return outOfDate;
    }

    public boolean isFinishedUpdating() {
	return finished;
    }

    public void run() {
	System.out.println("| UPDATE THREAD - Calling Update Manager");

	final File file = new File(WORKING_DIRECTORY, "UPDATE.py");

	if (!file.exists()) {

	    System.out.println("| UPDATE THREAD - Update script not found, downloading...");

	    try {
		URL website = new URL("https://s3.amazonaws.com/BUCKET_NAME/UPDATE.py");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

		fos.close();
		rbc.close();
		System.out.println("| UPDATE THREAD - Successfully downloaded update file");

	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}

	try {
	    URL url = new URL(getUpdateUrl());
	    Scanner s = new Scanner(url.openStream());

	    while (s.hasNext()) {
		String n = s.next();
		this.returnedVersion = Double.parseDouble(n);

		if (getURLReturnedVersion() > getCurrentVersion()) {

		    outOfDate = true;

		    System.out.println("| UPDATE THREAD - Update found. " + getApplicationName() + " version  "
			    + getURLReturnedVersion());

		    Thread t = new Thread(new UpdateProcess(this, getApplicationName(), getJarURL()));
		    t.start();

		} else {
		    System.out.println("| UPDATE THREAD - No update found.");
		}
	    }

	    s.close();
	} catch (Exception e) {
	    System.out.println("| UPDATE THREAD - Error fetching latest application data, aborting update check...");
	}
    }

}

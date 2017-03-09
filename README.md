# Auto Updater Version 1.0

<h2>An open source API to check for and update your Java Applications by passing arguments in for the latest version and the latest version's download site.</h2>

<br />
<br />
This application uses URL checking (Amazon AWS is recommended for file / version storage) to check for an update to a java application using version mapping.

If an update is detected, the API starts a python script to download and start the new jar file, while also deleting the old jar file.

The script then terminates itself, and you are looking at the newest update for your application!

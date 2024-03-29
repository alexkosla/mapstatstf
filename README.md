# mapstatstf

The way I ran this program locally was via IntelliJ, my IDE.
Load the project into IntelliJ (https://www.jetbrains.com/idea/download)

For the back-end create a new maven configuration matching this image
![image](https://user-images.githubusercontent.com/33711919/229597270-3073e524-7ba0-4ec8-b3c7-202152a9c21f.png)

For displaying the webpage, I created a new tomcat configuration matching this image. I had trouble doing this with any version of tomcat aside from Tomcat 10.0.2, which can be downloaded here (https://archive.apache.org/dist/tomcat/tomcat-10/v10.0.2/bin/) and installed with Dr. Ali's instructions. 
![image](https://user-images.githubusercontent.com/33711919/229598443-992ef046-414c-46f4-aff1-59f62b8548c7.png)

Run both of these using the arrow at the top right of the screen. Similar configurations are almost certainly possible to do in Eclipse.

To access the front-end, go to http://localhost:8999/. The back-end querying forms can be found on the sidebar, accessible from the hamburger menu at the top left.

![image](https://user-images.githubusercontent.com/33711919/229601692-aaae036d-be2a-4a69-934b-e807ee58a1ee.png)

# MySQL
You'll need MySQL and JDBC installed. I did this by using this installer (https://dev.mysql.com/downloads/installer/) and picking the standard installation. During the installation, set your username to 'root' and your password for root to be 'admin'. I also used the default port of 3306. If you use a different password for root or a different port, you'll need to go into the jdbc connection calls in the Stat and User services and change the password/port. To execute my create-db.sql script, I simply opened it up in MySQL Workbench (included in the standard installation) and ran it. 

# Standalone
You can also run the back-end from Tomcat standalone, provided it's Tomcat 10.0.2. Go to localhost:8087/manager/html (or whichever port you have tomcat set up at), enter your admin details, and you can upload the war file provided here by clicking the Choose File button, then Deploy beneath it.

![image](https://user-images.githubusercontent.com/33711919/229600504-35c3f04a-250c-4af7-907e-96d55c583cc3.png)

The back-end will then be accessible using the path of tomcat + the application path of /mapstatstf-q3-4. You can test it works by going to the following url (corrected for the right port).
![image](https://user-images.githubusercontent.com/33711919/229600933-8152ffa1-d855-4aa2-803c-1e24f8ef67e5.png)

Then for your front-end, you should be able to edit the javascript files to use the new url. I have given an example in new-user.js. You'll also need to set the right url in new-stats.js.

![image](https://user-images.githubusercontent.com/33711919/229601300-c2af9cef-d931-4e4e-b998-bc655a348e65.png)



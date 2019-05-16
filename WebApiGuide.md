# Web API Guide

This guide acts as the knowledgebase for the entire API backend.

## Connecting to the Database

Use a client that can view MySql Databases. I used DataGrip with the student license from the GitHub Student Pack.

Credentials:  
    Host - projectcc7database.c5o2ji0onlw3.us-east-1.rds.amazonaws.com.  
    Port - 3306  
    Database - (empty)  
    User - master  
    Password - nJkfHpwpL6LsJj4DZTMeZs7tFLCrXQ  

## Connecting to the PHP Server using SFTP

The PHP server that hosts the API is also hosted on an Amazon EC2 instance. This is essentially just a remote linux kernel with a static ip.

You can SFTP into the server with the following credentials. You have to download the .pem file which Amazon provided in order to connect. (This is a private key that lets you use SFTP & SSH). This is in the repo titled `projectcc7-keypair.pem`

Credentials:  
    Server - ec2-34-230-82-126.compute-1.amazonaws.com  
    Port - 22  
    Username - ec2-user  
    Password - (empty)  

You must also reference the .pem private key with your SFTP client in order to connect.

## PHP Folder Structure

The folder structure of the server directly reflects what files you have access to using a URL.

All the files for this API are located under the /var/www/ directory (from root directory):

/var/www/  
-–html/           Public and acts as the root for URL requests. Other folders are NOT accessible by an outside user.  
----api/          For organization purposes  
------users/      All the PHP files associated with managing the Users table.  
--inc/            This directory has the php files that you need to access but don't want publicly available to the user (I.e. passwords, etc.)  

If you want to add functionality to the Web API, these are the files you will want to modify.

Using this 

## Accessing the API

Use the following URL to connect to the remote api, giving it the correct path to the PHP file you want.  
URL: http://ec2-34-230-82-126.compute-1.amazonaws.com/api/..

For example, the following URL will call the users.php script on the server and return the contents of the Users table in JSON format:  
http://ec2-34-230-82-126.compute-1.amazonaws.com/api/users/users.php

If you access that URL using a GET, it will return the same thing. But, if you access it with a POST and some data, then it will instead create a user in the user table. (Or at least it will when it's implemented...).

# init
.. is a tool for working with file system templates. Directories and filenames can be set using values given to _init_ and so can values inside files.
It works recursively so you can have as many directory levels as you would like and you can change names and content at any level.

Init template config is located the users home directory
My path ( on a windows machine ) is C:\Users\Anders\.init

.      .  
.      └───templates  
.          ├───.gitignore  
.          │       .gitignore  
.          │  
.          └───example  
.              │   ¤¤_filename_¤¤  
.              │  
.              └───my-¤¤_directory-name_¤¤-directory  
.                  ├───subdir  
.                  │       test.txt  
.                  │  
.                  ├───subdir2  
.                  └───subdir3  
.                      │   dir3.txt  
.                      │  
.                      └───subdir-next-level  
.                              test.txt  

This directory structure contains two templates: ___example___ and ___.gitignore___ ( yes, I chose to call this template just like the file contained inside the template )

Where you to go to any directory on your machine and enter  
___init .gitignore___  

... this would simply try to copy the template version of .gitignore to your current directory, but it would fail since you did not set a value for the parameter ___other-dir___  
___Could not find value for parameter [ other-dir ]___  

init .gitignore other-dir=.vscode/  

... would create a local .gitignore file, with some defaults and one additional line ignoring the .vscode directory.
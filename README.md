# init
.. is a tool for working with file system templates. Directories and filenames can be set using values given to _init_ and so can values inside files.
It works recursively so you can have as many directory levels as you would like and you can change names and content at any level.

Init template config is located your users home directory
My path ( on a windos machine ) is C:\Users\Anders\.init

.
└───types
    ├───example
    │       .gitignore
    │
    └───gitter
        │   ¤¤_filename_¤¤
        │
        └───my-¤¤_directory-name_¤¤-directory
            ├───subdir
            │       test.txt
            │
            ├───subdir2
            └───subdir3
                │   dir3.txt
                │
                └───subdir-next-level
                        test.txt
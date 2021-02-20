I wanted to create a groovy script that would let me easily move around files within a particular directory

What you need to do is provide the list of files that you need moved. Now, they have to be in the particular directory you're in

Provide the file names as regular arguments, and make sure to not give absolute path

You need to provide a prefix through the '--target=' option

So along with the arguments you provide, provide an argument like: --target=prefix_

You can also provide a directory to put the new files in. Like so: --target=dir/prefix_
The name before the slash will be used as the name for the directory that will be made. If it exists, the one existing will be used.
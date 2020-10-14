##  Chapter 2 homework for the DO288 bootcamp

Fork this repo to your own account in **github.ibm.com**. Make the repository **private**. Working with an enterprise git repo requires 
providing a source-secret. This is an ssh-auth type secret which will be used by "oc new-app" to authenticate via ssh when retrieving 
the source code that is to be built. A prerequisite to this is using the ssh-keygen tool to generate the ssh key, and then adding it to 
your github account as a valid ssh key.

Here is an example of using the command (no passphrase was provided):

```
[friar@oc3027208274 ~]$ ssh-keygen
Generating public/private rsa key pair.
Enter file in which to save the key (/home/friar/.ssh/id_rsa): 
Created directory '/home/friar/.ssh'.
Enter passphrase (empty for no passphrase): 
Enter same passphrase again: 
Your identification has been saved in /home/friar/.ssh/id_rsa.
Your public key has been saved in /home/friar/.ssh/id_rsa.pub.
The key fingerprint is:
SHA256:u6l5v2+08IEAXH8XkHTK6May3vl+0d41B4inAvgC3cY friar@oc3027208274.ibm.com
The key's randomart image is:
+---[RSA 2048]----+
|     . ..  .ooo  |
|      o  . o.o . |
|   . + .  o.+..  |
|  . o E .o..o..  |
|   . o .S.++   o |
|    . . .=+ o ..+|
|     .  o. + o o=|
|       o.+ .+ . o|
|      oo+.=*+.   |
+----[SHA256]-----+
[friar@oc3027208274 ~]$
```
Now, there will be a file called .ssh/id_rsa.pub in your HOME directory. Copy its contents. Navigate to **github.ibm.com** in your browser. At
the top right, you will have a drop-down for your profile, and one of the entries in that drop-down will be "Settings". Select that. After that,
select "SSH and GPG keys". On the right side, there will be a button for "New SSH key" - click that. Fill in any title you like, but paste into
the "Key" field, the content of the id_rsa.pub file. Click "Add SSH key".

Now go back to the git repo you created in your account by forking this repo. Click on the "Clone or download" button and be sure to select the
"Clone with SSH" option. Copy the value provided and run the following command.

```
git clone <paste the git url value here>
```

If the git clone works without errors, then that is a good indication that your ssh key setup has worked.

## Task Milestone 1:
At this point you are ready to create the source secret. To do this, run the following command: "oc help create secret generic". Read the 
documentation. As arguments to the "oc create secret generic" command, you will provide the following items:

1.  The name of the secret: use "github-ibm-com"
2.  The location to your ssh private key
3.  The location to your ssh public key
4.  The "type" of the secret. Use the value **kubernetes.io/ssh-auth** for this

If you do this right, the build triggered by the following command will succeed. Otherwise, it will fail. You can check the build logs by running "oc logs -f simple-1-build".

```
oc new-app --name=less-simple git@github.ibm.com:<your github.ibm.com id>/day4-less-simple-stuff.git --source-secret=github-ibm-com
```
If the previous step worked, run the following commands as well.

```
oc expose svc less-simple
oc patch route less-simple --type=json -p '[{"op": "add", "path": "/spec/tls", "value": {"termination": "edge"}}, {"op": "add", "path": "/spec/port", "value": {"targetPort": "9080-tcp"}}]'
```

And then, `curl -k https://<hostname for route>/simple-stuff/less-simple/simon`. This should produce the string "/my-special-folder does not exist"

**Note that if you were not successful on milestone 1, you will not be able to proceed with this part of the excercise for full credit. To get partial 
credit, you can create your repo as a _public_ repo in _github.com_. If you do it this way, you will not need the _--source-secret_ flag.**

## Task Milestone 2

1. Create a config map called "my-dockerfile" with its contents being the Dockerfile of this project
2. Review the help for the "oc set volume" command. For the name of the deployment config, specify "dc/less-simple". For the mount path, specify "/my-special-folder".
Use "configmap" for the type, and specify the name of the config map as "my-dockerfile". Run the command to mount the contents of the configmap you have created
at /my-special-folder/Dockerfile
3. Create a generic secret called "my-special-secret" with a key of "MY_SECRET_VAR" and its value coming from a string literal "timbuctoo".
4. Review the help for the "oc set env" command. Use it to define the environment variable MY_SECRET_VAR in the "less-simple" deployment config.

At this point, rerunning the "curl" command will produce the Dockerfile contents as output. If you have gotten steps 3 and 4 correct as well, at the end of the
output of the Dockerfile, you will see "The secret is timbuctoo".


Expected Output:
1. Output from the curl command for both Milestone.

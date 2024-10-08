<ol>
<li> Download code into IDEA.</li> 
<li> Build the project.</li>
<li> Server
   <ol>
   <li> Open terminal/command window</li>
   <li> cd to {project_directory}/out/production/SocketDemo</li>
   <li> enter 'java SocketDemo' to see usage message</li>
   <li> enter 'java SocketDemo server 4466' to start server on port 4466</li>
   <li> you should see "Starting SocketDemo with arguments [server, 4466]"</li>
   <li> leave window open</li>
   </ol>
   </li>
<li> Client
   <ol>
   <li> Open terminal/command window</li>
   <li> cd to {project_directory}/out/production/SocketDemo</li>
   <li> enter 'java SocketDemo' to see usage message</li>
   <li> enter 'java SocketDemo client localhost 4466' to start client.</li>
   <li> you should see
     
        Starting SocketDemo with arguments [client, localhost, 4466]
        Echo server. Type text to echo; or just Enter to exit.
        
   <li> type stuff; when you hit Enter it should be echoed back to you.</li>
   <li> when you hit just Enter, both client and server stop with a message.</li>
   </ol>
<li> there is a bug in the server when stopping, can you fix it?</li>
</ol>

# Example of RMI client/server application using dynamic proxy

This is a minimal example of a rmi application using a dynamic proxy aproache instead of using stubs.

#How to run

First build the project.

### Start rmi registry

```bash
cd bin; remiregistry &
java rmi.fileserver.server.FileServerApp &
```

After that the server should be running nd you can run the client and call the remote methods on the file server. Check FileServerClientApp class for an example of how to call the server.
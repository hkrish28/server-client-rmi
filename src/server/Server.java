package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public interface Server extends Remote {

  String get(String key) throws RemoteException, ExecutionException, InterruptedException;

  String put(String key, String value) throws RemoteException, ExecutionException, InterruptedException;

  String delete(String key) throws RemoteException, ExecutionException, InterruptedException;


}

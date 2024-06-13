package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ServerImpl implements Server, Serializable {

//  ExecutorService executorService = Executors.newFixedThreadPool(3);

  private Map<String, String > mapStore = new ConcurrentHashMap<>();


  @Override
  public synchronized String get(String key) throws RemoteException {
    if(mapStore.containsKey(key)) {
      return mapStore.get(key);
    }
    return "Key " + key + " not present in store";
  }

  @Override
  public synchronized String put(String key, String value) throws RemoteException {
    mapStore.put(key, value);
    return "Value updated for " + key + " successfully";
  }

  @Override
  public synchronized String delete(String key) throws RemoteException {
    if(mapStore.containsKey(key)) {
      mapStore.remove(key);
      return key + " deleted successfully";
    }
    else {
      return key + " not present in the store to be removed";
    }
  }
}

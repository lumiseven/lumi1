package zookeeper_test_02;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class Bootstrap implements Watcher{
	
	private Logger logger = Logger.getLogger(getClass());
	
	private ZooKeeper zk;
	
	private String hostPort;
	
	Bootstrap(String hostPort){
		this.hostPort = hostPort;
	}
	
	public void process(WatchedEvent e) {
		System.out.println(e);
	}
	
	void startZK() throws IOException{
		zk = new ZooKeeper(hostPort, 15000, this);
	}

	
	
	
	public void bootstrap(){
		createParent("/workers", new byte[0]);
		createParent("/assign", new byte[0]);
		createParent("/tasks", new byte[0]);
		createParent("/status", new byte[0]);
	}
	
	private void createParent(String path, byte[] data){
		zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createParentCallback, data);
	}
	
	StringCallback createParentCallback = new StringCallback(){

		public void processResult(int rc, String path, Object ctx, String name) {
			switch(Code.get(rc)){
			case CONNECTIONLOSS:
				createParent(path, (byte[])ctx);
				break;
			case OK:
				logger.info("Parent created");
				break;
			case NODEEXISTS:
				logger.warn("Parent already registered: " + path);
				break;
			default:
				logger.error("Something went wrong: ", KeeperException.create(Code.get(rc), path));
			}
		}
		
	};
}

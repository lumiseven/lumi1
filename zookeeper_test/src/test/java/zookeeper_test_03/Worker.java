package zookeeper_test_03;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker implements Watcher{

	private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
	
	ZooKeeper zk;
	String hostPort;
	Random random = new Random();
	String serverId = Integer.toHexString(random.nextInt());
	
	Worker(String hostPort){
		this.hostPort = hostPort;
	}
	
	void startZK() throws IOException {
		zk = new ZooKeeper(hostPort, 15000, this);
	}
	
	public void process(WatchedEvent event) {
		LOG.info(event.toString() + ", " + hostPort);
	}
	
	void register(){
		zk.create("/worker/worker-" + serverId, "Idle".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, createWorkerCallback, null);
	}
	
	StringCallback createWorkerCallback = new StringCallback(){

		public void processResult(int rc, String path, Object ctx, String name) {
			switch (Code.get(rc)){
			case CONNECTIONLOSS:
				register();
				break;
			case OK:
				LOG.info("Registered successfully: " + serverId);
				break;
			case NODEEXISTS:
				LOG.warn("Already registered: " + serverId);
				break;
			default:
				LOG.error("Something went wrong: " + KeeperException.create(Code.get(rc), path));
			}
		}
		
	};
	
	public static void main(String [] args) throws Exception{
		Worker w = new Worker(args[0]);
		w.startZK();
		
		w.register();
		
		Thread.sleep(30000);
	}

}

package zookeeper_test_01;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class Master2 implements Watcher{
	
	ZooKeeper zk;
	String hostPort;
	
	Random random = new Random();
	String serverId = Long.toString(random.nextLong());
	static boolean isLeader = false;
	
	//asynchronous 
	void runForMaster() {
		zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallback, null);
	}
	
	StringCallback masterCreateCallback = new StringCallback() {
		public void processResult(int rc, String path, Object ctx, String name){
			switch(Code.get(rc)){//rc若不为0则对应KeepException异常
			case CONNECTIONLOSS:
				checkMaster();
				return;
			case OK:
				isLeader = true;
				break;
			default:
				isLeader = false;
			}
			
			System.out.println("I'm" + (isLeader? "" : "not") + "the leader");
		}
	};
	
	void checkMaster() {
		zk.getData("/master", false, masterCheckCallback, null);
	}
	
	DataCallback masterCheckCallback = new DataCallback(){
		public void processResult(int rc, String path, Object ctx, byte data[],
                Stat stat){
			switch(Code.get(rc)) {
			case CONNECTIONLOSS:
				checkMaster();
				return;
			case NONODE:
				runForMaster();
				return;
			}
		}
	};
	
	
	
	Master2(String hostPort){
		this.hostPort = hostPort;
	}
	
	void startZK() throws IOException{
		zk = new ZooKeeper(hostPort, 15000, this);
	}
	
	void stopZK() throws InterruptedException {
		zk.close();
	}

	public void process(WatchedEvent e) {
		System.out.println(e);
	}
	
	public static void main(String args[]) throws Exception{
		Master2 m = new Master2("127.0.0.1:2181");
		
		m.startZK();
		
		m.runForMaster();
		
		if (isLeader) {
			System.out.println("I'm the leader");
			Thread.sleep(60000);
		} else {
			System.out.println("Someone else is the leader");
		}
		
		m.stopZK();
	}

}
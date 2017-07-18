package zookeeper_test_01;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Master implements Watcher{
	
	ZooKeeper zk;
	String hostPort;
	
	Random random = new Random();
	String serverId = Long.toString(random.nextLong());
	static boolean isLeader = false;
	
	//return true if there is a master
	boolean checkMaster() throws KeeperException, InterruptedException {
		while (true) {
			try {
				Stat stat = new Stat();
				byte data[] = zk.getData("/master", false, stat);
				isLeader = new String(data).equals(serverId);
				return true;
			} catch (NoNodeException e) {
				return false;
			} catch (ConnectionLossException e){
				
			}
		}
	}
	
	void runForMaster() throws KeeperException, InterruptedException {
		while (true){
			try {
				zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);//此处采用同步的方式创建master
				isLeader = true;
				break;
			} catch (NodeExistsException e) {
				isLeader = false;
				break;
			} catch (ConnectionLossException e){
				
			}
			if (checkMaster()) break;
		}
	}
	
	
	
	Master(String hostPort){
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
		Master m = new Master("127.0.0.1:2181");
		
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

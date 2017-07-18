package zookeeper_test_05;

import java.io.IOException;
import java.util.Date;

import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class AdminClient implements Watcher{

	ZooKeeper zk;
	String hostPort;
	
	AdminClient(String hostPort){
		this.hostPort = hostPort;
	}
	
	void startZK() throws IOException {
		zk = new ZooKeeper(hostPort, 15000, this);
	}
	
	void listState() throws Exception{
		try {
			Stat stat = new Stat();
			byte[] masterData = zk.getData("/master", false, stat);
			Date startDate = new Date(stat.getCtime());
			System.out.println("master: " + new String(masterData) + "since" + startDate);
		} catch (NoNodeException e){
			System.out.println("No master");
		}
		
		System.out.println("Workers: ");
		for (String w : zk.getChildren("/workers", false)){
			byte[] workerData = zk.getData("/worker/" + w, false, null);
			System.out.println("\t" + w + ": " + new String(workerData));
		}
		
		System.out.println("Tasks: ");
		for (String t : zk.getChildren("/assign", false)){
			System.out.println("\t" + t);
		}
	}

	public void process(WatchedEvent event) {
		System.out.println(event);
	}
	
	public static void main(String[] args) throws Exception{
		AdminClient ac = new AdminClient(args[0]);
		ac.startZK();
		
		ac.listState();
	}
}

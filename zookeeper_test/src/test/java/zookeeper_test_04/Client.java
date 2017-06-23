package zookeeper_test_04;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.ZooDefs.Ids;

public class Client implements Watcher{

	ZooKeeper zk;
	String hostPort;
	
	Client(String hostPort){
		this.hostPort = hostPort;
	}
	
	void startZK() throws IOException {
		zk = new ZooKeeper(hostPort, 15000, this);
	}
	
	/*
	 * 如果在执行create时连接丢失，create操作会被重试，因为多次执行创建操作，
	 * 也许会为一个认为创建多个znode，对于大多是至少执行一次(execute at least once)
	 * 策略的应用程序，没什么问题，对于某些最多执行一次(execute at most once)
	 * 策略的应用程序，就需要为每个任务指定一个唯一的ID，并将其编码到znode的名称中，
	 * 遇到连接丢失异常时，只有在/tasks下不存在以这个会话ID命名的节点时才重试命令
	 */
	String queueCommand(String command) throws Exception {
		while (true) {
			String name = "";
			try {
				name = zk.create("/task/task-", command.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
				return name;
			} catch (NodeExistsException e) {
				throw new Exception(name + " already appears to be running");
			} catch (ConnectionLossException e){
				
			}
		}
	}

	public void process(WatchedEvent event) {
		System.out.println(event);
	}
	
	public static void main (String [] args) throws Exception {
		Client c = new Client(args[0]);
		
		c.startZK();
		
		String name = c.queueCommand(args[1]);
		System.out.println("Created " + name);
	}
}

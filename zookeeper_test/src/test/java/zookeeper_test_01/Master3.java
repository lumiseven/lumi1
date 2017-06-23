package zookeeper_test_01;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Master3 implements Watcher{
	
	ZooKeeper zk;
	String hostPort;
	
	Random random = new Random();
	String serverId = Long.toString(random.nextLong());
	static boolean isLeader = false;
	
	Master3(String hostPort){
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
	
	//asynchronous 
	void runForMaster() {
		zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallback, null);//此处采用异步的方式创建master
	}
	
	StringCallback masterCreateCallback = new StringCallback() {
		public void processResult(int rc, String path, Object ctx, String name){
			switch(Code.get(rc)){//rc若不为0则对应KeepException异常
			case CONNECTIONLOSS:
				checkMaster();//验证master是否被创建
				return;
			case OK:
				isLeader = true;
				break;
			case NODEEXISTS://若znode存在，其余备份服务端添加watcher检查状态，若master断开连接，需要重新指定master
				isLeader = false;
				masterExists();
				break;
			default:
				isLeader = false;
			}
			
			System.out.println("I'm" + (isLeader? "" : "not") + "the leader");
		}
	};
	
	void masterExists() {//给master znode绑定watcher事件
		zk.exists("/master", masterExistsWatcher, masterExistsCallback, null);
	}
	
	Watcher masterExistsWatcher = new Watcher(){//仅监听NodeDeleted事件，若master的znode被delete，重新创建master的znode

		public void process(WatchedEvent event) {
			if (event.getType() == EventType.NodeDeleted){
				assert "/master".equals( event.getPath() );
				
				runForMaster();
			}
		}
		
	};
	
	StatCallback masterExistsCallback = new StatCallback(){

		public void processResult(int rc, String path, Object ctx, Stat stat) {
			switch(Code.get(rc)){
			case CONNECTIONLOSS:
				masterExists();
				break;
			case OK:
				if (stat == null){//如果回调发现master已经断开，重建master(竞选主节点)
					runForMaster();
				}
				break;
			default:
				checkMaster();
				break;
			}
		}
		
	};
	
	void checkMaster() {
		zk.getData("/master", false, masterCheckCallback, null);
	}
	
	DataCallback masterCheckCallback = new DataCallback(){
		public void processResult(int rc, String path, Object ctx, byte data[],
                Stat stat){
			switch(Code.get(rc)) {
			case CONNECTIONLOSS://连接丢失再次尝试连接
				checkMaster();
				return;
			case NONODE://没有master则创建
				runForMaster();
				return;
			}
		}

	};
	
	
	
	
	
	public static void main(String args[]) throws Exception{
		Master3 m = new Master3("127.0.0.1:2181");
		
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
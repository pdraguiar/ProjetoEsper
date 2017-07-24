import java.util.concurrent.TimeUnit;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.base.BaseWindowedBolt.Duration;
import org.apache.storm.topology.TopologyBuilder;

public class Main {

	public static void main(String[] args) {
		Config config = new Config();
		
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("spout1", new Spout1());
		builder.setSpout("spout2", new Spout2());
		builder.setSpout("spout3", new Spout3());
		builder.setSpout("spout4", new Spout4());
		builder.setSpout("spout5", new Spout5());
		//builder.setBolt("splitter-bolt", new SplitterBolt()).shuffleGrouping("array-spout");
		//builder.setBolt("word-count", new CountBolt()).shuffleGrouping("splitter-bolt");
		//builder.setBolt("view-taxi-bolt", new ViewTaxiBolt()).shuffleGrouping("array-spout");
		builder.setBolt("esperbolt", 
				new EsperBolt()).allGrouping("spout1")
								.allGrouping("spout2")
								.allGrouping("spout3")
								.allGrouping("spout4")
								.allGrouping("spout5");
		
		LocalCluster local = new LocalCluster();
		local.submitTopology("UPE Esper", config, builder.createTopology());

	}

}

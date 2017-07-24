import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class Spout1 implements IRichSpout {
	SpoutOutputCollector collector;
	
	public static final String CAMINHO_TXT = "<<Caminho para o txt do Taxi 1>>";

	BufferedReader br;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void open(Map arg0, TopologyContext config, SpoutOutputCollector collector) {
		this.collector = collector;

		try {
			br = new BufferedReader(new FileReader(CAMINHO_TXT));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void nextTuple() {
		try {
			String line = br.readLine();

			if (line != null) {
				String[] split = line.split(",");
				Integer id = Integer.valueOf(split[0]);
				Date data = formatter.parse(split[1]);
				Double latitude = Double.valueOf(split[2]);
				Double longitude = Double.valueOf(split[3]);

				Taxi taxi = new Taxi(id, data, latitude, longitude);

				this.collector.emit(new Values(taxi));

				Thread.sleep(1000);
			}
			else {
				br.close();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("taxi"));
	}

	@Override
	public void ack(Object arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void fail(Object arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}

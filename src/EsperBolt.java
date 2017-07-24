import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class EsperBolt implements IRichBolt{

	OutputCollector collector;
	EPServiceProvider cep;
	EPAdministrator cepAdm;
	EPStatement stmt;
	
	@Override
	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		Configuration esperConfig = new Configuration();
		esperConfig.addEventType("Taxi", Taxi.class.getName());
		esperConfig.addPlugInSingleRowFunction("distance", "DistanceCalculator", "distance");
		
		cep = EPServiceProviderManager.getProvider("CepTaxi", esperConfig);
		cepAdm = cep.getEPAdministrator();
		EPStatement stmt = cepAdm.createEPL("SELECT t, t2 FROM pattern "
				+ "[every t=Taxi -> t2=Taxi(id != t.id, distance(latitude, longitude, t.latitude, t.longitude) < 100)]");
		//stmt = cepAdm.createEPL("SELECT * FROM Taxi");
		
		stmt.addListener(new UpdateListener() {

			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				for (EventBean eventBean : newEvents) {
					HashMap<?, EventBean> map = (HashMap) eventBean.getUnderlying();
					Iterator<EventBean> i = map.values().iterator();
					StringBuilder builder = new StringBuilder();
					
					builder.append("Taxis ");
					
					while(i.hasNext()) {
						Taxi t = (Taxi) ((EventBean) i.next()).getUnderlying();
						builder.append(t.getId()+" e ");
					}
					
					builder.setLength(builder.length()-3);
					builder.append(" muito proximos");
					System.out.println(builder.toString());
				}
			}
			
		});
	}


	@Override
	public void execute(Tuple input) {
		Taxi linha = (Taxi) input.getValueByField("taxi");
		cep.getEPRuntime().sendEvent(linha);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}
	
	

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}


	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}


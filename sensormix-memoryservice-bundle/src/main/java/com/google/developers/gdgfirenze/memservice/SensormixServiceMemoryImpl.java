package com.google.developers.gdgfirenze.memservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.developers.gdgfirenze.model.AbstractSample;
import com.google.developers.gdgfirenze.model.DailySampleReport;
import com.google.developers.gdgfirenze.model.SampleReport;
import com.google.developers.gdgfirenze.model.Sensor;
import com.google.developers.gdgfirenze.service.SensormixService;

public class SensormixServiceMemoryImpl implements SensormixService {

//	private static Logger logger = Logger.getLogger(SensormixServiceMemoryImpl.class.getName());

	private List<AbstractSample> samples = new ArrayList<AbstractSample>();
	private Map<String, Sensor> sensors = new HashMap<String, Sensor>();

	@Override
	public List<String> listSensorsIds() {
		List<String> ret = new ArrayList<String>();   
		for (Sensor sensor : sensors.values()) {
			if (sensor.getId() != null) {
				ret.add(sensor.getId());
			}
		}
		return ret;
	}

	/**
	 * Search criteria:
	 * null sensorId => any sensor
	 * null sampleType => any sample type
	 * limitCount != null => stop when ret.size() >= limitCount
	 * limitFrom != null => skip the first limitFrom items
	 */
	@Override
	public List<AbstractSample> getSamples(String sensorId, String sampleType, Date from, Date to,
			Long limitFrom, Long limitCount) {
		
		List<AbstractSample> ret = new ArrayList<>();
		if (sensorId == null && sampleType == null && from == null && to == null && limitFrom == null && limitCount == null) {
			for (AbstractSample currentSample : samples) {
				ret.add(currentSample);
			}
		} else {
			for (AbstractSample currentSample : samples) {
				if(!isSampleToFilter(currentSample, sensorId, sampleType, from, to)) {
					if (limitCount != null && ret.size() >= limitCount) {
						
					} else {
						ret.add(currentSample);
					}
//					if (limitCount == null) {
//						ret.add(currentSample);
//					} else if (limitCount != null && ret.size() <= limitCount) {
//						ret.add(currentSample);
//					}
				}
			}
		}
		return ret;
	}

	@Override
	public long countSamples(String sensorId, String sampleType, Date from, Date to) {
		long ret = 0;
		if (sensorId == null && sampleType == null && from == null && to == null) {
			ret = samples.size();
		} else {
			for (AbstractSample currentSample : samples) {
				if(!isSampleToFilter(currentSample, sensorId, sampleType, from, to)) {
					ret++;
				}
			}
		}
		return ret;
	}
	
	/**
	 * TODO: Add implementation: ignore for the moment
	 */
	@Override
	public SampleReport getSampleReport(String sensorId, String sampleType, Date from, Date to) {

		SampleReport sr = new SampleReport();
		sr.setSensorId(sensorId);
		sr.setSampleType(sampleType);
		sr.setDailySampleReports(new ArrayList<DailySampleReport>());
		return sr;
	}

	@Override
	public List<Sensor> getSensors(List<String> sensorIds, Date from, Date to) {
		List<Sensor> ret = new ArrayList<Sensor>();
		if (sensorIds == null && from == null && to == null) {
			for (Sensor currentSensor : sensors.values()) {
				ret.add(currentSensor);
			}
		} else {
			if (sensorIds != null && sensorIds.size() > 0) {
				for (String currentSensorId : sensorIds) {
					if (sensors.containsKey(currentSensorId)) {
						Sensor currentSensor = sensors.get(currentSensorId);
						if (from != null && to != null) {
							if (currentSensor.getLastSeen().compareTo(from) >= 0 && currentSensor.getLastSeen().compareTo(to) <= 0) {
								ret.add(currentSensor);
							}
						} else if (from == null && to != null) {
							if (currentSensor.getLastSeen().compareTo(to) <= 0) {
								ret.add(currentSensor);
							}
						} else if (from != null && to == null) {
							if (currentSensor.getLastSeen().compareTo(from) >= 0) {
								ret.add(currentSensor);
							}
						} else {
							ret.add(currentSensor);
						}
					}
				}
			}
		}
		return ret;
	}

	@Override
	public void registerSensor(Sensor sensor) {
		boolean found = false;
		if (sensors != null && sensors.values() != null && sensors.values().size() > 0) {
			for (Sensor currentSensor : sensors.values()) {
				if (currentSensor.getId().equalsIgnoreCase(sensor.getId())) {
					found = true;
				}
			}
		}
		if (!found) {
			sensors.put(sensor.getId(), sensor);
		}
	}

	@Override
	public void recordSamples(List<AbstractSample> samplesToAdd) {
		if (samplesToAdd != null && samplesToAdd.size() > 0) {
			samples.addAll(samplesToAdd);
		}
	}

	/**
	 * TODO: Add implementation: ignore for the moment
	 */
	@Override
	public List<String> listSamplesTypes() {
		List<String> ret = new ArrayList<String>();
		return ret;
	}
	
	
	/**************************************
	 * PRIVATE METHODS: for internal use. *
	 **************************************/
	
	private boolean isSampleToFilter(AbstractSample currentSample,
			String sensorId, String sampleType, Date from, Date to) {
		boolean isToFilter = false;
		if (sensorId != null) {
			if(!currentSample.getSensorId().equalsIgnoreCase(sensorId)) {
				isToFilter = true;
			}
		}
		if (isToFilter == false && sampleType != null) {
			if (!currentSample.getType().equalsIgnoreCase(sampleType)) {
				isToFilter = true;
			}
		}
		if (isToFilter == false && from != null && to != null) {
			if (currentSample.getTime().compareTo(from) < 0 || currentSample.getTime().compareTo(to) > 0) {
				isToFilter = true;
			}
		} else if (isToFilter == false && from != null && to == null) {
			if (currentSample.getTime().compareTo(from) < 0) {
				isToFilter = true;
			}
		} else if (isToFilter == false && from == null && to != null) {
			if (currentSample.getTime().compareTo(to) > 0) {
				isToFilter = true;
			}
		}
		return isToFilter;
	}

}
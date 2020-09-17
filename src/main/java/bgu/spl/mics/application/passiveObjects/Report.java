package bgu.spl.mics.application.passiveObjects;

import java.util.List;

/**
 * Passive data-object representing a delivery vehicle of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Report {
	private String missionN;
	private int idM;
	private int idMoneypenny;
	private List<String> agentsSerialNumbers;
	private List<String> agentsNames;
	private String gadgetName;
	private int timeIssued;
	private int QTime;
	private int timeCreated;

	/**
     * Retrieves the mission name.
     */
	public String getMissionName() {
		return missionN;
	}

	/**
	 * Sets the mission name.
	 */
	public void setMissionName(String missionName) {
		missionN=missionName;
	}

	/**
	 * Retrieves the M's id.
	 */
	public int getM() {
		return idM;
	}

	/**
	 * Sets the M's id.
	 */
	public void setM(int m) {
		idM=m;
	}

	/**
	 * Retrieves the Moneypenny's id.
	 */
	public int getMoneypenny() {
		return idMoneypenny;
	}

	/**
	 * Sets the Moneypenny's id.
	 */
	public void setMoneypenny(int moneypenny) {
		idMoneypenny=moneypenny;
	}

	/**
	 * Retrieves the serial numbers of the agents.
	 * <p>
	 * @return The serial numbers of the agents.
	 */
	public List<String> getAgentsSerialNumbers() {
		return agentsSerialNumbers;
	}

	/**
	 * Sets the serial numbers of the agents.
	 */
	public void setAgentsSerialNumbers(List<String> agentsSerialNumbersNumber) {
		agentsSerialNumbers=agentsSerialNumbersNumber;
	}

	/**
	 * Retrieves the agents names.
	 * <p>
	 * @return The agents names.
	 */
	public List<String> getAgentsNames() {
		return agentsNames;
	}

	/**
	 * Sets the agents names.
	 */
	public void setAgentsNames(List<String> _agentsNames) {
		agentsNames=_agentsNames;
	}

	/**
	 * Retrieves the name of the gadget.
	 * <p>
	 * @return the name of the gadget.
	 */
	public String getGadgetName() {
		return gadgetName;
	}

	/**
	 * Sets the name of the gadget.
	 */
	public void setGadgetName(String _gadgetName) {
		gadgetName=_gadgetName;
	}

	/**
	 * Retrieves the time-tick in which Q Received the GadgetAvailableEvent for that mission.
	 */
	public int getQTime() {
		return QTime;
	}

	/**
	 * Sets the time-tick in which Q Received the GadgetAvailableEvent for that mission.
	 */
	public void setQTime(int qTime) {
		QTime=qTime;
	}

	/**
	 * Retrieves the time when the mission was sent by an Intelligence Publisher.
	 */
	public int getTimeIssued() {
		return timeIssued;
	}

	/**
	 * Sets the time when the mission was sent by an Intelligence Publisher.
	 */
	public void setTimeIssued(int _timeIssued) {
		timeIssued=_timeIssued;
	}

	/**
	 * Retrieves the time-tick when the report has been created.
	 */
	public int getTimeCreated() {
		return timeCreated;
	}

	/**
	 * Sets the time-tick when the report has been created.
	 */
	public void setTimeCreated(int _timeCreated) {
		timeCreated=_timeCreated;
	}
}

package bgu.spl.mics.application.passiveObjects;

import java.util.List;

/**
 * Passive data-object representing information about a mission.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class MissionInfo {
	private List<String> serialAgentsNumbers;
	private int duration;
	private String gadget;
	private String name;
	private int timeExpired;
	private int timeIssued;

	/**
     * Sets the name of the mission.
     */
    public void setMissionName(String _missionName) {
		name=_missionName;
    }

	/**
     * Retrieves the name of the mission.
     */
	public String getMissionName() {
		return name;
	}

    /**
     * Sets the serial agent number.
     */
    public void setSerialAgentsNumbers(List<String> _serialAgentsNumbers) {
		serialAgentsNumbers=_serialAgentsNumbers;
    }

	/**
     * Retrieves the serial agent number.
     */
	public List<String> getSerialAgentsNumbers() {
		return serialAgentsNumbers;
	}

    /**
     * Sets the gadget name.
     */
    public void setGadget(String _gadget) {
		gadget=_gadget;
    }

	/**
     * Retrieves the gadget name.
     */
	public String getGadget() {
		return gadget;
	}

    /**
     * Sets the time the mission was issued in milliseconds.
     */
    public void setTimeIssued(int _timeIssued) {
		timeIssued=_timeIssued;
    }

	/**
     * Retrieves the time the mission was issued in milliseconds.
     */
	public int getTimeIssued() {
		return timeIssued;
	}

    /**
     * Sets the time that if it that time passed the mission should be aborted.
     */
    public void setTimeExpired(int _timeExpired) {
		timeExpired=_timeExpired;
    }

	/**
     * Retrieves the time that if it that time passed the mission should be aborted.
     */
	public int getTimeExpired() {
		return timeExpired;
	}

    /**
     * Sets the duration of the mission in time-ticks.
     */
    public void setDuration(int _duration) {
		duration=_duration;
    }

	/**
	 * Retrieves the duration of the mission in time-ticks.
	 */
	public int getDuration() {
		return duration;
	}
}

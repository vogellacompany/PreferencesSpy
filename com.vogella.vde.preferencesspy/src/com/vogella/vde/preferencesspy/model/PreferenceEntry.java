package com.vogella.vde.preferencesspy.model;

public class PreferenceEntry {

	private String nodePath;

	private String key;

	private String oldValue;

	private String newValue;

	public PreferenceEntry() {
	}

	public PreferenceEntry(String nodePath, String key, String oldValue,
			String newValue) {
		this.nodePath = nodePath;
		this.key = key;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
}

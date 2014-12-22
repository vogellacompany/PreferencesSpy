package com.vogella.vde.preferencesspy.model;

public class PreferenceEntryKey {

	private String key;
	private String nodePath;

	public PreferenceEntryKey(String nodePath, String key) {
		this.setNodePath(nodePath);
		this.setKey(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((nodePath == null) ? 0 : nodePath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PreferenceEntryKey other = (PreferenceEntryKey) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (nodePath == null) {
			if (other.nodePath != null) {
				return false;
			}
		} else if (!nodePath.equals(other.nodePath)) {
			return false;
		}
		return true;
	}

}

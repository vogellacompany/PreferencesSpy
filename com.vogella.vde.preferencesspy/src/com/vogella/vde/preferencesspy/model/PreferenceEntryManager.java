package com.vogella.vde.preferencesspy.model;

import java.util.HashMap;
import java.util.Map;

public class PreferenceEntryManager extends PreferenceNodeEntry {

	private Map<String, PreferenceNodeEntry> recentPreferenceEntries = new HashMap<String, PreferenceNodeEntry>();

	public PreferenceEntryManager() {
	}

	public PreferenceNodeEntry getRecentPreferenceNodeEntry(String nodePath) {
		return recentPreferenceEntries.get(nodePath);
	}

	public PreferenceNodeEntry removeRecentPreferenceNodeEntry(String nodePath) {
		return recentPreferenceEntries.remove(nodePath);
	}

	public void clearRecentPreferenceNodeEntry() {
		recentPreferenceEntries.clear();
	}

	public void putRecentPreferenceEntry(String nodePath, PreferenceNodeEntry preferenceNodeEntry) {
		recentPreferenceEntries.put(nodePath, preferenceNodeEntry);
	}

}

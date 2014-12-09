package com.vogella.vde.preferencesspy.model;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;

public class PreferenceNodeEntry extends PreferenceEntry {

	private IObservableSet preferenceEntries = new WritableSet();

	public PreferenceNodeEntry() {
		super();
	}

	public PreferenceNodeEntry(String nodePath) {
		super(nodePath, "", "", "");
	}

	public void addChildren(Collection<PreferenceEntry> entries) {
		getPreferenceEntries().addAll(entries);
	}

	public void addChildren(PreferenceEntry... entry) {
		getPreferenceEntries().addAll(Arrays.asList(entry));
	}

	public void removeChildren(Collection<PreferenceEntry> entries) {
		getPreferenceEntries().removeAll(entries);
	}

	public void removeChildren(PreferenceEntry... entry) {
		getPreferenceEntries().removeAll(Arrays.asList(entry));
	}

	public IObservableSet getPreferenceEntries() {
		return preferenceEntries;
	}

	public void setPreferenceEntries(IObservableSet preferenceEntries) {
		this.preferenceEntries = preferenceEntries;
	}

}

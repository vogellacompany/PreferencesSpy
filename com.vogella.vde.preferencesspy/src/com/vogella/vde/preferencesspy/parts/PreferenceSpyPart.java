package com.vogella.vde.preferencesspy.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.vogella.vde.preferencesspy.constants.PreferenceSpyEventTopics;
import com.vogella.vde.preferencesspy.model.PreferenceEntry;

public class PreferenceSpyPart {

	private TableViewer tableViewer;
	private WritableList input;

	@PostConstruct
	public void postConstruct(Composite parent) {
		tableViewer = new TableViewer(parent);

		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		createColumn("Nodepath", 300);
		createColumn("Key", 300);
		createColumn("Old Value", 150);
		createColumn("New Value", 150);

		input = new WritableList();
		ViewerSupport.bind(
				tableViewer,
				input,
				PojoProperties.values(new String[] { "nodePath", "key",
						"oldValue", "newValue" }));
	}

	private void createColumn(String columnName, int width) {
		TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		viewerColumn.getColumn().setWidth(width);
		viewerColumn.getColumn().setText(columnName);

		viewerColumn.setLabelProvider(new ColumnLabelProvider());
	}

	@Inject
	@Optional
	public void preferenceChanged(
			@UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_CHANGED) PreferenceChangeEvent event) {
		input.add(new PreferenceEntry(event.getNode().absolutePath(), event
				.getKey(), String.valueOf(event.getOldValue()), String
				.valueOf(event.getNewValue())));
	}

	@Inject
	@Optional
	public void DeletePreferenceEntries(
			@UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_ENTRIES_DELETE) PreferenceChangeEvent event) {
		input.removeAll(input);
	}

	@Inject
	@Optional
	public void DeleteAllPreferenceEntries(
			@UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_ENTRIES_DELETE_ALL) PreferenceChangeEvent event) {
		input.clear();
	}

}
package com.vogella.vde.preferencesspy.parts;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.vogella.vde.preferencesspy.constants.PreferenceSpyEventTopics;
import com.vogella.vde.preferencesspy.model.PreferenceEntry;
import com.vogella.vde.preferencesspy.model.PreferenceEntry.Fields;

public class PreferenceSpyPart {

	private TableViewer tableViewer;
	private WritableList input;

	@PostConstruct
	public void postConstruct(Composite parent,
			final ESelectionService selectionService,
			EModelService modelService, MWindow window) {
		tableViewer = new TableViewer(parent);

		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = event.getSelection();
						if (selection instanceof IStructuredSelection) {
							@SuppressWarnings("unchecked")
							ArrayList<PreferenceEntry> preferenceEntries = new ArrayList<PreferenceEntry>(
									((IStructuredSelection) selection).toList());
							selectionService.setSelection(preferenceEntries);
						}
					}
				});

		createColumn(Fields.nodePath, "Nodepath", 300);
		createColumn(Fields.key, "Key", 300);
		createColumn(Fields.oldValue, "Old Value", 150);
		createColumn(Fields.newValue, "New Value", 150);

		input = new WritableList();
		ViewerSupport.bind(
				tableViewer,
				input,
				PojoProperties.values(new String[] { "nodePath", "key",
						"oldValue", "newValue" }));
	}

	private void createColumn(Fields field, String columnName, int width) {
		TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		viewerColumn.getColumn().setWidth(width);
		viewerColumn.getColumn().setText(columnName);

		viewerColumn.setLabelProvider(new ColumnLabelProvider());
		viewerColumn.setEditingSupport(new PreferenceSpyEditingSupport(
				tableViewer, field));
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
			@UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_ENTRIES_DELETE) List<PreferenceEntry> preferenceEntries) {
		if (preferenceEntries != null && !preferenceEntries.isEmpty()) {
			input.removeAll(preferenceEntries);
		}
	}

	@Inject
	public void DeleteAllPreferenceEntries(
			@Optional @UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_ENTRIES_DELETE_ALL) List<PreferenceEntry> preferenceEntries) {
		if (input != null) {
			input.clear();
		}
	}

}
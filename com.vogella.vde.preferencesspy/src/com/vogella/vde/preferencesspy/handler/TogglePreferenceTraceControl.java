package com.vogella.vde.preferencesspy.handler;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.prefs.BackingStoreException;

import com.vogella.vde.preferencesspy.constants.PreferenceConstants;

public class TogglePreferenceTraceControl {

	@Inject
	Logger LOG;

	private ToolItem toolItem;
	private ResourceManager resourceManager;

	@Inject
	public void tracePreferenceChanged(
			@Preference(value = PreferenceConstants.TRACE_PREFERENCES) boolean tracePreferences) {
		if (toolItem != null && !toolItem.isDisposed()) {
			toolItem.setSelection(tracePreferences);
		}
	}

	@PostConstruct
	public void createGui(Composite parent, final @Preference IEclipsePreferences preferences,
			@Preference(value = PreferenceConstants.TRACE_PREFERENCES) boolean tracePreferences) {
		ToolBar toolBar = new ToolBar(parent, SWT.NONE);
		toolItem = new ToolItem(toolBar, SWT.CHECK);
		toolItem.setSelection(tracePreferences);
		toolItem.setToolTipText("Toggle Preference Trace");
		toolItem.setImage(getResourceManager().createImage(getImageDescriptor()));
		toolItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Object source = event.getSource();
				if (source instanceof ToolItem) {
					preferences.putBoolean(PreferenceConstants.TRACE_PREFERENCES, ((ToolItem) source).getSelection());
					try {
						preferences.flush();
					} catch (BackingStoreException e) {
						LOG.error(e);
					}
				}
			}
		});
	}

	@PreDestroy
	public void dispose() {
		if (resourceManager != null) {
			resourceManager.dispose();
		}
	}

	protected ResourceManager getResourceManager() {
		if (null == resourceManager) {
			resourceManager = new LocalResourceManager(JFaceResources.getResources());
		}
		return resourceManager;
	}

	protected ImageDescriptor getImageDescriptor() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		URL url = FileLocator.find(bundle, new Path("icons/trace_preferences.png"), null);
		return ImageDescriptor.createFromURL(url);
	}
}

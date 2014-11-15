package com.vogella.vde.preferencesspy.addon;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IPreferenceNodeVisitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;

import com.vogella.vde.preferencesspy.Activator;
import com.vogella.vde.preferencesspy.constants.PreferenceConstants;
import com.vogella.vde.preferencesspy.constants.PreferenceSpyEventTopics;

public class PreferenceSpyAddon {

	@Inject
	private Logger LOG;

	@Inject
	private IEventBroker eventBroker;

	private IEclipsePreferences eclipsePreferences = InstanceScope.INSTANCE
			.getNode("");

	private ChangedPreferenceListener preferenceChangedListener = new ChangedPreferenceListener();

	@Inject
	@Optional
	public void initialzePreferenceSpy(
			@Preference(value = PreferenceConstants.TRACE_PREFERENCES) boolean tracePreferences) {
		if (tracePreferences) {
			registerVisitor();
		} else {
			deregisterVisitor();
		}
	}

	private void deregisterVisitor() {
		try {
			eclipsePreferences.accept(new IPreferenceNodeVisitor() {
				@Override
				public boolean visit(IEclipsePreferences node)
						throws BackingStoreException {
					node.removePreferenceChangeListener(preferenceChangedListener);
					return true;
				}
			});
		} catch (BackingStoreException e) {
			LOG.error(e);
		}
	}

	private void registerVisitor() {
		try {
			eclipsePreferences.accept(new IPreferenceNodeVisitor() {
				@Override
				public boolean visit(IEclipsePreferences node)
						throws BackingStoreException {
					node.addPreferenceChangeListener(preferenceChangedListener);
					return true;
				}
			});
		} catch (BackingStoreException e) {
			LOG.error(e);
		}
	}

	@PostConstruct
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.TRACE_PREFERENCES, false);
	}

	private final class ChangedPreferenceListener implements
			IPreferenceChangeListener {

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			eventBroker.post(
					PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_CHANGED,
					event);
		}
	}
}

package com.vogella.vde.preferencesspy.handler;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;

import com.vogella.vde.preferencesspy.constants.PreferenceConstants;

public class TogglePreferenceTraceHandler {
	@Execute
	public void execute(@Preference IEclipsePreferences eclipsePreferences) {
		eclipsePreferences.putBoolean(PreferenceConstants.TRACE_PREFERENCES,
				!eclipsePreferences.getBoolean(
						PreferenceConstants.TRACE_PREFERENCES, false));
	}
}
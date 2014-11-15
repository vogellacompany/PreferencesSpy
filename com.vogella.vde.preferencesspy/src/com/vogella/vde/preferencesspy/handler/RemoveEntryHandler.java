package com.vogella.vde.preferencesspy.handler;

import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;

import com.vogella.vde.preferencesspy.constants.PreferenceSpyEventTopics;
import com.vogella.vde.preferencesspy.model.PreferenceEntry;

public class RemoveEntryHandler {
	@Execute
	public void execute(
			IEventBroker eventBroker,
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) List<PreferenceEntry> preferenceEntries) {
		eventBroker
				.post(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_ENTRIES_DELETE,
						preferenceEntries);
	}

}
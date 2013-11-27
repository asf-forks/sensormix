package com.google.developers.gdgfirenze.karafcli.completers;

import java.util.List;

import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;

public class MaintenanceCompleter implements Completer {

	@Override
	public int complete(String buffer, int cursor, List<String> candidates) {
		StringsCompleter delegate = new StringsCompleter();
	    delegate.getStrings().add("on");
	    delegate.getStrings().add("off");
	    return delegate.complete(buffer, cursor, candidates);
	}

}


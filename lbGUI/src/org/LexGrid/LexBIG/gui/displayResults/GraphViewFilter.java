/*
 * Copyright: (c) 2004-2010 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.LexGrid.LexBIG.gui.displayResults;

import java.util.Iterator;

import prefuse.action.filter.FisheyeTreeFilter;
import prefuse.util.PrefuseLib;
import prefuse.visual.VisualItem;

/**
 * Overrides behavior of the standard FisheyeTreeFilter to maintain visibility
 * of all node connections.
 */
public class GraphViewFilter extends FisheyeTreeFilter {
	GraphView gv_ = null;

	public GraphViewFilter(String group, int depth, GraphView view) {
		super(group, depth);
		gv_ = view;
	}

	@SuppressWarnings("unchecked")
	public void run(double d) {
		super.run(d);
		// Superclass will make these items invisible; undo here...
		if (gv_.dcns.getShowSecondaryRelInGraph())
			for (Iterator<VisualItem> items = m_vis.items(m_group); items
					.hasNext();) {
				VisualItem item = items.next();
				if (item.getDOI() == -1.7976931348623157E+308D)
					PrefuseLib.updateVisible(item, true);
			}
	}
}
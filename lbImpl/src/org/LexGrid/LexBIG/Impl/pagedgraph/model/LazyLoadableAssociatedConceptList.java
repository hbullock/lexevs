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
package org.LexGrid.LexBIG.Impl.pagedgraph.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList;
import org.LexGrid.LexBIG.DataModel.Collections.SortOptionList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.Impl.pagedgraph.builder.AssociationListBuilder.AssociationDirection;
import org.LexGrid.LexBIG.Impl.pagedgraph.paging.AssociatedConceptIterator;
import org.LexGrid.LexBIG.Impl.pagedgraph.paging.callback.CycleDetectingCallback;
import org.lexevs.dao.database.service.codednodegraph.model.GraphQuery;

/**
 * The Class LazyLoadableAssociatedConceptList.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class LazyLoadableAssociatedConceptList extends AssociatedConceptList {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -434490124369412627L;
    
    /** The graph query. */
    private GraphQuery graphQuery;
    
    /** The coding scheme uid. */
    private String codingSchemeUri; 
    
    /** The coding scheme version. */
    private String codingSchemeVersion;
    
    /** The relations container name. */
    private String relationsContainerName; 
    
    /** The association predicate uid. */
    private String associationPredicateName; 
    
    /** The entity code. */
    private String entityCode;
    
    /** The entity code namespace. */
    private String entityCodeNamespace;
    
    /** The direction. */
    private AssociationDirection direction;
    
    /** The page size. */
    private int pageSize;
    
    /** The count. */
    private int count;
    
    /** The resolve forward association depth. */
    private int resolveForwardAssociationDepth;
    
    /** The resolve backward association depth. */
    private int resolveBackwardAssociationDepth;
    
    /** The resolve coded entry depth. */
    private int resolveCodedEntryDepth;
    
    /** The resolve forward. */
    private boolean resolveForward;
    
    /** The resolve backward. */
    private boolean resolveBackward;
    
    /** The cycle detecting callback. */
    private CycleDetectingCallback cycleDetectingCallback;
    
    private SortOptionList sortAlgorithms;

    /**
     * Instantiates a new lazy loadable associated concept list.
     * 
     * @param count the count
     * @param entityCode the entity code
     * @param entityCodeNamespace the entity code namespace
     * @param direction the direction
     * @param pageSize the page size
     * @param codingSchemeUri the coding scheme uri
     * @param codingSchemeVersion the coding scheme version
     * @param relationsContainerName the relations container name
     * @param associationPredicateName the association predicate name
     * @param resolveForward the resolve forward
     * @param resolveBackward the resolve backward
     * @param resolveForwardAssociationDepth the resolve forward association depth
     * @param resolveBackwardAssociationDepth the resolve backward association depth
     * @param resolveCodedEntryDepth the resolve coded entry depth
     * @param graphQuery the graph query
     * @param cycleDetectingCallback the cycle detecting callback
     */
    public LazyLoadableAssociatedConceptList(
            int count,
            String codingSchemeUri, 
            String codingSchemeVersion, 
            String relationsContainerName,
            String associationPredicateName, 
            String entityCode,
            String entityCodeNamespace, 
            boolean resolveForward,
            boolean resolveBackward,
            int resolveForwardAssociationDepth,
            int resolveBackwardAssociationDepth,
            int resolveCodedEntryDepth,
            GraphQuery graphQuery,
            SortOptionList sortAlgorithms,
            CycleDetectingCallback cycleDetectingCallback,
            AssociationDirection direction,
            int pageSize) {
        super();
        this.count = count;
        this.codingSchemeUri = codingSchemeUri;
        this.codingSchemeVersion = codingSchemeVersion;
        this.associationPredicateName = associationPredicateName;
        this.relationsContainerName = relationsContainerName;
        this.entityCode = entityCode;
        this.entityCodeNamespace = entityCodeNamespace;
        this.resolveForwardAssociationDepth = resolveForwardAssociationDepth;
        this.resolveBackwardAssociationDepth = resolveBackwardAssociationDepth;
        this.resolveCodedEntryDepth = resolveCodedEntryDepth;
        this.graphQuery = graphQuery;
        this.direction = direction;
        this.pageSize = pageSize;
        this.resolveForward = resolveForward;
        this.resolveBackward = resolveBackward;
        this.cycleDetectingCallback = cycleDetectingCallback;
        this.sortAlgorithms = sortAlgorithms;
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#addAssociatedConcept(org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept)
     */
    @Override
	public void addAssociatedConcept(AssociatedConcept vAssociatedConcept)
			throws IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#addAssociatedConcept(int, org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept)
	 */
	@Override
	public void addAssociatedConcept(int index,
			AssociatedConcept vAssociatedConcept)
			throws IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#enumerateAssociatedConcept()
	 */
	@Override
	public Enumeration<AssociatedConcept> enumerateAssociatedConcept() {
		return new Enumeration<AssociatedConcept>() {

			private Iterator<AssociatedConcept> iterator = iterateAssociatedConcept();
			
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public AssociatedConcept nextElement() {
				return iterator.next();
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#getAssociatedConcept()
	 */
	@Override
	public AssociatedConcept[] getAssociatedConcept() {
	    List<AssociatedConcept> list = new ArrayList<AssociatedConcept>();
	    
	    Iterator<AssociatedConcept> itr = iterateAssociatedConcept();
	    
	    while(itr.hasNext()) {
	        list.add(itr.next());
	    }
	    
	    return list.toArray(new AssociatedConcept[list.size()]);
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#getAssociatedConcept(int)
	 */
	@Override
	public AssociatedConcept getAssociatedConcept(int index)
			throws IndexOutOfBoundsException {
		return getAssociatedConcept()[index];
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#getAssociatedConceptCount()
	 */
	@Override
	public int getAssociatedConceptCount() {
	    return count;
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#iterateAssociatedConcept()
	 */
	@Override
	public Iterator<AssociatedConcept> iterateAssociatedConcept() {
	    return new AssociatedConceptIterator(
	            this.codingSchemeUri,
	            this.codingSchemeVersion, 
	            this.relationsContainerName,
	            this.associationPredicateName,
	            this.entityCode,
	            this.entityCodeNamespace,
	            this.resolveForward,
	            this.resolveBackward,
	            this.resolveForwardAssociationDepth,
	            this.resolveBackwardAssociationDepth,
	            this.resolveCodedEntryDepth,
	            this.graphQuery,
	            this.sortAlgorithms,
	            this.cycleDetectingCallback,
	            this.direction,
	            this.pageSize);
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#removeAllAssociatedConcept()
	 */
	@Override
	public void removeAllAssociatedConcept() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#removeAssociatedConcept(org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept)
	 */
	@Override
	public boolean removeAssociatedConcept(AssociatedConcept vAssociatedConcept) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#removeAssociatedConceptAt(int)
	 */
	@Override
	public AssociatedConcept removeAssociatedConceptAt(int index) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#setAssociatedConcept(org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept[])
	 */
	@Override
	public void setAssociatedConcept(AssociatedConcept[] arg0) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList#setAssociatedConcept(int, org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept)
	 */
	@Override
	public void setAssociatedConcept(int index,
			AssociatedConcept vAssociatedConcept)
			throws IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}	
}

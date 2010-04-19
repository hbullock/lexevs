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
package org.LexGrid.LexBIG.Impl.pagedgraph.paging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.Impl.pagedgraph.builder.AssociationListBuilder.AssociationDirection;
import org.lexevs.dao.database.access.codednodegraph.CodedNodeGraphDao.TripleNode;
import org.lexevs.dao.database.service.codednodegraph.CodedNodeGraphService;
import org.lexevs.dao.database.service.codednodegraph.model.GraphQuery;
import org.lexevs.locator.LexEvsServiceLocator;
import org.lexevs.paging.AbstractPageableIterator;
import org.lexevs.paging.codednodegraph.TripleUidIterator;

/**
 * The Class AssociatedConceptIterator.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class AssociatedConceptIterator extends AbstractPageableIterator<AssociatedConcept> {

	/** The triple uid iterator. */
	private Iterator<String> tripleUidIterator;
	
	/** The direction. */
	private AssociationDirection direction;
	
	/** The coding scheme uid. */
	private String codingSchemeUri;
	
	private String codingSchemeVersion;
	
	/**
	 * Instantiates a new associated concept iterator.
	 * 
	 * @param codedNodeGraphDao the coded node graph dao
	 * @param codingSchemeUid the coding scheme uid
	 * @param associationPredicateUid the association predicate uid
	 * @param entityCode the entity code
	 * @param entityCodeNamespace the entity code namespace
	 * @param direction the direction
	 * @param pageSize the page size
	 */
	public AssociatedConceptIterator(
            String codingSchemeUri, 
            String codingSchemeVersion, 
            String relationsContainerName,
            String associationPredicateName, 
            String entityCode,
            String entityCodeNamespace,
            GraphQuery graphQuery,
            AssociationDirection direction,
            int pageSize){
		super(pageSize);
		tripleUidIterator = new 
		    TripleUidIterator(
		    		codingSchemeUri, 
		    		codingSchemeVersion, 
		    		relationsContainerName,
		            associationPredicateName, 
		            entityCode, 
	                entityCodeNamespace, 
		    		graphQuery,
		    		getTripleUidIteratorNode(direction), 
		    		pageSize);
		this.codingSchemeUri = codingSchemeUri;
		this.codingSchemeVersion = codingSchemeVersion;
		this.direction = direction;
	}

    /* (non-Javadoc)
     * @see org.lexevs.paging.AbstractPageableIterator#doPage(int, int)
     */
    @Override
    protected List<AssociatedConcept> doPage(int currentPosition, int pageSize) {
        CodedNodeGraphService codedNodeGraphSerivce =
            LexEvsServiceLocator.getInstance().getDatabaseServiceManager().getCodedNodeGraphService();
        
        List<AssociatedConcept> returnList = new ArrayList<AssociatedConcept>();
        
        int count = 0;
        while(this.tripleUidIterator.hasNext() && count < pageSize) {
            String tripleUid = tripleUidIterator.next();
            if(direction.equals(AssociationDirection.SOURCE_OF)) {
                returnList.add(
                        codedNodeGraphSerivce.
                        getAssociatedConceptFromUidTarget(
                                codingSchemeUri, 
                                codingSchemeVersion,
                                tripleUid));
            } else {
                returnList.add(
                        codedNodeGraphSerivce.
                        getAssociatedConceptFromUidSource(
                                codingSchemeUri, 
                                codingSchemeVersion,
                                tripleUid));
            }
            count++;
        }
        
        return returnList;
    }
	
	/**
	 * Gets the triple uid iterator node.
	 * 
	 * @param direction the direction
	 * 
	 * @return the triple uid iterator node
	 */
	protected TripleNode getTripleUidIteratorNode(AssociationDirection direction) {
	    if(direction.equals(AssociationDirection.SOURCE_OF)) {
            return TripleNode.SUBJECT;
        }
        
        if(direction.equals(AssociationDirection.TARGET_OF)) {
            return TripleNode.OBJECT;
        }
        
        throw new RuntimeException("Could not assign Triple Node.");
    }
	
	/**
	 * Gets the associated concept node.
	 * 
	 * @param direction the direction
	 * 
	 * @return the associated concept node
	 */
	protected TripleNode getAssociatedConceptNode(AssociationDirection direction) {
        if(direction.equals(AssociationDirection.SOURCE_OF)) {
            return TripleNode.OBJECT;
        }
        
        if(direction.equals(AssociationDirection.TARGET_OF)) {
            return TripleNode.SUBJECT;
        }
        
        throw new RuntimeException("Could not assign Triple Node.");
    }
}

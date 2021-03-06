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
package org.lexevs.dao.database.service.association;

import org.LexGrid.relations.AssociationPredicate;
import org.LexGrid.relations.AssociationSource;

/**
 * The Interface AssociationService.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public interface AssociationService {
	
	/** The Constant INSERT_ASSOCIATIONSOURCE_ERROR. */
	public final static String INSERT_ASSOCIATIONSOURCE_ERROR = "INSERT-ASSOCIATIONSOURCE-ERROR";
	
	/** The Constant INSERT_ASSOCIATIONPREDICATE_ERROR. */
	public final static String INSERT_ASSOCIATIONPREDICATE_ERROR = "INSERT-ASSOCIATIONPREDICATE-ERROR";

	/**
	 * Insert association source.
	 * 
	 * @param codingSchemeUri the coding scheme uri
	 * @param version the version
	 * @param relationContainerName the relation container name
	 * @param associationPredicateName the association predicate name
	 * @param source the source
	 */
	public void insertAssociationSource(String codingSchemeUri, String version,
			String relationContainerName, String associationPredicateName,
			AssociationSource source);
	
	/**
	 * Insert association predicate.
	 * 
	 * @param codingSchemeUri the coding scheme uri
	 * @param version the version
	 * @param relationsName the relations name
	 * @param predicate the predicate
	 */
	public void insertAssociationPredicate(
			String codingSchemeUri, String version, String relationsName, AssociationPredicate predicate);

	/**
	 * Gets the association triple by association instance id.
	 * 
	 * @param codingSchemeUri the coding scheme uri
	 * @param version the version
	 * @param associationInstanceId the association instance id
	 * 
	 * @return the association triple by association instance id
	 */
	public AssociationTriple getAssociationTripleByAssociationInstanceId(
			String codingSchemeUri, 
			String version,
			String associationInstanceId);
	
	/**
	 * The Class AssociationTriple.
	 */
	public static class AssociationTriple {
		
		/** The association source. */
		private AssociationSource associationSource;
		
		/** The relation container name. */
		private String relationContainerName;
		
		/** The association predicate name. */
		private String associationPredicateName;

		/**
		 * Instantiates a new association triple.
		 * 
		 * @param associationSource the association source
		 * @param relationContainerName the relation container name
		 * @param associationPredicateName the association predicate name
		 */
		public AssociationTriple(AssociationSource associationSource,
				String relationContainerName, String associationPredicateName) {
			super();
			this.associationSource = associationSource;
			this.relationContainerName = relationContainerName;
			this.associationPredicateName = associationPredicateName;
		}

		/**
		 * Gets the association source.
		 * 
		 * @return the association source
		 */
		public AssociationSource getAssociationSource() {
			return associationSource;
		}
		
		/**
		 * Sets the association source.
		 * 
		 * @param associationSource the new association source
		 */
		public void setAssociationSource(AssociationSource associationSource) {
			this.associationSource = associationSource;
		}
		
		/**
		 * Gets the relation container name.
		 * 
		 * @return the relation container name
		 */
		public String getRelationContainerName() {
			return relationContainerName;
		}
		
		/**
		 * Sets the relation container name.
		 * 
		 * @param relationContainerName the new relation container name
		 */
		public void setRelationContainerName(String relationContainerName) {
			this.relationContainerName = relationContainerName;
		}
		
		/**
		 * Gets the association predicate name.
		 * 
		 * @return the association predicate name
		 */
		public String getAssociationPredicateName() {
			return associationPredicateName;
		}
		
		/**
		 * Sets the association predicate name.
		 * 
		 * @param associationPredicateName the new association predicate name
		 */
		public void setAssociationPredicateName(String associationPredicateName) {
			this.associationPredicateName = associationPredicateName;
		}
	}
}
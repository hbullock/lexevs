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
package org.lexevs.dao.index.indexer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.LexGrid.LexBIG.DataModel.Core.AbsoluteCodingSchemeVersionReference;
import org.LexGrid.LexBIG.Utility.logging.LgLoggerIF;
import org.LexGrid.concepts.Entity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.lexevs.dao.database.service.entity.EntityService;
import org.lexevs.dao.index.access.IndexDaoManager;
import org.lexevs.dao.index.access.entity.EntityDao;
import org.lexevs.dao.index.access.search.SearchDao;
import org.lexevs.dao.index.factory.IndexLocationFactory;
import org.lexevs.system.constants.SystemVariables;
import org.lexevs.system.service.SystemResourceService;

import edu.mayo.informatics.indexer.utility.MetaData;

/**
 * The Class EntityBatchingIndexCreator.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class EntityBatchingIndexCreator implements IndexCreator {
	
	/** The batch size. */
	private int batchSize = 1000;
	
	/** The entity service. */
	private EntityService entityService;
	
	/** The system resource service. */
	private SystemResourceService systemResourceService;
	
	/** The system variables. */
	private SystemVariables systemVariables;

	private IndexDaoManager indexDaoManager;
	
	private MetaData metaData;

	private Analyzer analyzer = LuceneLoaderCode.getAnaylzer();
	
	private Analyzer standardAnalyzer = new StandardAnalyzer();
	
	private EntityIndexer entityIndexer;
	
	private EntityIndexer searchIndexer;
	
	private LgLoggerIF logger;
	
	public String index(AbsoluteCodingSchemeVersionReference reference) {
		return this.index(reference, null, false);
	}
	
	public String index(AbsoluteCodingSchemeVersionReference reference, EntityIndexerProgressCallback callback) {	
		return this.index(reference, callback, false);
	}

	/* (non-Javadoc)
	 * @see org.lexevs.dao.index.indexer.IndexCreator#index(org.LexGrid.LexBIG.DataModel.Core.AbsoluteCodingSchemeVersionReference)
	 */
	public String index(AbsoluteCodingSchemeVersionReference reference, EntityIndexerProgressCallback callback, boolean onlyRegister) {	
		String indexName = this.getIndexName(reference);
		
		addIndexMetadata(reference, indexName, entityIndexer.getIndexerFormatVersion().getModelFormatVersion());

		if(!onlyRegister) {

			EntityDao entityIndexService = indexDaoManager.getEntityDao(reference.getCodingSchemeURN(), reference.getCodingSchemeVersion());
			SearchDao searchIndexService = indexDaoManager.getSearchDao();

			int totalIndexedEntities = 0;

			int position = 0;
			for(
					List<? extends Entity> entities = 
						entityService.getEntities(reference.getCodingSchemeURN(), reference.getCodingSchemeVersion(), position, batchSize);
					entities.size() > 0; 
					entities = entityService.getEntities(reference.getCodingSchemeURN(), reference.getCodingSchemeVersion(), position += batchSize, batchSize)) {

				List<Document> fullEntityDocs = new ArrayList<Document>();
				List<Document> searchDocs = new ArrayList<Document>();

				for(Entity entity : entities) {
					fullEntityDocs.addAll(
						entityIndexer.indexEntity(
								reference.getCodingSchemeURN(), 
								reference.getCodingSchemeVersion(), entity));

					searchDocs.addAll(
							searchIndexer.indexEntity(
									reference.getCodingSchemeURN(), 
									reference.getCodingSchemeVersion(), entity));
					
					totalIndexedEntities++;

					if(callback != null) {
						callback.onEntityIndex(entity);
					}

					if(totalIndexedEntities % 1000 == 0) {
						this.getLogger().info("Indexed: " + totalIndexedEntities + " Entities.");
					}
				}

				this.getLogger().info("Flusing " + fullEntityDocs.size() + searchDocs.size() + " Documents to the Index.");
				entityIndexService.addDocuments(
						reference.getCodingSchemeURN(), 
						reference.getCodingSchemeVersion(), 
						fullEntityDocs, analyzer);
				
				searchIndexService.addDocuments(
						reference.getCodingSchemeURN(), 
						reference.getCodingSchemeVersion(), 
						searchDocs, standardAnalyzer);
			}

			this.getLogger().info("Indexing Complete. Indexed: " + totalIndexedEntities + " Entities.");
			
			if(! indexName.equals(IndexLocationFactory.DEFAULT_SINGLE_INDEX_NAME)) {
				EntityDao entityIndexDao = 
					this.indexDaoManager.getEntityDao(reference.getCodingSchemeURN(), reference.getCodingSchemeVersion());
				
				this.getLogger().info("In multi-directory index mode, optimizing...");
				entityIndexDao.optimizeIndex(reference.getCodingSchemeURN(), reference.getCodingSchemeVersion());
				this.getLogger().info("Optimizing complete.");
			}
		}
		
		return indexName;
	}

	/**
	 * Adds the index metadata.
	 * 
	 * @param reference the reference
	 * @param indexName the index name
	 * @param indexVersion the index version
	 */
	protected void addIndexMetadata(
			AbsoluteCodingSchemeVersionReference reference, String indexName, String indexVersion) {
		try {	  
			String codingSchemeName = 
				systemResourceService.getInternalCodingSchemeNameForUserCodingSchemeName(reference.getCodingSchemeURN(), reference.getCodingSchemeVersion());

			metaData.setIndexMetaDataValue(codingSchemeName + "[:]" + reference.getCodingSchemeVersion(), indexName);

			metaData.setIndexMetaDataValue(indexName, "lgModel", indexVersion);
			metaData.setIndexMetaDataValue(indexName, "has 'Norm' fields", false + "");
			metaData.setIndexMetaDataValue(indexName, "has 'Double Metaphone' fields", true + "");
			metaData.setIndexMetaDataValue(indexName, "indexing started", "");
			metaData.setIndexMetaDataValue(indexName, "indexing finished", "");

			metaData.rereadFile(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String getIndexName(AbsoluteCodingSchemeVersionReference reference) {
		if(systemVariables.getIsSingleIndex()){
			return IndexLocationFactory.DEFAULT_SINGLE_INDEX_NAME;
		} else {
			return UUID.randomUUID().toString();
		}
	}

	/**
	 * Sets the entity service.
	 * 
	 * @param entityService the new entity service
	 */
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	/**
	 * Gets the entity service.
	 * 
	 * @return the entity service
	 */
	public EntityService getEntityService() {
		return entityService;
	}

	/**
	 * Gets the batch size.
	 * 
	 * @return the batch size
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * Sets the batch size.
	 * 
	 * @param batchSize the new batch size
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * Sets the system resource service.
	 * 
	 * @param systemResourceService the new system resource service
	 */
	public void setSystemResourceService(SystemResourceService systemResourceService) {
		this.systemResourceService = systemResourceService;
	}

	/**
	 * Gets the system resource service.
	 * 
	 * @return the system resource service
	 */
	public SystemResourceService getSystemResourceService() {
		return systemResourceService;
	}

	/**
	 * Gets the system variables.
	 * 
	 * @return the system variables
	 */
	public SystemVariables getSystemVariables() {
		return systemVariables;
	}

	/**
	 * Sets the system variables.
	 * 
	 * @param systemVariables the new system variables
	 */
	public void setSystemVariables(SystemVariables systemVariables) {
		this.systemVariables = systemVariables;
	}

	

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public void setLogger(LgLoggerIF logger) {
		this.logger = logger;
	}

	public LgLoggerIF getLogger() {
		return logger;
	}

	public EntityIndexer getEntityIndexer() {
		return entityIndexer;
	}

	public void setEntityIndexer(EntityIndexer entityIndexer) {
		this.entityIndexer = entityIndexer;
	}

	public EntityIndexer getSearchIndexer() {
		return searchIndexer;
	}

	public void setSearchIndexer(EntityIndexer searchIndexer) {
		this.searchIndexer = searchIndexer;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setIndexDaoManager(IndexDaoManager indexDaoManager) {
		this.indexDaoManager = indexDaoManager;
	}

	public IndexDaoManager getIndexDaoManager() {
		return indexDaoManager;
	}
}
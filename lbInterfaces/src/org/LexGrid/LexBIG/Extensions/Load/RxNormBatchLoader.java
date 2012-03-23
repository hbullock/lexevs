package org.LexGrid.LexBIG.Extensions.Load;

import java.net.URI;

import org.LexGrid.LexBIG.Exceptions.LBParameterException;

public interface RxNormBatchLoader extends SpringBatchLoader {
	public String NAME = "RxNormBatchLoader";
	public String VERSION = "1.1";
	public String DESCRIPTION = "RXNORM Spring Batch Loader";
	
	/**
	 * Load meta.
	 * 
	 * @param rrfDir the rrf dir
	 * 
	 * @return the job execution
	 * 
	 * @throws Exception the exception
	 */
	public void loadRXNORM(URI rrfDir) throws Exception;

	/**
	 * Resume meta.
	 * 
	 * @param rrfDir the rrf dir
	 * @param uri the uri
	 * @param version the version
	 * 
	 * @return the job execution
	 * 
	 * @throws Exception the exception
	 */
	public void resumeRXNORM(URI rrfDir, String uri, String version) throws Exception;

	public void removeLoad(String uri, String version) throws LBParameterException;	
}
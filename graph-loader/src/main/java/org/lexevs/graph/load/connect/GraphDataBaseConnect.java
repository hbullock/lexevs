package org.lexevs.graph.load.connect;

import java.util.List;

import org.lexevs.dao.database.access.association.model.Triple;
import org.lexevs.dao.database.access.association.model.graphdb.GraphDbTriple;

import com.orientechnologies.orient.core.db.graph.OGraphDatabase;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

public interface GraphDataBaseConnect {
	
	public OGraphDatabase openForWrite(String dbName);
	public OGraphDatabase openForRead(String dbName);
	public OGraphDatabase getGraphDbFromPool(String dbPath, String username, String password);
	public void close(OGraphDatabase database);
	public void delete(String dbName);
	public OClass createVertexTable(String table, List<String> fieldnames);
	public OClass createEdgeTable(String table, List<String> fieldnames);
	public Object storeVertex(String table, String entityCode, String entityNamespace);
	void storeGraphTriple(GraphDbTriple triple, String vertexTableName,
			String edgeTableName);	
	public ODocument getVertexForCode(String code, String vertexTableName);
	void storeGraphTriple(GraphDbTriple triple, String vertexTableName);
}

package org.cts2.internal.mapper;

import static org.junit.Assert.assertEquals;

import org.LexGrid.commonTypes.Text;
import org.cts2.entity.EntityDirectoryEntry;
import org.junit.Before;
import org.junit.Test;

public class DefinitionToDefinitionTest extends BaseDozerBeanMapperTest{
	private org.LexGrid.concepts.Definition lgDef;
	private org.cts2.core.Definition ctsDef;
	
	@Before
	public void initialize() {
		lgDef = new org.LexGrid.concepts.Definition();
		lgDef.setPropertyId("test propertyid");
		lgDef.setLanguage("test language");
		Text t = new Text();
		t.setDataType("string");
		t.setContent("content");
		lgDef.setValue(t);
		
		ctsDef = baseDozerBeanMapper.map(lgDef, org.cts2.core.Definition.class);
//		ctsDef.setAssertedByCodeSystemVersion(assertedByCodeSystemVersion)
//		ctsDef.setAssertedInCodeSystemVersion(assertedInCodeSystemVersion)
//		ctsDef.setCorrespondingStatement(correspondingStatement)
//		ctsDef.setDefinitionRole(definitionRole)
//		ctsDef.setExternalIdentifier(externalIdentifier) 	done
//		ctsDef.setFormat(format)
//		ctsDef.setLanguage(language) 	done
//		ctsDef.setSchema(schema)
//		ctsDef.setUsageContext(usageContext)
//		ctsDef.setValue(value)  	done
	}
	
	@Test
	public void testGetPropertyId(){
		assertEquals("test propertyid", ctsDef.getExternalIdentifier());
	}
	
	@Test
	public void testGetLanguage() {
		assertEquals("test language", ctsDef.getLanguage().getContent());
	}
	
	@Test
	public void testGetValue() {
		assertEquals("content", ctsDef.getValue());
	}
	
	@Test
	public void testGetDataType() {
		assertEquals("string", ctsDef.getFormat().getContent());
	}

}
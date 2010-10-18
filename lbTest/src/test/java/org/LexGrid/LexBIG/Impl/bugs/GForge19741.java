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
package org.LexGrid.LexBIG.Impl.bugs;

import org.LexGrid.LexBIG.Impl.function.LexBIGServiceTestCase;
import org.LexGrid.LexBIG.Impl.testUtility.ServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;

/**
 * This class should be used as a place to write JUnit tests which show a bug,
 * and pass when the bug is fixed.
 * 
 * @author <A HREF="mailto:kevin.peterson@mayo.edu">Kevin Peterson</A>
 */
public class GForge19741 extends LexBIGServiceTestCase {
    final static String testID = "GForge19741";

    private CodedNodeGraph cng;
    
    @Override
    protected String getTestID() {
        return testID;
    }

    /*
     * LexBIG Bug #19741 -
     * https://gforge.nci.nih.gov/tracker/index.php?func=detail&aid=19741&group_id=491&atid=1850
     */
    public void setUp(){
        try {
            LexBIGService lbsi = ServiceHolder.instance().getLexBIGService();      
            cng = lbsi.getNodeGraph("Automobiles", null, "relations");    
        } catch (Exception e) {
          fail(e.getMessage());
        }
    }
 
    public void testNonTransitiveRelationship() throws Exception {
        // A couple of non-transitive tests -- just to make sure we have things set up right
        assertTrue(cng.areCodesRelated(Constructors.createNameAndValue("hasSubtype", null),
                Constructors.createConceptReference("005", "Automobiles"),
                Constructors.createConceptReference("Ford", "urn:oid:11.11.0.1"), true).booleanValue());
    }
   
    public void testTransitiveRelationshipDirectOnlyFalse() throws Exception {
        // now test something that requires transitivity
        assertTrue(cng.areCodesRelated(Constructors.createNameAndValue("hasSubtype", null),
                Constructors.createConceptReference("005", "Automobiles"),
                Constructors.createConceptReference("Jaguar", "urn:oid:11.11.0.1"), false).booleanValue());
    }
    
    public void testTransitiveRelationshipDirectOnlyTrue() throws Exception {
        // This should fail because it is not a direct relationship -- but the 'directOnly' flag is set to true;
        assertFalse(cng.areCodesRelated(Constructors.createNameAndValue("hasSubtype", null),
                Constructors.createConceptReference("005", "Automobiles"),
                Constructors.createConceptReference("Jaguar", "urn:oid:11.11.0.1"), true).booleanValue());
    }   
}
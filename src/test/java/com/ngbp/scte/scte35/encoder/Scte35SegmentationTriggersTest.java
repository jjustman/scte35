package com.ngbp.scte.scte35.encoder;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ngbp.scte.scte35.Scte35ModuleConfig;
import com.ngbp.scte.scte35.decoder.Scte35Decoder;

/**
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Scte35ModuleConfig.class })
public class Scte35SegmentationTriggersTest {

    private static final Logger LOG = Logger.getLogger(Scte35SegmentationTriggersTest.class);

    @Test
	public void createCoreTriggersWithWrapper() throws Exception {
		
    	String myUUID = UUID.randomUUID().toString();
    	
    	//by default, coerce the first - to segmentation_event_id
		String myProgramTitle = "Paddy's Pub: Home of the Original Kitten Mittens";
		int segmentNum = 4;

		
		String programStart = Scte35SegmentationTriggers.createProgramStart(myUUID, myProgramTitle);
		String adBreakStart = Scte35SegmentationTriggers.createDistributorAdvertisementStart(myUUID, myProgramTitle, segmentNum);
		String adBreakEnd = Scte35SegmentationTriggers.createDistributorAdvertisementEnd(myUUID, myProgramTitle, segmentNum);
		String programEnd = Scte35SegmentationTriggers.createProgramEnd(myUUID, myProgramTitle);
				
		Scte35Decoder decoder = new Scte35Decoder();
		LOG.debug("programStart");
		decoder.base64Decode(programStart);
		
		LOG.debug("adBreakStart");
		decoder.base64Decode(adBreakStart);
		
		LOG.debug("adBreakEnd");
		decoder.base64Decode(adBreakEnd);
		
		LOG.debug("programEnd");
		decoder.base64Decode(programEnd);
		
		
	}

}
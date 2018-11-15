package com.ngbp.scte.scte35.encoder;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.springframework.stereotype.Component;

import com.ngbp.scte.scte35.encoder.marshaller.SpliceInfoSectionMarshaller;
import com.ngbp.scte.scte35.encoder.model.SpliceInfoSection;

/**
 * Implements a SCTE35 encoding mechanism.
 * 
 * 
 * https://support.google.com/admanager/answer/7506166?hl=en
 * 
 * 
 * For time_signal,  the unique_program_id (converted to a string) 
 * 
 */

@Component
public class Scte35Encoder {

    private static final Logger LOG = Logger.getLogger(Scte35Encoder.class);

	public String encodeToBase64(SpliceInfoSection spliceInfoSection) throws Exception {
		NDC.push(String.format("Scte35Encoder.txnId: %s", UUID.randomUUID().toString()));
		LOG.debug(String.format("marshalling message for commandType: %s", spliceInfoSection.spliceCommandType.getByteRawValue()));
		SpliceInfoSectionMarshaller spliceInfoSectionMarshaller = new SpliceInfoSectionMarshaller();
		spliceInfoSectionMarshaller.setSpliceInfoSection(spliceInfoSection);

		String myBase64Payload = spliceInfoSectionMarshaller.marshallToBase64();
		NDC.pop();
		
		return myBase64Payload;
	} 
}
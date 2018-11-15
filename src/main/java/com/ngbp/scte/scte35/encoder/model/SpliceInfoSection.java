package com.ngbp.scte.scte35.encoder.model;

import com.ngbp.scte.scte35.utils.BitField;
import com.ngbp.scte.scte35.utils.LongBitField;
import com.ngbp.scte.scte35.utils.UnsignedInteger;

/**
 * 2018-09-27 - jjustman - re-mapping from wrong datasizes to match SCTE-35 spec
 * 
 * UnsignedInteger:  https://google.github.io/guava/releases/20.0/api/docs/com/google/common/primitives/UnsignedInteger.html
 * bitfield: https://commons.apache.org/proper/commons-lang/javadocs/api-3.4/org/apache/commons/lang3/BitField.html
 * 
 *    Unless otherwise specified, all RESERVED bits
shall be set to ‘1’ and this field shall be ignored by receiving
equipment. 
 * Table 5 - splice_info_section()
Syntax Bits Mnemonic Encrypted
splice_info_section() {

table_id 						8 uimsbf	Bitmask: 1111 1111 = 0xFF 				value always 0xFC

 //16 bits in total alignment
 section_syntax_indicator 		1 bslbf 	Bitmask: 1000 0000 0000 0000 = 0x8000	value always 0
 private_indicator 				1 bslbf		Bitmask: 0100 0000 0000 0000 = 0x4000	value always 0
 reserved 						2 bslbf		Bitmask: 0011 0000 0000 0000 = 0x3000	value always 0	
 section_length 			   12 uimsbf	Bitmask  0000 1111 1111 1111 = 0x0FFF	This is a 12-bit field specifying the number of remaining bytes in the
																					splice_info_section immediately following the section_length field up to the end of the
																					splice_info_section. The value in this field shall not exceed 4093. 
 
 
protocol_version 				8 uimsbf	Bitmask: 1111 1111 = 0xFF				value always 0

//1+6+33 = 40 bits = 5 bytes
 encrypted_packet 				1 bslbf		Bitmask: 1000 0000 0000 0000 0000 0000 0000 0000 0000 0000 = 0x8000000000	value 0 if not encrypted
 encryption_algorithm 			6 uimsbf	Bitmask: 0111 1110 0000 0000 0000 0000 0000 0000 0000 0000 = 0x7E00000000	value 0 / "undefined" if encryption packet is 0
 pts_adjustment 			   33 uimsbf	Bitmask: 0000 0001 1111 1111 1111 1111 1111 1111 1111 1111 = 0x1FFFFFFFFF	value 0 for no pts adjustment

 
cw_index 						8 uimsbf	Bitmask: 1111 1111 = 0xFF 				value always 0

//12+12+8 = 32
 tier 						   12 bslbf		Bitmask: 1111 1111 1111 0000 0000 0000 0000 0000 = 0xFFF00000
 splice_command_length 		   12 uimsbf	Bitmask: 0000 0000 0000 1111 1111 1111 0000 0000 = 0x000FFF00
 splice_command_type 			8 uimsbf 	Bitmask: 0000 0000 0000 0000 0000 0000 1111 1111 = 0x000000FF
 	
splice_null 			0x00 SpliceNull
Reserved 				0x01
Reserved 				0x02 
Reserved 				0x03
splice_schedule			0x04 SpliceSchedule
splice_insert 			0x05 SpliceInsert
time_signal 			0x06 TimeSignal
bandwidth_reservation 	0x07 BandwidthReservation
Reserved 				0x08 - 0xfe
private_command 		0xff 

	
	splice_command_length – a 12 bit length of the splice command. The length shall represent the number
	of bytes following the splice_command_type up to but not including the descriptor_loop_length. Devices
	that are compliant with this version of the standard shall populate this field with the actual length. The
	value of 0xFFF provides backwards compatibility and shall be ignored by downstream equipment. 

if(splice_command_type == 0x00)
 splice_null() E
if(splice_command_type == 0x04)
 splice_schedule() E
if(splice_command_type == 0x05)
 splice_insert() E
if(splice_command_type == 0x06)
 time_signal() E
if(splice_command_type == 0x07)
 bandwidth_reservation() E
if(splice_command_type == 0xff)
 private_command() E
 
descriptor_loop_length 		   16 uimsbf 	Bitmask: 1111 1111 1111 1111 = 0xFFFF

for(i=0; i<N1; i++)
 splice_descriptor() E
for(i=0; i<N2; i++)
 alignment_stuffing 			8 bslbf E
 if(encrypted_packet)
 E_CRC_32 					   32 rpchof E
 
 CRC_32 					   32 rpchof 
 
 */
public class SpliceInfoSection {

	//byte
    public UnsignedInteger tableID = 			UnsignedInteger.fromIntBits(0xFC, 8);
  
    //map this to a short, 2 bytes
    public BitField sectionSyntaxIndicator = 	new BitField(0x8000) {{ set(0); }};
    public BitField privateIndicator = 			new BitField(0x4000) {{ set(0); }};
    public BitField reserved1 = 				new BitField(0x3000) {{ set(0x3); }};
    public BitField sectionLength = 			new BitField(0x0FFF) {{ set(0); }};
    
    //byte
    public UnsignedInteger protocolVersion =	UnsignedInteger.fromIntBits(0x00, 8);
    
    //40 bits
    public LongBitField encryptedPacket =		new LongBitField(0x8000000000L) {{ set(0); }};
    public LongBitField encryptionAlgorithm = 	new LongBitField(0x7E00000000L) {{ set(0); }};;
    public LongBitField ptsAdjustment = 		new LongBitField(0x1FFFFFFFFFL) {{ set(0); }};;
    
    //8 bits
    public UnsignedInteger cwIndex =			UnsignedInteger.fromIntBits(0x00, 8);
    
    //32 bits
    public BitField tier = 						new BitField(0xFFF00000) {{ set(0xFFF); }};;
    public BitField spliceCommandLength =		new BitField(0x000FFF00) {{ set(0); }};;
    /**The length shall represent the number of bytes following the splice_command_type up to but not including the descriptor_loop_length. **/
    public BitField spliceCommandType = 		new BitField(0x000000FF) {{ set(0); }};;
    
    public static final BitField SPLICE_NULL = 				new BitField(0x000000FF) {{ set(0); }};
    public static final BitField SPLICE_SCHEDULE = 			new BitField(0x000000FF) {{ set(4); }};
    public static final BitField SPLICE_INSERT =			new BitField(0x000000FF) {{ set(5); }};
    public static final BitField TIME_SIGNAL = 				new BitField(0x000000FF) {{ set(6); }};
    public static final BitField BANDWIDTH_RESERVATION = 	new BitField(0x000000FF) {{ set(7); }};
    
    //inner structs keyed off of spliceCommandType here
    
    SpliceInsert spliceInsert;       		
    public SpliceInsert getSpliceInsert() {
		return spliceInsert;
	}
	public void setSpliceInsert(SpliceInsert spliceInsert) {
		this.spliceInsert = spliceInsert;
	}
	TimeSignal timeSignal;
	public TimeSignal getTimeSignal() {
		return timeSignal;
	}
	public void setTimeSignal(TimeSignal timeSignal) {
		this.timeSignal = timeSignal;
	}
    
    //end of inner structs

	public UnsignedInteger descriptorLoopLength = 		UnsignedInteger.fromIntBits(0x00, 16);
    //read splice_descriptor N times based upon len
    
    
    //model aggregation
    public SpliceDescriptor spliceDescriptor;
    // SegmentationDescriptor is a SpliceDescriptor

    
    public SpliceDescriptor getSpliceDescriptor() {
		return spliceDescriptor;
	}

	public void setSpliceDescriptor(SpliceDescriptor spliceDescriptor) {
		this.spliceDescriptor = spliceDescriptor;
	}

	//not used if encryptedPacket = 0?
    public int alignmentStuffing; 
    public int eCRC32;
    //end not used if encryptedPacket = 0;
    
    //32 bits
    public int CRC32 = -0;
    
//  public SpliceInsert spliceInsert;

    @Override
    public String toString() {
        return "SpliceInfoSection{" +
                "tableID=" + tableID +
                ", sectionSyntaxIndicator=" + sectionSyntaxIndicator +
                ", privateIndicator=" + privateIndicator +
                ", reserved1=" + reserved1 +
                ", sectionLength=" + sectionLength +
                ", protocolVersion=" + protocolVersion +
                ", encryptedPacket=" + encryptedPacket +
                ", encryptionAlgorithm=" + encryptionAlgorithm +
                ", ptsAdjustment=" + ptsAdjustment +
                ", cwIndex=" + cwIndex +
                ", tier=" + tier +
                ", spliceCommandLength=" + spliceCommandLength +
                ", spliceCommandType=" + spliceCommandType +
                ", descriptorLoopLength=" + descriptorLoopLength +
                ", alignmentStuffing=" + alignmentStuffing +
                ", eCRC32=" + eCRC32 +
                ", CRC32=" + CRC32 +
//                ", spliceInsert=" + spliceInsert +
                '}';
    }
}

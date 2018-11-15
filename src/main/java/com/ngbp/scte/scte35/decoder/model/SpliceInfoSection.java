package com.ngbp.scte.scte35.decoder.model;

/**
 * Created by andres.aguilar on 6/16/16.
 */
public class SpliceInfoSection {

    public int tableID;
    public int sectionSyntaxIndicator;
    public int privateIndicator;
    public int reserved1;
    public int sectionLength;
    public int protocolVersion;
    public int encryptedPacket;
    public int encryptionAlgorithm;
    public long ptsAdjustment;
    public int cwIndex;
    public int tier;
    public int spliceCommandLength;
    public int spliceCommandType;
    public int descriptorLoopLength;
    public int alignmentStuffing;
    public int eCRC32;
    public int CRC32;
    public SpliceInsert spliceInsert;

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
                ", spliceInsert=" + spliceInsert +
                '}';
    }
}

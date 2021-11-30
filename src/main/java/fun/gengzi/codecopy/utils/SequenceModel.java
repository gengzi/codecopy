package fun.gengzi.codecopy.utils;

import java.io.Serializable;

public class SequenceModel implements Serializable {
    private static final long serialVersionUID = 6600315135422457571L;
    private String sequenceName;
    private long seqCurrentVal;
    private SnowflakeIdWorker idWorker;
    private final long sequenceMask = 4095L;

    public SequenceModel() {
    }

    public long getNextValue() {
        return this.idWorker.nextId();
    }

    public SnowflakeIdWorker getIdWorker() {
        return this.idWorker;
    }

    public void createIdWorker() {
        this.idWorker = new SnowflakeIdWorker((long)this.workId(), (long)this.datacenterId());
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public long getSeqCurrentVal() {
        return this.seqCurrentVal;
    }

    public void setSeqCurrentVal(long seqCurrentVal) {
        this.seqCurrentVal = seqCurrentVal;
    }

    private int workId() {
        int hash = this.sequenceName.hashCode();
        int mathValue = MathUtils.getValue(31);
        Long longValue = (long)(hash * mathValue) * 4095L;
        longValue = longValue % 31L;
        return MathUtils.positive(longValue.intValue());
    }

    private int datacenterId() {
        return 31;
    }
}

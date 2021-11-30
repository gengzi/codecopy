package fun.gengzi.codecopy.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

public class SequenceUtil {

    private static SequenceUtil instance = null;
    private static ConcurrentHashMap<String, SequenceModel> sequenceMap = new ConcurrentHashMap(500);

    public SequenceUtil() {
    }

    public static SequenceUtil create() {
        if (instance == null) {
            Class var0 = SequenceUtil.class;
            synchronized (SequenceUtil.class) {
                if (instance == null) {
                    instance = new SequenceUtil();
                }
            }
        }

        return instance;
    }

    public long sequenceNextVal(Class<?> sequenceModel) throws IllegalArgumentException {
        if (sequenceModel == null) {
            throw new IllegalArgumentException("sequenceClass is null");
        } else {
            String className = sequenceModel.getSimpleName();
            if (StringUtils.isBlank(className)) {
                throw new IllegalArgumentException("sequenceName is null");
            } else {
                long nextId = this.sequenceNextVal(className);
                return nextId;
            }
        }
    }

    public long sequenceNextVal(String sequenceName) {
        SequenceModel seqObject = (SequenceModel) sequenceMap.get(sequenceName);
        if (seqObject == null) {
            seqObject = (new SequenceHandle(sequenceName, seqObject)).getSequenceModel();
            sequenceMap.putIfAbsent(sequenceName, seqObject);
        }

        return seqObject.getNextValue();
    }
}

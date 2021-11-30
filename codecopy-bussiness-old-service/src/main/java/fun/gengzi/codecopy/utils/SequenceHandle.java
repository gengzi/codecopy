package fun.gengzi.codecopy.utils;

public class SequenceHandle {
    private String sequenceName;
    private SequenceModel sequenceModel;

    public SequenceHandle(String sequenceName, SequenceModel sequenceModel) {
        this.sequenceName = sequenceName;
        this.sequenceModel = sequenceModel;
    }

    public void updateModel() {
        if (this.sequenceModel == null) {
            this.sequenceModel = new SequenceModel();
            this.sequenceModel.setSequenceName(this.sequenceName);
            this.sequenceModel.createIdWorker();
        }

    }

    public SequenceModel getSequenceModel() {
        if (this.sequenceModel == null) {
            this.updateModel();
        }

        return this.sequenceModel;
    }
}

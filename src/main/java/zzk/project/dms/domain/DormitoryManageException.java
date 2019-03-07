package zzk.project.dms.domain;

public class DormitoryManageException extends RuntimeException {
    public DormitoryManageException() {
        super();
    }

    public DormitoryManageException(String message) {
        super(message);
    }

    public DormitoryManageException(String message, Throwable cause) {
        super(message, cause);
    }

    public DormitoryManageException(Throwable cause) {
        super(cause);
    }

    protected DormitoryManageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}

package zzk.project.dms.utilies;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {
    public static final int DEFAULT_PAGE_SIZE = 8;

    public static class OffsetAndLimit {
        private int offset;
        private int limit;

        public OffsetAndLimit(int offset, int limit) {
            this.offset = offset;
            this.limit = limit;
        }

        public int getOffset() {
            return offset;
        }

        public int getLimit() {
            return limit;
        }
    }

    public static Pageable toPageable(int offset, int limit) {
        int total = offset + limit;
        int pageNumber = total / DEFAULT_PAGE_SIZE;
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        return PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE);
    }

    public static OffsetAndLimit toOffsetAndLimit(int page) {
        OffsetAndLimit offsetAndLimit;
        if (page == 1) {
            offsetAndLimit = new OffsetAndLimit(0, DEFAULT_PAGE_SIZE);
        }else {
            offsetAndLimit = new OffsetAndLimit(page * DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SIZE);
        }
        return offsetAndLimit;
    }
}

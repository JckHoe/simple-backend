package com.tribehired.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BasePaginationResponse {
    private boolean moreRecordsIndicator;
    private int totalRecords;
    private int recordPerPage;
    private int pageNo;
}

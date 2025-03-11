package com.silvermoongroup.fsa.web.account;

import com.silvermoongroup.businessservice.financialmanagement.dto.AccountEntryDTO;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import java.util.List;

/**
 * Created by koen on 04.11.15.
 */
public class FindAccountEntriesList implements PaginatedList {

    List<AccountEntryDTO> list;
    private int pageNumber;
    private int objectsPerPage;
    private int fullListSize;


    @Override
    public List getList() {
        return list;
    }

    public void setList(
            List<AccountEntryDTO> partialAccountEntryList) {
        this.list = partialAccountEntryList;
    }

    public List<AccountEntryDTO> getPartialAccountEntryList() {
        return list;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public int getObjectsPerPage() {
        return objectsPerPage;
    }

    public void setObjectsPerPage(int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }

    @Override
    public int getFullListSize() {
        return fullListSize;
    }

    public void setFullListSize(int fullListSize) {
        this.fullListSize = fullListSize;
    }

    @Override
    public String getSortCriterion() {
        return null;
    }

    @Override
    public SortOrderEnum getSortDirection() {
        return null;
    }

    @Override
    public String getSearchId() {
        return null;
    }
}

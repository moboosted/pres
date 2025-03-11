/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.request;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementRequestSearchResultDTO;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import java.util.List;

/**
 * Created by damir on 12/15/2015.
 */
public class RequestSearchList implements PaginatedList {

    List<AgreementRequestSearchResultDTO> list;
    private int pageNumber;
    private int objectsPerPage;
    private int fullListSize;
    private String sortCriterion;
    private SortOrderEnum sortDirection = SortOrderEnum.ASCENDING;

    public RequestSearchList() {
    }

    @Override
    public List getList() {
        return list;
    }

    public void setList(List<AgreementRequestSearchResultDTO> list) {
        this.list = list;
    }

    public List<AgreementRequestSearchResultDTO> getPartialSearchRequestList() {
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

    public void setFullListSize(int fullListSize) { this.fullListSize = fullListSize; }

    @Override
    public String getSortCriterion() {
        return sortCriterion;
    }

    public void setSortCriterion(String sortCriterion) {
        this.sortCriterion = sortCriterion;
    }

    @Override
    public SortOrderEnum getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortOrderEnum sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public String getSearchId() {
        return null;
    }
}

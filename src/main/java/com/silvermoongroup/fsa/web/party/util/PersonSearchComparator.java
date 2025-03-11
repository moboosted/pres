package com.silvermoongroup.fsa.web.party.util;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;

import java.util.Comparator;

public class PersonSearchComparator implements Comparator<PartySearchResultVO> {
    private static final int ASCENDING = 1;
    private static final int DESCENDING = 2;
    private static final String SURNAME_SORTNAME = "surname";
    private static final String FIRSTNAME_SORTNAME = "firstName";
    private static final String MIDDLENAME_SORTNAME = "middlename";
    private static final String BIRTHDATE_SORTNAME = "birthDate";

    private final String columnName;
    private final int sortOrder;
    private final ITypeParser typeParser;

    public PersonSearchComparator(ITypeParser typeParser) {
        this(SURNAME_SORTNAME, ASCENDING, typeParser);
    }

    public PersonSearchComparator(String columnName, int sortOrder, ITypeParser typeParser) {
        this.columnName = columnName;
        this.sortOrder = sortOrder;
        this.typeParser = typeParser;
    }

    @Override
    public int compare(PartySearchResultVO o1, PartySearchResultVO o2) {

        int result = 0;
        if (getColumnName().equalsIgnoreCase(BIRTHDATE_SORTNAME)) {
            Date o1Date = typeParser.parseDate(o1.getBirthDate());
            Date o2Date = typeParser.parseDate(o2.getBirthDate());
            result = o1Date.compareTo(o2Date);
        } else if (getColumnName().equalsIgnoreCase(FIRSTNAME_SORTNAME)) {
            result = o1.getFirstname().compareToIgnoreCase(o2.getFirstname());
        } else if (getColumnName().equalsIgnoreCase(MIDDLENAME_SORTNAME)) {
            result = o1.getMiddlename().compareToIgnoreCase(o2.getMiddlename());
        } else {
            String surnameFirstNameSortO1 = (o1.getSurname() + o1.getFirstname());
            String surnameFirstNameSortO2 = (o2.getSurname() + o2.getFirstname());
            result = surnameFirstNameSortO1.compareToIgnoreCase(surnameFirstNameSortO2);
            //result = o1.getSurname().compareToIgnoreCase(o2.getSurname());
        }
        if (getSortOrder() == DESCENDING) {
            result = negate(result);
        }
        return result;
    }

    private String getColumnName() {
        return columnName;
    }

    private int getSortOrder() {
        return sortOrder;
    }

    private int negate(int result) {
        return (-1 * result);
    }
}
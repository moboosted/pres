/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.util;

import java.io.Serializable;


public class PostalCdComposite implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String countryCd;
    private String regionCityTownCd;
    private String regionCityTown;
    private String postalCd;

    /**
     * Default Constructor for Hibernate
     */
    public PostalCdComposite() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }


    public String getRegionCityTownCd() {
        return regionCityTownCd;
    }

    public void setRegionCityTownCd(String regionCityTownCd) {
        this.regionCityTownCd = regionCityTownCd;
    }


    public String getRegionCityTown() {
        return regionCityTown;
    }

    public void setRegionCityTown(String regionCityTown) {
        this.regionCityTown = regionCityTown;
    }


    public String getPostalCd() {
        return postalCd;
    }

    public void setPostalCd(String postalCd) {
        this.postalCd = postalCd;
    }

}
package com.g12.faunalencyclopedia.Data;

import java.util.List;
/**
 * @author UID:u7630167 Name: Yihang Zhu
 *         UID:u7574003 Name: Andy Chih
 */
public class Animal implements java.io.Serializable{
    private String id;
    private String county;
    //String category;
    private String taxonomic_group;
    private String taxonomic_subgroup;
    private String scientific_name;
    private String common_name;
    private String year_last_documented;
    //String ny_listing_status;
    private String federal_listing_status;
    //String state_conservation_rank;
    private String global_conservation_rank;
    //String distribution_status;
    private List<String> data;

    public Animal(List<String> datalist){
        this.id = datalist.get(1);
        this.county = datalist.get(8);
        //this.category = datalist.get(9);
        this.taxonomic_group = datalist.get(10);
        this.taxonomic_subgroup = datalist.get(11);
        this.scientific_name = datalist.get(12);
        this.common_name = datalist.get(13);
        this.year_last_documented = datalist.get(14);
        //this.ny_listing_status = datalist.get(15);
        this.federal_listing_status = datalist.get(16);
        //this.state_conservation_rank = datalist.get(17);
        this.global_conservation_rank = datalist.get(18);
        //this.distribution_status = datalist.get(19);
        this.data = data;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "county='" + county + '\'' +
                //", category='" + category + '\'' +
                ", taxonomic_group='" + taxonomic_group + '\'' +
                ", taxonomic_subgroup='" + taxonomic_subgroup + '\'' +
                ", scientific_name='" + scientific_name + '\'' +
                ", common_name='" + common_name + '\'' +
                ", year_last_documented=" + year_last_documented +
                //", ny_listing_status='" + ny_listing_status + '\'' +
                ", federal_listing_status='" + federal_listing_status + '\'' +
                //", state_conservation_rank='" + state_conservation_rank + '\'' +
                ", global_conservation_rank='" + global_conservation_rank + '\'' +
                //", distribution_status='" + distribution_status + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTaxonomic_group() {
        return taxonomic_group;
    }

    public void setTaxonomic_group(String taxonomic_group) {
        this.taxonomic_group = taxonomic_group;
    }

    public String getTaxonomic_subgroup() {
        return taxonomic_subgroup;
    }

    public void setTaxonomic_subgroup(String taxonomic_subgroup) {
        this.taxonomic_subgroup = taxonomic_subgroup;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public void setScientific_name(String scientific_name) {
        this.scientific_name = scientific_name;
    }

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }

    public String getYear_last_documented() {
        return year_last_documented;
    }

    public void setYear_last_documented(String year_last_documented) {
        this.year_last_documented = year_last_documented;
    }

    public String getFederal_listing_status() {
        return federal_listing_status;
    }

    public void setFederal_listing_status(String federal_listing_status) {
        this.federal_listing_status = federal_listing_status;
    }

    public String getGlobal_conservation_rank() {
        return global_conservation_rank;
    }

    public void setGlobal_conservation_rank(String global_conservation_rank) {
        this.global_conservation_rank = global_conservation_rank;
    }

    public int getYear() {
        try {
            return Integer.parseInt(year_last_documented);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int compareTo(Animal otherAnimal) {
        // Compare animals based on some criteria relevant to your application
        // Return a negative value if this animal is less than the otherAnimal,
        // 0 if they are equal, and a positive value if this animal is greater.
        // For simplicity, let's just compare based on the animal names.
        return this.data.get(4).compareTo(otherAnimal.data.get(4));
    }
}

package org.opensrp.domain.postgres;

import java.util.Date;

public class ClientForm {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.client_forms.id
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.client_forms.json
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    private Object json;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.client_forms.created_at
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    private Date createdAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.client_forms.id
     *
     * @return the value of core.client_forms.id
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.client_forms.id
     *
     * @param id the value for core.client_forms.id
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.client_forms.json
     *
     * @return the value of core.client_forms.json
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    public Object getJson() {
        return json;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.client_forms.json
     *
     * @param json the value for core.client_forms.json
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    public void setJson(Object json) {
        this.json = json;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.client_forms.created_at
     *
     * @return the value of core.client_forms.created_at
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.client_forms.created_at
     *
     * @param createdAt the value for core.client_forms.created_at
     *
     * @mbg.generated Fri Aug 16 14:03:07 EAT 2019
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
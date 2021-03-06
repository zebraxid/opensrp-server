package org.opensrp.domain.postgres;

import java.util.ArrayList;
import java.util.List;

public class ViewConfigurationMetadataExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public ViewConfigurationMetadataExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdIsNull() {
            addCriterion("view_configuration_id is null");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdIsNotNull() {
            addCriterion("view_configuration_id is not null");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdEqualTo(Long value) {
            addCriterion("view_configuration_id =", value, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdNotEqualTo(Long value) {
            addCriterion("view_configuration_id <>", value, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdGreaterThan(Long value) {
            addCriterion("view_configuration_id >", value, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdGreaterThanOrEqualTo(Long value) {
            addCriterion("view_configuration_id >=", value, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdLessThan(Long value) {
            addCriterion("view_configuration_id <", value, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdLessThanOrEqualTo(Long value) {
            addCriterion("view_configuration_id <=", value, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdIn(List<Long> values) {
            addCriterion("view_configuration_id in", values, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdNotIn(List<Long> values) {
            addCriterion("view_configuration_id not in", values, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdBetween(Long value1, Long value2) {
            addCriterion("view_configuration_id between", value1, value2, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andViewConfigurationIdNotBetween(Long value1, Long value2) {
            addCriterion("view_configuration_id not between", value1, value2, "viewConfigurationId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdIsNull() {
            addCriterion("document_id is null");
            return (Criteria) this;
        }

        public Criteria andDocumentIdIsNotNull() {
            addCriterion("document_id is not null");
            return (Criteria) this;
        }

        public Criteria andDocumentIdEqualTo(String value) {
            addCriterion("document_id =", value, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdNotEqualTo(String value) {
            addCriterion("document_id <>", value, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdGreaterThan(String value) {
            addCriterion("document_id >", value, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdGreaterThanOrEqualTo(String value) {
            addCriterion("document_id >=", value, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdLessThan(String value) {
            addCriterion("document_id <", value, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdLessThanOrEqualTo(String value) {
            addCriterion("document_id <=", value, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdLike(String value) {
            addCriterion("document_id like", value, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdNotLike(String value) {
            addCriterion("document_id not like", value, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdIn(List<String> values) {
            addCriterion("document_id in", values, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdNotIn(List<String> values) {
            addCriterion("document_id not in", values, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdBetween(String value1, String value2) {
            addCriterion("document_id between", value1, value2, "documentId");
            return (Criteria) this;
        }

        public Criteria andDocumentIdNotBetween(String value1, String value2) {
            addCriterion("document_id not between", value1, value2, "documentId");
            return (Criteria) this;
        }

        public Criteria andIdentifierIsNull() {
            addCriterion("identifier is null");
            return (Criteria) this;
        }

        public Criteria andIdentifierIsNotNull() {
            addCriterion("identifier is not null");
            return (Criteria) this;
        }

        public Criteria andIdentifierEqualTo(String value) {
            addCriterion("identifier =", value, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierNotEqualTo(String value) {
            addCriterion("identifier <>", value, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierGreaterThan(String value) {
            addCriterion("identifier >", value, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierGreaterThanOrEqualTo(String value) {
            addCriterion("identifier >=", value, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierLessThan(String value) {
            addCriterion("identifier <", value, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierLessThanOrEqualTo(String value) {
            addCriterion("identifier <=", value, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierLike(String value) {
            addCriterion("identifier like", value, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierNotLike(String value) {
            addCriterion("identifier not like", value, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierIn(List<String> values) {
            addCriterion("identifier in", values, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierNotIn(List<String> values) {
            addCriterion("identifier not in", values, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierBetween(String value1, String value2) {
            addCriterion("identifier between", value1, value2, "identifier");
            return (Criteria) this;
        }

        public Criteria andIdentifierNotBetween(String value1, String value2) {
            addCriterion("identifier not between", value1, value2, "identifier");
            return (Criteria) this;
        }

        public Criteria andServerVersionIsNull() {
            addCriterion("server_version is null");
            return (Criteria) this;
        }

        public Criteria andServerVersionIsNotNull() {
            addCriterion("server_version is not null");
            return (Criteria) this;
        }

        public Criteria andServerVersionEqualTo(Long value) {
            addCriterion("server_version =", value, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionNotEqualTo(Long value) {
            addCriterion("server_version <>", value, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionGreaterThan(Long value) {
            addCriterion("server_version >", value, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("server_version >=", value, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionLessThan(Long value) {
            addCriterion("server_version <", value, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionLessThanOrEqualTo(Long value) {
            addCriterion("server_version <=", value, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionIn(List<Long> values) {
            addCriterion("server_version in", values, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionNotIn(List<Long> values) {
            addCriterion("server_version not in", values, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionBetween(Long value1, Long value2) {
            addCriterion("server_version between", value1, value2, "serverVersion");
            return (Criteria) this;
        }

        public Criteria andServerVersionNotBetween(Long value1, Long value2) {
            addCriterion("server_version not between", value1, value2, "serverVersion");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated do_not_delete_during_merge Mon Apr 23 10:29:52 EAT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table core.view_configuration_metadata
     *
     * @mbg.generated Mon Apr 23 10:29:52 EAT 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
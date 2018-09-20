package com.ylt.main.pojo;

import java.util.ArrayList;
import java.util.List;

public class PersonExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PersonExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andPnameIsNull() {
            addCriterion("pname is null");
            return (Criteria) this;
        }

        public Criteria andPnameIsNotNull() {
            addCriterion("pname is not null");
            return (Criteria) this;
        }

        public Criteria andPnameEqualTo(String value) {
            addCriterion("pname =", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotEqualTo(String value) {
            addCriterion("pname <>", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameGreaterThan(String value) {
            addCriterion("pname >", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameGreaterThanOrEqualTo(String value) {
            addCriterion("pname >=", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLessThan(String value) {
            addCriterion("pname <", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLessThanOrEqualTo(String value) {
            addCriterion("pname <=", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLike(String value) {
            addCriterion("pname like", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotLike(String value) {
            addCriterion("pname not like", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameIn(List<String> values) {
            addCriterion("pname in", values, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotIn(List<String> values) {
            addCriterion("pname not in", values, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameBetween(String value1, String value2) {
            addCriterion("pname between", value1, value2, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotBetween(String value1, String value2) {
            addCriterion("pname not between", value1, value2, "pname");
            return (Criteria) this;
        }

        public Criteria andPsexIsNull() {
            addCriterion("psex is null");
            return (Criteria) this;
        }

        public Criteria andPsexIsNotNull() {
            addCriterion("psex is not null");
            return (Criteria) this;
        }

        public Criteria andPsexEqualTo(String value) {
            addCriterion("psex =", value, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexNotEqualTo(String value) {
            addCriterion("psex <>", value, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexGreaterThan(String value) {
            addCriterion("psex >", value, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexGreaterThanOrEqualTo(String value) {
            addCriterion("psex >=", value, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexLessThan(String value) {
            addCriterion("psex <", value, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexLessThanOrEqualTo(String value) {
            addCriterion("psex <=", value, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexLike(String value) {
            addCriterion("psex like", value, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexNotLike(String value) {
            addCriterion("psex not like", value, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexIn(List<String> values) {
            addCriterion("psex in", values, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexNotIn(List<String> values) {
            addCriterion("psex not in", values, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexBetween(String value1, String value2) {
            addCriterion("psex between", value1, value2, "psex");
            return (Criteria) this;
        }

        public Criteria andPsexNotBetween(String value1, String value2) {
            addCriterion("psex not between", value1, value2, "psex");
            return (Criteria) this;
        }

        public Criteria andPmanagerIsNull() {
            addCriterion("pmanager is null");
            return (Criteria) this;
        }

        public Criteria andPmanagerIsNotNull() {
            addCriterion("pmanager is not null");
            return (Criteria) this;
        }

        public Criteria andPmanagerEqualTo(String value) {
            addCriterion("pmanager =", value, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerNotEqualTo(String value) {
            addCriterion("pmanager <>", value, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerGreaterThan(String value) {
            addCriterion("pmanager >", value, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerGreaterThanOrEqualTo(String value) {
            addCriterion("pmanager >=", value, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerLessThan(String value) {
            addCriterion("pmanager <", value, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerLessThanOrEqualTo(String value) {
            addCriterion("pmanager <=", value, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerLike(String value) {
            addCriterion("pmanager like", value, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerNotLike(String value) {
            addCriterion("pmanager not like", value, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerIn(List<String> values) {
            addCriterion("pmanager in", values, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerNotIn(List<String> values) {
            addCriterion("pmanager not in", values, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerBetween(String value1, String value2) {
            addCriterion("pmanager between", value1, value2, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPmanagerNotBetween(String value1, String value2) {
            addCriterion("pmanager not between", value1, value2, "pmanager");
            return (Criteria) this;
        }

        public Criteria andPphoneIsNull() {
            addCriterion("pphone is null");
            return (Criteria) this;
        }

        public Criteria andPphoneIsNotNull() {
            addCriterion("pphone is not null");
            return (Criteria) this;
        }

        public Criteria andPphoneEqualTo(String value) {
            addCriterion("pphone =", value, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneNotEqualTo(String value) {
            addCriterion("pphone <>", value, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneGreaterThan(String value) {
            addCriterion("pphone >", value, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneGreaterThanOrEqualTo(String value) {
            addCriterion("pphone >=", value, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneLessThan(String value) {
            addCriterion("pphone <", value, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneLessThanOrEqualTo(String value) {
            addCriterion("pphone <=", value, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneLike(String value) {
            addCriterion("pphone like", value, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneNotLike(String value) {
            addCriterion("pphone not like", value, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneIn(List<String> values) {
            addCriterion("pphone in", values, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneNotIn(List<String> values) {
            addCriterion("pphone not in", values, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneBetween(String value1, String value2) {
            addCriterion("pphone between", value1, value2, "pphone");
            return (Criteria) this;
        }

        public Criteria andPphoneNotBetween(String value1, String value2) {
            addCriterion("pphone not between", value1, value2, "pphone");
            return (Criteria) this;
        }

        public Criteria andPemailIsNull() {
            addCriterion("pemail is null");
            return (Criteria) this;
        }

        public Criteria andPemailIsNotNull() {
            addCriterion("pemail is not null");
            return (Criteria) this;
        }

        public Criteria andPemailEqualTo(String value) {
            addCriterion("pemail =", value, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailNotEqualTo(String value) {
            addCriterion("pemail <>", value, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailGreaterThan(String value) {
            addCriterion("pemail >", value, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailGreaterThanOrEqualTo(String value) {
            addCriterion("pemail >=", value, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailLessThan(String value) {
            addCriterion("pemail <", value, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailLessThanOrEqualTo(String value) {
            addCriterion("pemail <=", value, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailLike(String value) {
            addCriterion("pemail like", value, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailNotLike(String value) {
            addCriterion("pemail not like", value, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailIn(List<String> values) {
            addCriterion("pemail in", values, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailNotIn(List<String> values) {
            addCriterion("pemail not in", values, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailBetween(String value1, String value2) {
            addCriterion("pemail between", value1, value2, "pemail");
            return (Criteria) this;
        }

        public Criteria andPemailNotBetween(String value1, String value2) {
            addCriterion("pemail not between", value1, value2, "pemail");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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
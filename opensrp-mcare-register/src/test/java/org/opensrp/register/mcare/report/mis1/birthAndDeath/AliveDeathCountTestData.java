package org.opensrp.register.mcare.report.mis1.birthAndDeath;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.text.SimpleDateFormat;
import java.util.*;


public class AliveDeathCountTestData extends MIS1TestData {



    public AliveDeathCountTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
    }

    public List<Members> getTotalAliveChild() {
        System.out.println("111111111111111111111111111111111111111");
        String status="";
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                status="valid";
                allMembers.add(createValidDataWithRandomDateTime(status));
            } else {
                status="invalid";
                allMembers.add(createInvalidValidDataWithRandomDateTime(i , status));
            }
        }

        allMembers.add(createValidDataWithStartDateTime("valid"));
       allMembers.add(createValidDataWithEndDateTime("valid"));

        return allMembers;
    }

    public List<Members> getChildUnderWeight(){
        System.out.println("222222222222222222222222222222222222222");
        List<Members> allMembers = new ArrayList<>();
        String status="";
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                status="valid";
                allMembers.add(createValidChildUnderWeightDataWithRandomDateTime(status));
            } else {
                status="invalid";
                allMembers.add(createInvalidValidChildUnderWeightDataWithRandomDateTime(i , status));
            }
        }

        allMembers.add(createValidChildUnderWeightDataWithStartDateTime("valid"));
        allMembers.add(createValidChildUnderWeightDataWithEndDateTime("valid"));
        return allMembers;
    }

    public List<Members> getPrematureChild() {
        System.out.println("3333333333333333333333333333333333");
        List<Members> allMembers = new ArrayList<>();
        String status = "";
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                status="valid";
                allMembers.add(createValidPrematureDataWithRandomDateTime(status));
            } else {
                status="invalid";
                allMembers.add(createInvalidValidPrematureDataWithRandomDateTime(i , status));
            }
        }

        allMembers.add(createValidPrematureDataWithStartDateTime("valid"));
        allMembers.add(createValidPrematureDataWithEndDateTime("valid"));

        return allMembers;
    }

    public List<Members> getDeathInLessThanSevenDays(){
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                allMembers.add(createValidDInLessSevDaysDataWithRandomDateTime(Members.DeathSectionQuery.deathInLessThanSevenDays));
            } else {
                allMembers.add(createInvalidValidDInLessSevDaysDataWithRandomDateTime(i , Members.DeathSectionQuery.deathInLessThanSevenDays));
            }
        }
        allMembers.add(createValidDInLessSevDaysDataWithStartDateTime( Members.DeathSectionQuery.deathInLessThanSevenDays ));
        allMembers.add(createValidDInLessSevDaysDataWithEndDateTime( Members.DeathSectionQuery.deathInLessThanSevenDays ));
        return allMembers;
    }

    public List<Members> getDeathInLessThanTwnEightDays(){
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                allMembers.add(createValidDInLessTwnEightDaysDataWithRandomDateTime(Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            } else {
                allMembers.add(createInvalidValidDInLessTwnEightDaysDataWithRandomDateTime(i , Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            }
        }
        allMembers.add(createValidDInLessTwnEightDaysDataWithStartDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        allMembers.add(createValidDInLessTwnEightDaysDataWithEndDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        return allMembers;
    }

    public List<Members> getDeathInLessThanOneYrDays(){
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                allMembers.add(createValidDInLessThanOneYrDataWithRandomDateTime(Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            } else {
                allMembers.add(createInvalidValidDInLessThanOneYrDataWithRandomDateTime(i , Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            }
        }
        allMembers.add(createValidDInLessThanOneYrDataWithStartDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        allMembers.add(createValidDInLessThanOneYrDataWithEndDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        return allMembers;
    }

    public List<Members> getDeathInLessThanFiveYrDays(){
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                allMembers.add(createValidDInLessThanFiveYrDataWithRandomDateTime(Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            } else {
                allMembers.add(createInvalidValidDInLessThanFiveYrDataWithRandomDateTime(i , Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            }
        }
        allMembers.add(createValidDInLessThanFiveYrDataWithStartDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        allMembers.add(createValidDInLessThanFiveYrDataWithEndDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        return allMembers;
    }

    public List<Members> getDeathofMotherDays(){
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                allMembers.add(createValidDeathofMotherDataWithRandomDateTime(Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            } else {
                allMembers.add(createInvalidValidDeathofMotherDataWithRandomDateTime(i , Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            }
        }
        allMembers.add(createValidDeathofMotherDataWithStartDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        allMembers.add(createValidDeathofMotherDataWithEndDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        return allMembers;
    }

    public List<Members> getOtherDeathDays(){
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount-2; i++) {
            if (i < validCount-2) {
                allMembers.add(createValidOtherofDeathDataWithRandomDateTime(Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            } else {
                allMembers.add(createInvalidValidOtherofDeathDataWithRandomDateTime(i , Members.DeathSectionQuery.deathInLessThanTwentyEightDays));
            }
        }
        allMembers.add(createValidOtherofDeathDataWithStartDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        allMembers.add(createValidOtherofDeathDataWithEndDateTime( Members.DeathSectionQuery.deathInLessThanTwentyEightDays ));
        return allMembers;
    }



    private Members createValidDInLessSevDaysDataWithStartDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessSevDaysDate(startDateTime ,deathSectionQuery);
    }

    private Members createValidDInLessSevDaysDataWithEndDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessSevDaysDate(endDateTime , deathSectionQuery);
    }

    private Members createValidDInLessSevDaysDataWithRandomDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessSevDaysDate(getRandomNumberBetween(startDateTime, endDateTime) , deathSectionQuery);
    }


    private Members createInvalidValidDInLessSevDaysDataWithRandomDateTime(int index, Members.DeathSectionQuery deathSectionQuery) {

        if (index % 2 == 0) {
            return createMemberWithDInLessSevDaysDate(getRandomNumberBetween(1504264166, 1506769766) , deathSectionQuery);
        } else {
            return createMemberWithDInLessSevDaysDate(getRandomNumberBetween(1483228800, 1485734400) , deathSectionQuery);
        }
    }



    private Members createValidDInLessTwnEightDaysDataWithStartDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessTwnEightDaysDate(startDateTime ,deathSectionQuery);
    }

    private Members createValidDInLessTwnEightDaysDataWithEndDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessTwnEightDaysDate(endDateTime , deathSectionQuery);
    }

    private Members createValidDInLessTwnEightDaysDataWithRandomDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessTwnEightDaysDate(getRandomNumberBetween(startDateTime, endDateTime) , deathSectionQuery);
    }


    private Members createInvalidValidDInLessTwnEightDaysDataWithRandomDateTime(int index, Members.DeathSectionQuery deathSectionQuery) {

        if (index % 2 == 0) {
            return createMemberWithDInLessTwnEightDaysDate(getRandomNumberBetween(1504264166, 1506769766) , deathSectionQuery);
        } else {
            return createMemberWithDInLessTwnEightDaysDate(getRandomNumberBetween(1483228800, 1485734400) , deathSectionQuery);
        }
    }



    private Members createValidDInLessThanOneYrDataWithStartDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessOneYrDate(startDateTime ,deathSectionQuery);
    }

    private Members createValidDInLessThanOneYrDataWithEndDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessOneYrDate(endDateTime , deathSectionQuery);
    }

    private Members createValidDInLessThanOneYrDataWithRandomDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessOneYrDate(getRandomNumberBetween(startDateTime, endDateTime) , deathSectionQuery);
    }


    private Members createInvalidValidDInLessThanOneYrDataWithRandomDateTime(int index, Members.DeathSectionQuery deathSectionQuery) {

        if (index % 2 == 0) {
            return createMemberWithDInLessOneYrDate(getRandomNumberBetween(1504264166, 1506769766) , deathSectionQuery);
        } else {
            return createMemberWithDInLessOneYrDate(getRandomNumberBetween(1483228800, 1485734400) , deathSectionQuery);
        }
    }



    private Members createValidDInLessThanFiveYrDataWithStartDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessFiveYrDate(startDateTime ,deathSectionQuery);
    }

    private Members createValidDInLessThanFiveYrDataWithEndDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessFiveYrDate(endDateTime , deathSectionQuery);
    }

    private Members createValidDInLessThanFiveYrDataWithRandomDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDInLessFiveYrDate(getRandomNumberBetween(startDateTime, endDateTime) , deathSectionQuery);
    }

    private Members createInvalidValidDInLessThanFiveYrDataWithRandomDateTime(int index, Members.DeathSectionQuery deathSectionQuery) {

        if (index % 2 == 0) {
            return createMemberWithDInLessFiveYrDate(getRandomNumberBetween(1504264166, 1506769766) , deathSectionQuery);
        } else {
            return createMemberWithDInLessFiveYrDate(getRandomNumberBetween(1483228800, 1485734400) , deathSectionQuery);
        }
    }



    private Members createValidDeathofMotherDataWithStartDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDMotherDate(startDateTime ,deathSectionQuery);
    }

    private Members createValidDeathofMotherDataWithEndDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDMotherDate(endDateTime , deathSectionQuery);
    }

    private Members createValidDeathofMotherDataWithRandomDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberWithDMotherDate(getRandomNumberBetween(startDateTime, endDateTime) , deathSectionQuery);
    }


    private Members createInvalidValidDeathofMotherDataWithRandomDateTime(int index, Members.DeathSectionQuery deathSectionQuery) {

        if (index % 2 == 0) {
            return createMemberWithDMotherDate(getRandomNumberBetween(1504264166, 1506769766) , deathSectionQuery);
        } else {
            return createMemberWithDMotherDate(getRandomNumberBetween(1483228800, 1485734400) , deathSectionQuery);
        }
    }



    private Members createValidOtherofDeathDataWithStartDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberOtherDeathDate(startDateTime ,deathSectionQuery);
    }

    private Members createValidOtherofDeathDataWithEndDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberOtherDeathDate(endDateTime , deathSectionQuery);
    }

    private Members createValidOtherofDeathDataWithRandomDateTime(Members.DeathSectionQuery deathSectionQuery) {

        return createMemberOtherDeathDate(getRandomNumberBetween(startDateTime, endDateTime) , deathSectionQuery);
    }


    private Members createInvalidValidOtherofDeathDataWithRandomDateTime(int index, Members.DeathSectionQuery deathSectionQuery) {

        if (index % 2 == 0) {
            return createMemberOtherDeathDate(getRandomNumberBetween(1504264166, 1506769766) , deathSectionQuery);
        } else {
            return createMemberOtherDeathDate(getRandomNumberBetween(1483228800, 1485734400) , deathSectionQuery);
        }
    }

    private Members createValidDataWithStartDateTime(String status) {

        return createMemberWithDeliveryVisitDate(startDateTime , status);
    }

    private Members createValidDataWithEndDateTime(String status) {

        return createMemberWithDeliveryVisitDate(endDateTime , status);
    }

    private Members createValidDataWithRandomDateTime(String status) {

        return createMemberWithDeliveryVisitDate(getRandomNumberBetween(startDateTime, endDateTime) , status);
    }


    private Members createInvalidValidDataWithRandomDateTime(int index , String status) {

        if (index % 2 == 0) {
            return createMemberWithDeliveryVisitDate(getRandomNumberBetween(1504264166, 1506769766) , status);
        } else {
            return createMemberWithDeliveryVisitDate(getRandomNumberBetween(1483228800, 1485734400) , status);
        }
    }
    private Members createValidPrematureDataWithStartDateTime(String status) {

        return createMemberWithPrematureChild(startDateTime , status);
    }

    private Members createValidPrematureDataWithEndDateTime(String status) {

        return createMemberWithPrematureChild(endDateTime , status);
    }

    private Members createValidPrematureDataWithRandomDateTime(String status) {

        return createMemberWithPrematureChild(getRandomNumberBetween(startDateTime, endDateTime) , status);
    }


    private Members createInvalidValidPrematureDataWithRandomDateTime(int index , String status) {

        if (index % 2 == 0) {
            return createMemberWithPrematureChild(getRandomNumberBetween(1504264166, 1506769766) , status);
        } else {
            return createMemberWithPrematureChild(getRandomNumberBetween(1483228800, 1485734400) , status);
        }
    }
    private Members createValidChildUnderWeightDataWithStartDateTime(String status) {
        return createMemberWithChildUnderWeight(startDateTime , status);
    }

    private Members createValidChildUnderWeightDataWithEndDateTime(String status) {
        return createMemberWithChildUnderWeight(endDateTime , status);
    }

    private Members createValidChildUnderWeightDataWithRandomDateTime(String status) {
        return createMemberWithChildUnderWeight(getRandomNumberBetween(startDateTime, endDateTime) , status);
    }


    private Members createInvalidValidChildUnderWeightDataWithRandomDateTime(int index , String status) {
        if (index % 2 == 0) {
            return createMemberWithChildUnderWeight(getRandomNumberBetween(1504264166, 1506769766),status);
        } else {
            return createMemberWithChildUnderWeight(getRandomNumberBetween(1483228800, 1485734400),status);
        }
    }
}

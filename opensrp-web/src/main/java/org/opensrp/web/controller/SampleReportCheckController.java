package org.opensrp.web.controller;


import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.opensrp.common.AllConstants;
import org.opensrp.connector.DHIS2.DHIS2ReportBuilder;
import org.opensrp.connector.DHIS2.DHIS2Service;
import org.opensrp.connector.DHIS2.dxf2.DataValueSet;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class SampleReportCheckController {

	@Autowired
	private  MIS1ReportGenerator mis1ReportGenerator;
	private long startDateTime = 1506816000000L;
    private long endDateTime = 1509408000000L;
    public String unionName = "union";

    private int totalCondomUsagesOfCurrentMonth=0;
    private int totalPillUsagesOfCurrentMonth=0;
    private int totalInjectableUsagesOfCurrentMonth=0;
    private int totalIUEUDUsagesOfCurrentMonth=0;
    private int totalImplantUsagesOfCurrentMonth=0;
    private int totalNewBirthControlPillUsagesOfCurrentMonth=0;
    private int totalNewBirthControlCondomUsagesOfCurrentMonth=0;
    private int totalNewBirthControlInjectableUsagesOfCurrentMonth=0;
    private int totalNewBirthControlIUEUUsagesOfCurrentMonth=0;
    private int totalNewBirthControlImplantUsagesOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlCondomOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlPillOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlInjectableOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlIUEUDUOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlImplantOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlCondomOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlPillOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlInjectableOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlIEUDOfCurrentMonth=0;
    private int totalCountOfMembersWhoLeftUsagesTakeOthereOfBirthControlImplantOfCurrentMonth=0;
    private int newParmanentMale = 0;
    private int totalParmanentMale = 0;
    private int newParmanentFemale = 0;
    private int totalParmanentFemale = 0;

    private long informationCountPnc1 = 0;
    private long serviceCountPnc1 = 0;
    private long informationCountPnc2 = 0;
    private long serviceCountPnc2 = 0;
    private long informationCountPnc3 = 0;
    private long serviceCountPnc3 = 0;
    private long informationCountPnc4 = 0;
    private long serviceCountPnc4 = 0;
    private long informationCountAnc1 = 0;
    private long serviceCountAnc1 = 0;
    private long informationCountAnc2 = 0;
    private long serviceCountAnc2 = 0;
    private long informationCountAnc3 = 0;
    private long serviceCountAnc3 = 0;
    private long informationCountAnc4 = 0;
    private long serviceCountAnc4 = 0;
    private long countOfBirthAtHomeWithTrainedPerson =0 ;
    private long countOfNormalBirthAtHospitalOrClinic =0 ;
    private long countOfCesareanBirthAtHospitalOrClinic =0 ;
    private long tt1 = 0 ;
    private long tt2 = 0 ;
    private long tt3 = 0 ;
    private long tt4 = 0 ;
    private long tt5 = 0 ;
    private long totalPregnentCount = 0 ;
    private long countOfCounsellingOnChangesOfAdolescent = 0;
    private long countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy = 0;
    private long countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid = 0;
    private long countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases = 0;

    private long countOfBcg=0;
    private long newBornCleanedCount=0;
    private long usedChlorhexidinCount=0;
    private long countOfDangerousDiseases=0;
    private long countOfPnueomonia = 0;
    private long countOfDiarrhea=0;
    private long countOfOpv1Andpcv1Andpenta1 =0;
    private long countOfOpv2Andpcv2Andpenta2=0;
    private long countOfOpv3Andpenta3=0;
    private long countOfPcv3=0;

    private long totalCountOfLiveBirth = 0 ;
    private long totalChildWithUnderWeight = 0;
    private long totalCountOfDeathofLessThanFiveYr = 0;
    private long totalCountOfDeathofLessThanOneYr = 0;
    private long totalCountOfDeathofLessThanSevenDays = 0;
    private long totalCountOfDeathofLessThanTwnEightDays = 0;
    private long totalCountOfDeathofMother = 0;
    private long totalCountOfDeathofOther = 0;
    private long totalPrematureChild = 0;

    private long countOfBreastFeedinginOneHourlessThnZeroGreaterThnSix=0;
    private long countOfBreastFeedingUntillSixMonth = 0;
    private long countOfBreastFeedingFromZeroToSixMonth=0;
    private long countOfMAMForZeroToSix=0;
    private long countOfSAMForZeroToSix=0;
    private long countOfMAMForSixToTwntyFour=0;
    private long countOfSAMForSixToTwntyFour=0;
    private long countOfMAMForTwntyFourToSixty=0;
    private long countOfSAMForTwntyFourToSixty=0;
    private long countOfIronAndFolicAcidCouncilingForPregWoman=0;
    private long countOfIronAndFolicAcidDistributionForPregWomen=0;
    private long countOfIronAndFolicAcidCouncilingForMother=0;
    private long countOfIronAndFolicAcidDistributionForMother=0;
    private long countOfCouncilingOnBreastFeedingAndNutritionForPregWomen=0;
    private long countOfCouncilingOnBreastFeedingAndNutritionForMother=0;
    private long countOfCouncilingOnMNPForMother=0;

    private long totalCountOfEligibleCoupleOfCurrentMonth=0;
    private long totalCountOfNewEligibleCoupleOfCurrentMonth=0;
    private long totalCountOfEligibleCoupleInUnitOfCurrentMonth=0;

    @RequestMapping("/checkmisreport")
     public ModelAndView sampleCheck() throws IllegalAccessException, IOException, JSONException {
        List<Members> listOfMembers = mis1ReportGenerator.getAllCalculatorValue();

        MIS1Report mis1Report = new MIS1Report(unionName, listOfMembers, startDateTime, endDateTime);
        /*DHIS2ReportBuilder dhis2ReportBuilder = new DHIS2ReportBuilder("PKTk8zxbl0J", new DateTime(), new DateTime().minusYears(2));
        List<DataValueSet> dataValueSets = dhis2ReportBuilder.build(mis1Report);
        DHIS2Service service = new DHIS2Service("http://192.168.19.18:1971", "dgfp", "Dgfp@123");
        for(DataValueSet dataValueSet : dataValueSets) {
            System.out.println(dataValueSet.getDataSet() + dataValueSet.send(service));
        }*/

      // MIS1Report mis1Report = new MIS1Report(unionName,listOfMembers, startDateTime, endDateTime);
        familyPlanningCalculatorCheck(mis1Report);
        maternityCare(mis1Report);
        childCare(mis1Report);
       // nutrition(mis1Report);
        birthAndDeath(mis1Report);
        ModelAndView model = new ModelAndView("reportcalculatorcheck");
        model.addObject("size", listOfMembers.size());

        ///////////////////////////////////////////Family Planning /////////////////////////////////////////
        model.addObject("totalCondomUsagesOfCurrentMonth", totalCondomUsagesOfCurrentMonth);
        model.addObject("totalPillUsagesOfCurrentMonth", totalPillUsagesOfCurrentMonth);
        model.addObject("totalInjectableUsagesOfCurrentMonth" , totalInjectableUsagesOfCurrentMonth);
        model.addObject("totalIUEUDUsagesOfCurrentMonth", totalIUEUDUsagesOfCurrentMonth);
        model.addObject("totalImplantUsagesOfCurrentMonth", totalImplantUsagesOfCurrentMonth);

        model.addObject("totalNewBirthControlCondomUsagesOfCurrentMonth", totalNewBirthControlCondomUsagesOfCurrentMonth);
        model.addObject("totalNewBirthControlPillUsagesOfCurrentMonth", totalNewBirthControlPillUsagesOfCurrentMonth);
        model.addObject("totalNewBirthControlInjectableUsagesOfCurrentMonth" , totalNewBirthControlInjectableUsagesOfCurrentMonth);
        model.addObject("totalNewBirthControlIUEUUsagesOfCurrentMonth", totalNewBirthControlIUEUUsagesOfCurrentMonth);
        model.addObject("totalNewBirthControlImplantUsagesOfCurrentMonth", totalNewBirthControlImplantUsagesOfCurrentMonth);

        model.addObject("totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlCondomOfCurrentMonth" , totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlCondomOfCurrentMonth);
        model.addObject("totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlPillOfCurrentMonth" ,totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlPillOfCurrentMonth);
        model.addObject("totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlInjectableOfCurrentMonth" , totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlInjectableOfCurrentMonth);
        model.addObject("totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlIUEUDUOfCurrentMonth", totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlIUEUDUOfCurrentMonth);
        model.addObject("totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlImplantOfCurrentMonth" , totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlImplantOfCurrentMonth);

        model.addObject("totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlCondomOfCurrentMonth" , totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlCondomOfCurrentMonth);
        model.addObject("totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlPillOfCurrentMonth" , totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlPillOfCurrentMonth);
        model.addObject("totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlInjectableOfCurrentMonth" , totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlInjectableOfCurrentMonth);
        model.addObject("totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlIEUDOfCurrentMonth" , totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlIEUDOfCurrentMonth);
        model.addObject("totalCountOfMembersWhoLeftUsagesTakeOthereOfBirthControlImplantOfCurrentMonth" , totalCountOfMembersWhoLeftUsagesTakeOthereOfBirthControlImplantOfCurrentMonth);

        model.addObject("newParmanentMale" , newParmanentMale);
        model.addObject("newParmanentFemale" , newParmanentFemale);
        model.addObject("totalParmanentMale" , totalParmanentMale);
        model.addObject("totalParmanentFemale" , totalParmanentFemale);

        model.addObject("totalCountOfEligibleCoupleOfCurrentMonth" , totalCountOfEligibleCoupleOfCurrentMonth);
        model.addObject("totalCountOfNewEligibleCoupleOfCurrentMonth" , totalCountOfNewEligibleCoupleOfCurrentMonth);
        model.addObject("totalCountOfEligibleCoupleInUnitOfCurrentMonth" , totalCountOfEligibleCoupleInUnitOfCurrentMonth);


        //////////////////////////////////////Maternity Care//////////////////////////////////////////////////////
        model.addObject("countOfBirthAtHomeWithTrainedPerson" , countOfBirthAtHomeWithTrainedPerson);
        model.addObject("countOfNormalBirthAtHospitalOrClinic" , countOfNormalBirthAtHospitalOrClinic);
        model.addObject("countOfCesareanBirthAtHospitalOrClinic" , countOfCesareanBirthAtHospitalOrClinic);
        model.addObject("tt1" , tt1);
        model.addObject("tt2" , tt2);
        model.addObject("tt3" , tt3);
        model.addObject("tt4" , tt4);
        model.addObject("tt5" ,tt5);
        model.addObject("totalPregnentCount" , totalPregnentCount);
        model.addObject("countOfCounsellingOnChangesOfAdolescent" ,countOfCounsellingOnChangesOfAdolescent);
        model.addObject("countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy" , countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy);
        model.addObject("countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid" ,countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid);
        model.addObject("countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases" ,countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases);
        ///////////////////////////////////Child Care///////////////////////////////////////////////////////////
        model.addObject("countOfBcg" , countOfBcg);
        model.addObject("newBornCleanedCount" , newBornCleanedCount);
        model.addObject("usedChlorhexidinCount" , usedChlorhexidinCount);
        model.addObject("countOfDangerousDiseases" , countOfDangerousDiseases);
        model.addObject("countOfPnueomonia" , countOfPnueomonia);
        model.addObject("countOfDiarrhea" , countOfDiarrhea);
        model.addObject("countOfOpv1Andpcv1Andpenta1" , countOfOpv1Andpcv1Andpenta1);
        model.addObject("countOfOpv2Andpcv2Andpenta2" , countOfOpv2Andpcv2Andpenta2);
        model.addObject("countOfOpv3Andpenta3" , countOfOpv3Andpenta3);
        model.addObject("countOfPcv3" , countOfPcv3);

        ////////////////////////////////////////Birth And Death/////////////////////////////////////////////////
        model.addObject("totalCountOfLiveBirth" ,totalCountOfLiveBirth );
        model.addObject("totalChildWithUnderWeight" ,totalChildWithUnderWeight );
        model.addObject("totalPrematureChild" , totalPrematureChild);
        model.addObject("totalCountOfDeathofLessThanSevenDays" , totalCountOfDeathofLessThanSevenDays);
        model.addObject("totalCountOfDeathofLessThanTwnEightDays" ,totalCountOfDeathofLessThanTwnEightDays);
        model.addObject("totalCountOfDeathofLessThanOneYr" , totalCountOfDeathofLessThanOneYr);
        model.addObject("totalCountOfDeathofLessThanFiveYr" ,totalCountOfDeathofLessThanFiveYr );
        model.addObject("totalCountOfDeathofMother" , totalCountOfDeathofMother);
        model.addObject("totalCountOfDeathofOther" ,totalCountOfDeathofOther);

        ///////////////////////////////////Nutrition/////////////////////////////////////////////////////////
        model.addObject("countOfIronAndFolicAcidCouncilingForPregWoman" , countOfIronAndFolicAcidCouncilingForPregWoman);
        model.addObject("countOfIronAndFolicAcidDistributionForPregWomen" , countOfIronAndFolicAcidDistributionForPregWomen);
        model.addObject("countOfIronAndFolicAcidCouncilingForMother" , countOfIronAndFolicAcidCouncilingForMother);
        model.addObject("countOfIronAndFolicAcidDistributionForMother" , countOfIronAndFolicAcidDistributionForMother);
        model.addObject("countOfCouncilingOnBreastFeedingAndNutritionForPregWomen" , countOfCouncilingOnBreastFeedingAndNutritionForPregWomen);
        model.addObject("countOfCouncilingOnBreastFeedingAndNutritionForMother" , countOfCouncilingOnBreastFeedingAndNutritionForMother);
        model.addObject("countOfCouncilingOnMNPForMother" , countOfCouncilingOnMNPForMother);
        model.addObject("countOfBreastFeedinginOneHourlessThnZeroGreaterThnSix" , countOfBreastFeedinginOneHourlessThnZeroGreaterThnSix);
        model.addObject("countOfMAMForZeroToSix" , countOfMAMForZeroToSix);
        model.addObject("countOfSAMForZeroToSix" , countOfSAMForZeroToSix);
        model.addObject("countOfMAMForSixToTwntyFour" , countOfMAMForSixToTwntyFour);
        model.addObject("countOfSAMForSixToTwntyFour" , countOfSAMForSixToTwntyFour);
        model.addObject("countOfMAMForTwntyFourToSixty" , countOfMAMForTwntyFourToSixty);
        model.addObject("countOfSAMForTwntyFourToSixty" , countOfSAMForTwntyFourToSixty);
        model.addObject("countOfBreastFeedingUntillSixMonth" , countOfBreastFeedingUntillSixMonth);
        return model;
    }
    
    public void familyPlanningCalculatorCheck(MIS1Report mis1Report){

      totalCondomUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().totalUsages();
      totalPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().totalUsages();
     totalInjectableUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getInjectableUsagesCalculator().totalUsages();
      totalIUEUDUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getIudUsagesCalculator().totalUsages();
      totalImplantUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getImplantUsagesCalculator().totalUsages();

      totalNewBirthControlCondomUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().newUsages();
      totalNewBirthControlPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().newUsages();
     totalNewBirthControlInjectableUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getInjectableUsagesCalculator().newUsages();
      totalNewBirthControlIUEUUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getIudUsagesCalculator().newUsages();
      totalNewBirthControlImplantUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getImplantUsagesCalculator().newUsages();

      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlCondomOfCurrentMonth =
               mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenNone();
      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlPillOfCurrentMonth =
               mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().leftUsagesButTakenNone();
      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlInjectableOfCurrentMonth =
               mis1Report.getFamilyPlanningReport().getInjectableUsagesCalculator().leftUsagesButTakenNone();
      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlIUEUDUOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getIudUsagesCalculator().leftUsagesButTakenNone();
      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlImplantOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getImplantUsagesCalculator().leftUsagesButTakenNone();

      totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlCondomOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenOther();
      totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlPillOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().leftUsagesButTakenOther();
      totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlInjectableOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getInjectableUsagesCalculator().leftUsagesButTakenOther();
      totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlIEUDOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getIudUsagesCalculator().leftUsagesButTakenOther();
      totalCountOfMembersWhoLeftUsagesTakeOthereOfBirthControlImplantOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getImplantUsagesCalculator().leftUsagesButTakenOther();

      newParmanentMale =
              mis1Report.getFamilyPlanningReport().getMalePermanentMethodUsagesCalculator().newUsages();
      totalParmanentMale =
              mis1Report.getFamilyPlanningReport().getMalePermanentMethodUsagesCalculator().totalUsages();
      newParmanentFemale =
              mis1Report.getFamilyPlanningReport().getFemalePermanentMethodUsagesCalculator().newUsages();
      totalParmanentFemale =
              mis1Report.getFamilyPlanningReport().getFemalePermanentMethodUsagesCalculator().totalUsages();

      totalCountOfEligibleCoupleOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getEligibleCoupleCountCalculator().getTotalEligibleCouple();
      totalCountOfNewEligibleCoupleOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getEligibleCoupleCountCalculator().getNewEligibleCoupleVisitCount();
      totalCountOfEligibleCoupleInUnitOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getEligibleCoupleCountCalculator().getUnitTotalEligibleCoupleVisitCount();

    }

    public void maternityCare(MIS1Report mis1Report){

        totalPregnentCount =
                mis1Report.getMaternityCareReport().getPregnantWomenCountCalculator().getNewPregnantCount();
     informationCountPnc1 =
    /*           mis1Report.getMaternityCareReport().getPncReportCalculator().getPncOneVisitCalculator().getInformationCount();
        informationCountAnc1 =
                mis1Report.getMaternityCareReport().getAncReportCalculator().getVisitOneCount().
*/
        countOfCounsellingOnChangesOfAdolescent =
                mis1Report.getMaternityCareReport().getAdolescentHealthReportCalculator().getCountOfCounsellingOnChangesOfAdolescent();
        countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy=
                mis1Report.getMaternityCareReport().getAdolescentHealthReportCalculator().getCountOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy();
        countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid=
                mis1Report.getMaternityCareReport().getAdolescentHealthReportCalculator().getCountOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid();
        countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases=
                mis1Report.getMaternityCareReport().getAdolescentHealthReportCalculator().getCountOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases();
        tt1 =
                mis1Report.getMaternityCareReport().getTTDoseReportCalculator().getDoseOneCount();
        tt2 =
                mis1Report.getMaternityCareReport().getTTDoseReportCalculator().getDoseTwoCount();
        tt3 =
                mis1Report.getMaternityCareReport().getTTDoseReportCalculator().getDoseThreeCount();
        tt4 =
                mis1Report.getMaternityCareReport().getTTDoseReportCalculator().getDoseFourCount();
        tt5 =
                mis1Report.getMaternityCareReport().getTTDoseReportCalculator().getDoseFiveCount();

        countOfBirthAtHomeWithTrainedPerson = mis1Report.getMaternityCareReport().getPostpartumCareCalculator().getCountOfBirthAtHomeWithTrainedPerson();
        countOfNormalBirthAtHospitalOrClinic = mis1Report.getMaternityCareReport().getPostpartumCareCalculator().getCountOfNormalBirthAtHospitalOrClinic();
        countOfCesareanBirthAtHospitalOrClinic = mis1Report.getMaternityCareReport().getPostpartumCareCalculator().getCountOfCesareanBirthAtHospitalOrClinic();
    }


   public void childCare(MIS1Report mis1Report){

       countOfBcg = mis1Report.getChildCareReport().getVaccinationReportCalculator().getBcgCount();
       newBornCleanedCount = mis1Report.getChildCareReport().getNewBornCareReportCalculator().getIsCleanedCount();
       usedChlorhexidinCount = mis1Report.getChildCareReport().getNewBornCareReportCalculator().getUsedChlorhexidinCount();
       countOfDangerousDiseases = mis1Report.getChildCareReport().getDiseaseReportCalculator().getVeryDangerousDiseasesCount();
       countOfPnueomonia = mis1Report.getChildCareReport().getDiseaseReportCalculator().getPneumoniaCount();
       countOfDiarrhea = mis1Report.getChildCareReport().getDiseaseReportCalculator().getDiarrheaCount();
       countOfOpv1Andpcv1Andpenta1 = mis1Report.getChildCareReport().getVaccinationReportCalculator().getOpv1Andpcv1Andpenta1Count();
       countOfOpv2Andpcv2Andpenta2 = mis1Report.getChildCareReport().getVaccinationReportCalculator().getOpv2Andpcv2Andpenta2Count();
       countOfOpv3Andpenta3 = mis1Report.getChildCareReport().getVaccinationReportCalculator().getOpv3Andpenta3Count();
       countOfPcv3 = mis1Report.getChildCareReport().getVaccinationReportCalculator().getPcv3Count();

    }
  public void birthAndDeath(MIS1Report mis1Report){

        totalCountOfLiveBirth =
                mis1Report.getBirthAndDeathReport().getBirthCountCalculator().getTotalCountOfLiveBirth();
        totalChildWithUnderWeight =
                mis1Report.getBirthAndDeathReport().getTotalChildWithUnderWeight().getTotalChildWithUnderWeight();
        totalPrematureChild =
                mis1Report.getBirthAndDeathReport().getTotalPrematureChild().getTotalPrematureChild();
        totalCountOfDeathofLessThanSevenDays =
                mis1Report.getBirthAndDeathReport().getTotalDeathCountofLessThanSevenDays().getTotalCountofLessThanSevenDays();
        totalCountOfDeathofLessThanTwnEightDays =
                mis1Report.getBirthAndDeathReport().getTotalDeathCountofLessThanTwnEightDays().getTotalCountofLessThanTwnEightDays();
        totalCountOfDeathofLessThanOneYr =
                mis1Report.getBirthAndDeathReport().getDeathCountofLessThanOneYr().getTotalCountofLessThanOneYr();
        totalCountOfDeathofLessThanFiveYr =
                mis1Report.getBirthAndDeathReport().getDeathCountofLessThanFiveYr().getTotalCountofLessThanFiveYr();
        totalCountOfDeathofMother =
                mis1Report.getBirthAndDeathReport().getDeathCountofMother().getTotalCountOfDeathofMother();
        totalCountOfDeathofOther =
                mis1Report.getBirthAndDeathReport().getDeathCountofOther().getTotalCountOfDeathofOther();

    }

 /*public void nutrition(MIS1Report mis1Report) {

    countOfIronAndFolicAcidCouncilingForPregWoman =
             mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfCounsellingOnFolicAcidAndIronForPregWoman();
     countOfIronAndFolicAcidDistributionForPregWomen =
             mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfDistributionOfFolicAcidAndIronForPregWoman();
     countOfIronAndFolicAcidCouncilingForMother =
             mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfCounsellingOnFolicAcidAndIronForMother();
     countOfIronAndFolicAcidDistributionForMother =
             mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfDistributionOfFolicAcidAndIronForMother();
     countOfCouncilingOnBreastFeedingAndNutritionForPregWomen =
             mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfCounsellingOnBreastMilkAndComplementaryFoodForPregWoman();
     countOfCouncilingOnBreastFeedingAndNutritionForMother =
             mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfCounsellingOnBreastMilkAndComplementaryFoodFoMother();
     countOfCouncilingOnMNPForMother =
             mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfCounsellingOnFeedingMMMother();
     countOfBreastFeedinginOneHourlessThnZeroGreaterThnSix =
             mis1Report.getNutritionReport().getChildNutritionCalculator().getChildZeroToSix().countOfBreastFeedingWithInOneHour();
     countOfBreastFeedingUntillSixMonth =
             mis1Report.getNutritionReport().getChildNutritionCalculator().getChildZeroToSix().getCountOfBreastFeedingUntillSixMonth();
     countOfMAMForZeroToSix =
             mis1Report.getNutritionReport().getChildNutritionCalculator().getChildZeroToSix().getCountOfContractedMAM();
     countOfSAMForZeroToSix =
             mis1Report.getNutritionReport().getChildNutritionCalculator().getChildZeroToSix().getCountOfContractedSAM();
     countOfMAMForSixToTwntyFour =
             mis1Report.getNutritionReport().getChildNutritionCalculator().getChild6TO23().getCountOfContractedMAM();
     countOfSAMForSixToTwntyFour =
             mis1Report.getNutritionReport().getChildNutritionCalculator().getChild6TO23().getCountOfContractedSAM();
     countOfMAMForTwntyFourToSixty =
             mis1Report.getNutritionReport().getChildNutritionCalculator().getChild24to59().getCountOfContractedMAM();
     countOfSAMForTwntyFourToSixty =
             mis1Report.getNutritionReport().getChildNutritionCalculator().getChild24to59().getCountOfContractedSAM();
 }*/

}

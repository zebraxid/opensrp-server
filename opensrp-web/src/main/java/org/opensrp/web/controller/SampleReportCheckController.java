package org.opensrp.web.controller;


import java.util.List;

import org.opensrp.common.AllConstants;
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

    private long countOfBreastFeedinginOneHourlessThnZeroGreaterThnSix=0;
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
     public ModelAndView sampleCheck(){

       List<Members> listOfMembers = mis1ReportGenerator.getAllCalculatorValue();
       MIS1Report mis1Report = new MIS1Report(unionName,listOfMembers, startDateTime, endDateTime);
        familyPlanningCalculatorCheck(mis1Report);
        childCare(mis1Report);
        nutrition(mis1Report);
        ModelAndView model = new ModelAndView("reportcalculatorcheck");
        model.addObject("size", listOfMembers.size());
        model.addObject("totalCondomUsagesOfCurrentMonth", totalCondomUsagesOfCurrentMonth);
        model.addObject("totalPillUsagesOfCurrentMonth", totalPillUsagesOfCurrentMonth);
        model.addObject("totalIUEUDUsagesOfCurrentMonth", totalIUEUDUsagesOfCurrentMonth);
        model.addObject("totalImplantUsagesOfCurrentMonth", totalImplantUsagesOfCurrentMonth);

        model.addObject("totalNewBirthControlCondomUsagesOfCurrentMonth", totalNewBirthControlCondomUsagesOfCurrentMonth);
        model.addObject("totalNewBirthControlPillUsagesOfCurrentMonth", totalNewBirthControlPillUsagesOfCurrentMonth);
        model.addObject("totalNewBirthControlIUEUUsagesOfCurrentMonth", totalNewBirthControlIUEUUsagesOfCurrentMonth);
        model.addObject("totalNewBirthControlImplantUsagesOfCurrentMonth", totalNewBirthControlImplantUsagesOfCurrentMonth);
        model.addObject("totalCountOfEligibleCoupleOfCurrentMonth" , totalCountOfEligibleCoupleOfCurrentMonth);
        model.addObject("totalCountOfNewEligibleCoupleOfCurrentMonth" , totalCountOfNewEligibleCoupleOfCurrentMonth);
        model.addObject("totalCountOfEligibleCoupleInUnitOfCurrentMonth" , totalCountOfEligibleCoupleInUnitOfCurrentMonth);

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
        return model;
    }
    
    public void familyPlanningCalculatorCheck(MIS1Report mis1Report){

      totalCondomUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().totalUsages();
      totalPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().totalUsages();
     /* totalInjectableUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getInjectableUsagesCalculator().totalUsages();*/
      totalIUEUDUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getIudUsagesCalculator().totalUsages();
      totalImplantUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getImplantUsagesCalculator().totalUsages();

      totalNewBirthControlCondomUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().newUsages();
      totalNewBirthControlPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().newUsages();
     /* totalNewBirthControlInjectableUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getInjectableUsagesCalculator().newUsages();*/
      totalNewBirthControlIUEUUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getIudUsagesCalculator().newUsages();
      totalNewBirthControlImplantUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getImplantUsagesCalculator().newUsages();

      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlCondomOfCurrentMonth =
               mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenNone();
      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlPillOfCurrentMonth =
               mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().leftUsagesButTakenNone();
      /*totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlInjectableOfCurrentMonth =
               mis1Report.getFamilyPlanningReport().getInjectableUsagesCalculator().leftUsagesButTakenNone();*/
      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlIUEUDUOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getIudUsagesCalculator().leftUsagesButTakenNone();
      totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlImplantOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getImplantUsagesCalculator().leftUsagesButTakenNone();
      totalCountOfEligibleCoupleOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getEligibleCoupleCountCalculator().getTotalEligibleCouple();
      totalCountOfNewEligibleCoupleOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getEligibleCoupleCountCalculator().getNewEligibleCoupleVisitCount();
      totalCountOfEligibleCoupleInUnitOfCurrentMonth =
              mis1Report.getFamilyPlanningReport().getEligibleCoupleCountCalculator().getUnitTotalEligibleCoupleVisitCount();

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

    public void nutrition(MIS1Report mis1Report){

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
        countOfBreastFeedingFromZeroToSixMonth =
                mis1Report.getNutritionReport().getChildNutritionCalculator().getChildZeroToSix().getCountOfBreastFeedingUntillSixMonth();
        /*countOfFeedingAfterSixMonthSixToTwntyFour =
                mis1Report.getNutritionReport().getChildNutritionCalculator().getChild6TO23().getCountOfBreastFeedingAfterSixMonth();
        countOfFeedingAfterSixMonthTwntyFourToSixty =
                mis1Report.getNutritionReport().getChildNutritionCalculator().getChild24to59().get*/
        countOfMAMForZeroToSix=
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


    }
}

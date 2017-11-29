<%--
  Created by IntelliJ IDEA.
  User: asha
  Date: 10/16/17
  Time: 5:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${size}</title>
</head>
<body>
 This is total number of member <b> ${size}</b><br>
 <h3>Current Month Calculation</h3>
 The total use of condom of current month:<br>db result: <b> ${totalCondomUsagesOfCurrentMonth}</b> <br>
 The total use of pill current month:<br>db result: <b> ${totalPillUsagesOfCurrentMonth}</b> <br>
 The total use of Injectable current month:<br>db result: <b> ${totalInjectableUsagesOfCurrentMonth}</b> <br>
 The total use of IEUD current month:<br>db result: <b> ${totalIUEUDUsagesOfCurrentMonth}</b> <br>
 The total use of Implant current month:<br>db result: <b> ${totalImplantUsagesOfCurrentMonth}</b> <br>
 <h3>Left Birth Controls</h3>
 Left Pill,Take None:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlPillOfCurrentMonth}</b><br>
 Left Condom,Take None:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlCondomOfCurrentMonth}</b><br>
 Left Injectable,Take None:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlInjectableOfCurrentMonth}</b><br>
 Left IEUD,Take None:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlIUEUDUOfCurrentMonth}</b><br>
 Left Implant,Take None:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeNoneOfBirthControlImplantOfCurrentMonth}</b><br>
 Left Pill,Take Other:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlPillOfCurrentMonth}</b><br>
 Left Condom,Take Other:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlCondomOfCurrentMonth}</b><br>
 Left Injectable,Take Other:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlInjectableOfCurrentMonth}</b><br>
 Left IEUD,Take Other:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeOtherOfBirthControlIEUDOfCurrentMonth}</b><br>
 Left Implant,Take Other:<br> db result: <b>${totalCountOfMembersWhoLeftUsagesTakeOthereOfBirthControlImplantOfCurrentMonth}</b><br>
 <h3>New Month Calculation</h3>
 The total use of condom of new current month:<br>db result: <b> ${totalNewBirthControlCondomUsagesOfCurrentMonth}</b><br>
 The total use of pill of new current month:<br>db result: <b> ${totalNewBirthControlPillUsagesOfCurrentMonth}</b><br>
 The total use of Injectable of new current month:<br>db result: <b> ${totalNewBirthControlInjectableUsagesOfCurrentMonth}</b><br>
 The total use of IEUD of new current month:<br>db result: <b> ${totalNewBirthControlIUEUUsagesOfCurrentMonth}</b><br>
 The total use of Implant of new current month:<br>db result: <b> ${totalNewBirthControlImplantUsagesOfCurrentMonth}</b> <br>
 <h3>Parmanent Birth Control</h3>
 new Parmanent Male:<br>db result:<b>${newParmanentMale}</b><br>
 total Parmanent Male:<br>db result:<b>${totalParmanentMale}</b><br>
 new Parmanent Female:<br>db result:<b>${newParmanentFemale}</b><br>
 total Parmanent Female:<br>db result:<b>${totalParmanentFemale}</b><br>
 <h3>Eligilble Couple Calculation</h3>
 Unit Total:<br>db result: <b> ${totalCountOfEligibleCoupleInUnitOfCurrentMonth}</b><br>
 Total on Current Month :<br>db result: <b> ${totalCountOfNewEligibleCoupleOfCurrentMonth}</b><br>
 Total new on Current Month:<br>db result: <b> ${totalIUEUDUsagesOfCurrentMonth}</b><br>
 <h3>Maternity Care</h3>
 countOfBirthAtHomeWithTrainedPerson:<br>db result: <b> ${countOfBirthAtHomeWithTrainedPerson}</b><br>
 countOfNormalBirthAtHospitalOrClinic:<br>db result: <b> ${countOfNormalBirthAtHospitalOrClinic}</b><br>
 countOfCesareanBirthAtHospitalOrClinic<br>db result: <b> ${countOfCesareanBirthAtHospitalOrClinic}</b> <br>
 tt1:<br>db result: <b> ${tt1}</b><br>
 tt2:<br>db result: <b> ${tt2}</b><br>
 tt3:<br>db result: <b> ${tt3}</b> <br>
 tt4:<br>db result: <b> ${tt4}</b><br>
 tt5:<br>db result: <b> ${tt5}</b> <br>
 countOfCounsellingOnChangesOfAdolescent:<br>db result:<b>${countOfCounsellingOnChangesOfAdolescent}</b><br>
 countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy:<br>db result:<b>${countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy}</b><br>
 countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid:<br>db result:<b>${countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid}</b><br>
 countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases:<br>db result:<b>${countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases}</b><br>
 totalPregnentCount:<br>db result: <b> ${totalPregnentCount}</b>
 <h3>Child Care</h3>
 BCG:<br>db result: <b> ${countOfBcg}</b><br>
 OPV1 , Penta1 , PCV1:<br>db result: <b> ${countOfOpv1Andpcv1Andpenta1}</b><br>
 OPV2 , Penta2 , PCV2:<br>db result: <b> ${countOfOpv2Andpcv2Andpenta2}</b><br>
 OPV3 , Penta3:<br>db result: <b> ${countOfOpv3Andpenta3}</b><br>
 IS clean out or not:<br>db result: <b> ${newBornCleanedCount}</b><br>
 Count of Chlorhexidin:<br>db result: <b> ${usedChlorhexidinCount}</b><br>
 Count of Dangerous Diseases:<br>db result: <b> ${countOfDangerousDiseases}</b><br>
 Count of Pneumonia:<br>db result: <b> ${countOfPnueomonia}</b><br>
 Count of Diarrhea:<br>db result: <b> ${countOfDiarrhea}</b><br>
 <h3>Birth And Death</h3>
 Number of live birth:<br>db result:<b>${totalCountOfLiveBirth}</b><br>
 Child of Under weight:<br>db result:<b>${totalChildWithUnderWeight}</b><br>
 Child of Premature birth:<br>db result:<b>${totalPrematureChild}</b><br>
 Death- 0 to 7 days:<br>db result:<b>${totalCountOfDeathofLessThanSevenDays}</b><br>
 Death- 8 to 28 days:<br>db result:<b>${totalCountOfDeathofLessThanTwnEightDays}</b><br>
 Death- less than 1 yr:<br>db result:<b>${totalCountOfDeathofLessThanOneYr}</b><br>
 Death- less than 5 yr:<br>db result:<b>${totalCountOfDeathofLessThanFiveYr}</b><br>
 Death-Mother:<br>db result:<b>${totalCountOfDeathofMother}</b><br>
 Death-Other:<br>db result:<b>${totalCountOfDeathofOther}</b><br>
 <h3>Nutrition</h3>
 Iron and Folic Acid Counselling for Preg Women:<br>db result: <b> ${countOfIronAndFolicAcidCouncilingForPregWoman}</b><br>
 Iron and Folic Acid Counselling for Mother:<br>db result: <b> ${countOfIronAndFolicAcidCouncilingForMother}</b><br>
 Iron and Folic Acid Distribution for Preg Women:<br>db result: <b> ${countOfIronAndFolicAcidDistributionForPregWomen}</b><br>
 Iron and Folic Acid Distribution for Mother:<br>db result: <b> ${countOfIronAndFolicAcidDistributionForMother}</b><br>
 Counselling Breast Feeding and Food for Preg Women:<br>db result: <b> ${countOfCouncilingOnBreastFeedingAndNutritionForPregWomen}</b><br>
 Counselling Breast Feeding and Food for Mother:<br>db result: <b> ${countOfCouncilingOnBreastFeedingAndNutritionForMother}</b><br>
 Feeding MNP Powder for Mother<br>db result: <b> ${countOfCouncilingOnMNPForMother}</b><br>
 Breast Feeding Within One Hour:<br>db result: <b> ${countOfBreastFeedinginOneHourlessThnZeroGreaterThnSix}</b><br>
 Breast Feeding Until Six month:<br>db result: <b> ${countOfBreastFeedingUntillSixMonth}</b><br>
 MAM - 0 to 6:<br>db result: <b> ${countOfMAMForZeroToSix}</b><br>
 SAM - 0 to 6:<br>db result: <b> ${countOfSAMForZeroToSix}</b><br>
 MAM - 6 to 24:<br>db result: <b> ${countOfMAMForSixToTwntyFour}</b><br>
 SAM - 6 to 24:<br>db result: <b> ${countOfSAMForSixToTwntyFour}</b><br>
 MAM - 24 to 60:<br>db result: <b> ${countOfMAMForTwntyFourToSixty}</b><br>
 SAM - 24 to 60:<br>db result: <b> ${countOfSAMForTwntyFourToSixty}</b><br>

</body>
</html>

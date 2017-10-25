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
 The total use of condom of current month:<br>db result: <b> ${totalCondomUsagesOfCurrentMonth}</b> expected result :2<br>
 The total use of pill current month:<br>db result: <b> ${totalPillUsagesOfCurrentMonth}</b> expected result :3<br>
 The total use of IEUD current month:<br>db result: <b> ${totalIUEUDUsagesOfCurrentMonth}</b> expected result :4<br>
 The total use of Implant current month:<br>db result: <b> ${totalImplantUsagesOfCurrentMonth}</b> expected result :1<br>
 <h3>New Month Calculation</h3>
 The total use of condom of new current month:<br>db result: <b> ${totalNewBirthControlCondomUsagesOfCurrentMonth}</b> expected result :1<br>
 The total use of pill of new current month:<br>db result: <b> ${totalNewBirthControlPillUsagesOfCurrentMonth}</b> expected result :2<br>
 The total use of IEUD of new current month:<br>db result: <b> ${totalNewBirthControlIUEUUsagesOfCurrentMonth}</b> expected result :2<br>
 The total use of Implant of new current month:<br>db result: <b> ${totalNewBirthControlImplantUsagesOfCurrentMonth}</b> expected result :1<br>
 <h3>Eligilble Couple Calculation</h3>
 Unit Total:<br>db result: <b> ${totalCountOfEligibleCoupleInUnitOfCurrentMonth}</b> expected result :<br>
 Total on Current Month :<br>db result: <b> ${totalCountOfNewEligibleCoupleOfCurrentMonth}</b> expected result :<br>
 Total new on Current Month:<br>db result: <b> ${totalIUEUDUsagesOfCurrentMonth}</b> expected result :<br>
 <h3>Child Care</h3>
 BCG:<br>db result: <b> ${countOfBcg}</b> expected result :4<br>
 OPV1 , Penta1 , PCV1:<br>db result: <b> ${countOfOpv1Andpcv1Andpenta1}</b> expected result :0<br>
 OPV2 , Penta2 , PCV2:<br>db result: <b> ${countOfOpv2Andpcv2Andpenta2}</b> expected result :0<br>
 OPV3 , Penta3:<br>db result: <b> ${countOfOpv3Andpenta3}</b> expected result :0<br>
 IS clean out or not:<br>db result: <b> ${newBornCleanedCount}</b> expected result :1<br>
 Count of Chlorhexidin:<br>db result: <b> ${usedChlorhexidinCount}</b> expected result :2<br>
 Count of Dangerous Diseases:<br>db result: <b> ${countOfDangerousDiseases}</b> expected result :2<br>
 Count of Pneumonia:<br>db result: <b> ${countOfPnueomonia}</b> expected result :3<br>
 Count of Diarrhea:<br>db result: <b> ${countOfDiarrhea}</b> expected result :5<br>
 <h3>Nutrition</h3>
 Iron and Folic Acid Counselling for Preg Women:<br>db result: <b> ${countOfIronAndFolicAcidCouncilingForPregWoman}</b> expected result :<br>
 Iron and Folic Acid Counselling for Mother:<br>db result: <b> ${countOfIronAndFolicAcidCouncilingForMother}</b> expected result :<br>
 Iron and Folic Acid Distribution for Preg Women:<br>db result: <b> ${countOfIronAndFolicAcidDistributionForPregWomen}</b> expected result :<br>
 Iron and Folic Acid Distribution for Mother:<br>db result: <b> ${countOfIronAndFolicAcidDistributionForMother}</b> expected result :<br>
 Counselling Breast Feeding and Food for Preg Women:<br>db result: <b> ${countOfCouncilingOnBreastFeedingAndNutritionForPregWomen}</b> expected result :<br>
 Counselling Breast Feeding and Food for Mother:<br>db result: <b> ${countOfCouncilingOnBreastFeedingAndNutritionForMother}</b> expected result :<br>
 Feeding MNP Powder for Mother<br>db result: <b> ${countOfCouncilingOnMNPForMother}</b> expected result :<br>
 Breast Feeding Within One Hour:<br>db result: <b> ${countOfBreastFeedinginOneHourlessThnZeroGreaterThnSix}</b> expected result :<br>
 Breast Feeding Until Six month:<br>db result: <b> ${countOfBreastFeedingFromZeroToSixMonth}</b> expected result :<br>
 Food for after Six Month in 6 to 24:<br>db result: <b> ${countOfBcg}</b> expected result :<br>
 Food for After Six Month 24 to 60:<br>db result: <b> ${countOfBcg}</b> expected result :<br>
 MAM - 0 to 6:<br>db result: <b> ${countOfMAMForZeroToSix}</b> expected result :<br>
 SAM - 0 to 6:<br>db result: <b> ${countOfSAMForZeroToSix}</b> expected result :<br>
 MAM - 6 to 24:<br>db result: <b> ${countOfMAMForSixToTwntyFour}</b> expected result :<br>
 SAM - 6 to 24:<br>db result: <b> ${countOfSAMForSixToTwntyFour}</b> expected result :<br>
 MAM - 24 to 60:<br>db result: <b> ${countOfMAMForTwntyFourToSixty}</b> expected result :<br>
 SAM - 24 to 60:<br>db result: <b> ${countOfSAMForTwntyFourToSixty}</b> expected result :<br>

</body>
</html>
